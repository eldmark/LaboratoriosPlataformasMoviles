package com.dmark123.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.Lab5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                RestauranteScreen()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RestauranteScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Barra de actualización
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Actualizar",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Actualización disponible",
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                )
                context.startActivity(intent)
            }) {
                Text("Descargar")
            }
        }

        // Fecha y botón terminar jornada
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Domingo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "21 de septiembre",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            OutlinedButton(onClick = {
            }) {
                Text("Terminar jornada")
            }
        }

        // Tarjeta del restaurante
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "McDonald's",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Km 29.5 Carretera Interamericana Centro Comercial Las Puertas",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "6:00AM 11:00PM",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    IconButton(onClick = {
                        val gmmIntentUri = Uri.parse("geo:14.60913497112332, -90.65899768408708")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Direcciones",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            Toast.makeText(context, "Marco Alejandro Díaz Castañeda", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text("Iniciar")
                    }
                    TextButton(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Comida Rápida Americana\nPrecio: QQ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {
                        Text("Detalles")
                    }
                }
            }
        }
    }
}
