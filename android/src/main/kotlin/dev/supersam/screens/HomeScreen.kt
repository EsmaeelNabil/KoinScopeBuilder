package dev.supersam.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun HomeScreen(
    openDetails: () -> Unit,
    openList: () -> Unit
) {

    val id = 1
    val vm = getOrCreateHomeViewModel(id)

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Home Screen, key: $id")
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                value = vm.message,
                onValueChange = { vm.message = it },
                label = { Text("Enter a message") }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                vm.hashCode().toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = vm.message,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        }


        Spacer(modifier = Modifier.padding(16.dp))
        Button(openDetails) {
            Text("Open Details")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        Button(openList) {
            Text("Open List")
        }

    }
}
