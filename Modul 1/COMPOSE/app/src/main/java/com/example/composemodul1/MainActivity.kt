package com.example.composemodul1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.example.composemodul1.R
import com.example.composemodul1.ui.theme.ComposeModul1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeModul1Theme  {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    DiceRollerScreen()
}

@Composable
fun DiceRollerScreen(modifier: Modifier = Modifier. fillMaxSize() .wrapContentSize(Alignment.Center)) {
    var result1 by remember {mutableStateOf(0)}
    var result2 by remember {mutableStateOf(0)}
    var message by remember { mutableStateOf("") }

    val imageResource1 = when (result1) {
        0 -> R.drawable.dice_0
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    val imageResource2 = when (result2) {
        0 -> R.drawable.dice_0
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Image(
                    painter = painterResource(imageResource1),
                    contentDescription = result1.toString()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(imageResource2),
                    contentDescription = result2.toString()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    result1 = (1..6).random()
                    result2 = (1..6).random()
                    message = if (result1 == result2)
                        "Selamat, anda dapat dadu double!"
                    else
                        "Anda belum beruntung!"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD0BCFF)
                )
            ) {
                Text("Roll", color = Color.Black)
            }
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(13.dp),
                color = Color.Black,
                fontSize = 15.sp
            )
        }
    }
}