package com.victor.animatedwaterbottle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victor.animatedwaterbottle.ui.theme.AnimatedWaterBottleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedWaterBottleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WaterBottleComponent()
                }
            }
        }
    }
}

@Composable
fun WaterBottleComponent() {
    var waterAmount by remember {
        mutableIntStateOf(240)
    }
    val totalAmountOfWater = 2400
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        WaterBottle(
            modifier = Modifier.width(250.dp),
            totalAmount = totalAmountOfWater,
            waterAmount = waterAmount,
            unit = "ml"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (waterAmount + 240 <= totalAmountOfWater)
                    waterAmount += 240
            }
        ) {
            Text(text = "Add water")
        }
        Button(
            onClick = {
                if (waterAmount - 240 >= 0)
                    waterAmount -= 240
            }
        ) {
            Text(text = "Drop water")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimatedWaterBottleTheme {
        WaterBottleComponent()
    }
}