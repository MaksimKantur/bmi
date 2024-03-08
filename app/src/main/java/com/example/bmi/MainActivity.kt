package com.example.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmi.ui.theme.BmiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BmiTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BMIApp(viewModel = viewModel())
                }
            }
        }
    }
}
class BMIViewModel : ViewModel() {
    var height = mutableStateOf("")
    var weight = mutableStateOf("")
    private var _bmi = mutableStateOf(0f)
    val bmi: Float
        get() = _bmi.value

    fun calculateBMI() {
        val heightInMeters = (height.value.toFloatOrNull() ?: 0f) / 100
        val weightInKg = weight.value.toFloatOrNull() ?: 0f
        if (heightInMeters > 0 && weightInKg > 0) {
            _bmi.value = weightInKg / (heightInMeters * heightInMeters)
        }
    }
}

@OptIn(ExperimentalMaterial3Api:: class)
@Composable
fun BMIApp(viewModel: BMIViewModel = viewModel()) {
    val height by viewModel.height
    val weight by viewModel.weight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = height,
            onValueChange = {viewModel.height.value = it},
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = {viewModel.weight.value = it},
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.calculateBMI() }) {
            Text("Calculate BMI")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Greeting(name = "Your BMI: ${viewModel.bmi}")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BmiTheme {
        BMIApp()
    }
}