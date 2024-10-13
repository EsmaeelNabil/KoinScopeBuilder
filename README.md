# KoinScopeBuilder


#### use this annotation on top of your classes

```kotlin
@KoinScopeWith(scopeName = "HomeViewModelScope")
class HomeViewModel //the rest of your implementation
```


#### add your module to the Koin module list
```kotlin

val appModule = listOf(
    // .. other modules

    //this is generated for you
    homeViewModelModule 
)
```

#### use the creator function to get your view model scoped to a specific key

```kotlin
val vm = getOrCreateHomeViewModel(key = "someKey")
```


#### features to be added:
[] visibility options in the annotation
 

<br>
<br>
<br>
<br>

----

#### behind the scenes generated code

<br>
<br>
<br>

```kotlin

private val scopeName = "HomeViewModelScope"

val homeViewModelModule = module {
    scope(named(scopeName)) {
        scopedOf(::HomeViewModel)
    }
}

/**
* Ensures the appropriate lifecycle management for a [HomeViewModel]
* disposing the Koin scope upon composable exit.
* This function initializes the view model before it is utilized.
* @param key : The unique identifier of the workgroup.
* @return A [HomeViewModel] scoped to the specified workgroup.
*/

@Composable
fun getOrCreateHomeViewModel(key: Any? = null): HomeViewModel {

    DisposableEffect(key) {
        onDispose {
            getKoin().getScopeOrNull(scopeName)?.close()
        }
    }

    return remember(key) {
        (getKoin().getScopeOrNull(scopeName) ?: getKoin().createScope(
            scopeName,
            named(scopeName)
        )).get<HomeViewModel>()
    }
}

```

