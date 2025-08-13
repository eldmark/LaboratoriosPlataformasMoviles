package com.dmark123.lab4
import androidx.compose.ui.Alignment
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dmark123.lab4.ui.theme.Lab4Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen1(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
val verdeOscuro = Color.Green.copy(
    red = Color.Green.red * 0.6f,
    green = Color.Green.green * 0.4f,
    blue = Color.Green.blue * 0.4f
)
@Composable
fun LogoUVG(modifier: Modifier = Modifier,opacity:Float=0.1f) {
    Box( modifier = modifier.fillMaxSize().padding(36.dp),
        contentAlignment = Alignment.Center){
    Image(
        painter = painterResource(id = R.drawable.logo_uvg),
        contentDescription = "Logo UVG",
        contentScale = ContentScale.FillBounds,
        modifier = modifier.alpha(opacity)


    )}
}


@Composable
fun Screen1(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LogoUVG(modifier = Modifier.fillMaxWidth().padding(16.dp), opacity = 0.2f)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(
                BorderStroke(4.dp, verdeOscuro),
                shape = RoundedCornerShape(0.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Universidad del Valle\n de Guatemala",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Programación de plataformas móviles, Sección 30",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "INTEGRANTES",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text("Marco Alejandro Díaz Castañeda")
                Text("Angel Gabriel Chávez Otzoy")
                Text("Fulano 1")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "CATEDRÁTICO",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Juan Carlos Durini",
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Marco Alejandro Díaz Castañeda")
            Text("Carné 24229")
        }
    }
}

