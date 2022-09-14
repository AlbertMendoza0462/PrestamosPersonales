package edu.ucne.prestamospersonales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.ucne.prestamospersonales.ui.Persona.PersonaScreen
import edu.ucne.prestamospersonales.ui.ocupacion.OcupacionScreen
import edu.ucne.prestamospersonales.ui.theme.PrestamosPersonalesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrestamosPersonalesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PersonaScreen(context = applicationContext, context1 = this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = name,
        fontSize = 30.sp,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrestamosPersonalesTheme {
        Greeting("Android")
    }
}