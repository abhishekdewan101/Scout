package com.abhishek101.gametracker.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.abhishek101.gametracker.ui.theme.GameTrackerTheme

@Composable
fun MainActivity() {
    GameTrackerTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        style = TextStyle(fontSize = 48.sp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GameTrackerTheme {
        Greeting("Android")
    }
}
