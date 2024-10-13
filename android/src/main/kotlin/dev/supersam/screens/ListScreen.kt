package dev.supersam.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import getOrCreateHomeViewModel

@Composable
fun ListScreen(
    id: Int,
    modifier: Modifier = Modifier,
    openDetails: () -> Unit
) {

    val vm = getOrCreateHomeViewModel(key = id)

    Column(
        modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("List Screen $id")
        Text(
            vm.hashCode().toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
        Text(
            vm.message,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.padding(16.dp))
        TextField(
            value = vm.message,
            onValueChange = { vm.message = it },
            label = { Text("Enter a message") }
        )

        Button(openDetails) {
            Text("Open Details")
        }

    }
}
