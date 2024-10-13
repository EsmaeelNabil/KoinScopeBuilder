import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

private val String.valCase: String
    get() = buildString {
        append(this@valCase.first().lowercase())
        append(this@valCase.drop(1))
    }


class KSBProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(KoinScopeWith::class.simpleName!!)
        symbols.forEach { symbol ->
            if (symbol is KSClassDeclaration && symbol.isValid()) {
                processClass(symbol)
            }
        }
        return emptyList()
    }

    private fun KSClassDeclaration.isValid(): Boolean {
        return this.annotations.any { it.shortName.asString() == KoinScopeWith::class.simpleName }
    }

    private fun processClass(clazz: KSClassDeclaration) {

        val koinModuleName = clazz.simpleName.asString().valCase.plus("Module")
        // Create the local composition
        generateKoinModule(clazz, koinModuleName)
    }

    private fun generateKoinModule(
        clazz: KSClassDeclaration,
        koinModuleName: String
    ) {
        val clazzName = clazz.simpleName.asString()
        val packageName = clazz.packageName.asString()

        val annotationArgumentValue = clazz.annotations.getArgumentValue("scopeName")

        val koinModule = """
            import org.koin.core.qualifier.named
            import org.koin.dsl.module
            import $packageName.$clazzName
            import org.koin.core.module.dsl.scopedOf
            import androidx.compose.runtime.Composable
            import androidx.compose.runtime.DisposableEffect
            import androidx.compose.runtime.derivedStateOf
            import androidx.compose.runtime.remember
            import org.koin.core.scope.Scope
            import org.koin.mp.KoinPlatform.getKoin
            
            private val scopeName = "$annotationArgumentValue"
        
            val $koinModuleName = module {
                scope(named(scopeName)) {
                    scopedOf(::${clazzName})
                }
            }
            
            /**
            * Ensures the appropriate lifecycle management for a [$clazzName]
            * disposing the Koin scope upon composable exit.
            * This function initializes the view model before it is utilized.
            * @param key : The unique identifier of the workgroup.
            * @return A [$clazzName] scoped to the specified workgroup.
            */
            
            @Composable
            fun getOrCreate${clazzName}(key: Any? = null): $clazzName {

                DisposableEffect(key) {
                    onDispose {
                        getKoin().getScopeOrNull(scopeName)?.close()
                    }
                }

                return remember(key) {
                    (getKoin().getScopeOrNull(scopeName) ?: getKoin().createScope(
                        scopeName,
                        named(scopeName)
                    )).get<$clazzName>()
                }
            }

                        
        """.trimIndent()

        codeGenerator.createNewFile(
            dependencies = Dependencies(false, clazz.containingFile!!),
            packageName = packageName,
            fileName = "${clazz.simpleName.asString()}Module",
        ).use { outputStream ->
            outputStream.writer().use { writer ->
                writer.write(koinModule)
            }
        }
    }

    /*

    import org.koin.core.qualifier.named
    import org.koin.dsl.module

    val scopeQualifier = named($scopeName)
    val ${className}Module = module {
        scope(scopeQualifier) {
            scoped { WorkTasksViewModel(get(), get()) }
        }
    }

    * */

}

/**
 * Get the value of the argument with the given name from the annotation arguments
 */
private fun Sequence<KSAnnotation>.getArgumentValue(argumentName: String) = this.find {
    it.shortName.asString() == KoinScopeWith::class.simpleName
}?.arguments?.find { it.name?.asString() == argumentName }?.value



