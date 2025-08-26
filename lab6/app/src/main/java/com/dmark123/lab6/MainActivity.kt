package com.dmark123.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmark123.lab6.ui.theme.Lab6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen1(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Contador(
    counter: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "-",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Text(
                text = "$counter",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Button(
                onClick = onIncrement,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Composable
fun StatisticItem(name: String, value: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HistoryGrid(history: List<Pair<Int, String>>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(history.reversed()) { item ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = if (item.second == "incremento") Color.Green else Color.Red,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.first.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun Screen1(modifier: Modifier = Modifier) {
    var counter by rememberSaveable { mutableIntStateOf(0) }
    var totalIncrements by rememberSaveable { mutableIntStateOf(0) }
    var totalDecrements by rememberSaveable { mutableIntStateOf(0) }
    var maxValue by rememberSaveable { mutableIntStateOf(0) }
    var minValue by rememberSaveable { mutableIntStateOf(0) }
    var history by rememberSaveable { mutableStateOf<List<Pair<Int, String>>>(emptyList()) }

    fun updateStatistics(newValue: Int, type: String) {
        // Actualizar máximo y mínimo
        if (newValue > maxValue) maxValue = newValue
        if (history.isEmpty() || newValue < minValue) minValue = newValue

        // Agregar al historial
        history = history + Pair(newValue, type)
    }

    fun reset() {
        counter = 0
        totalIncrements = 0
        totalDecrements = 0
        maxValue = 0
        minValue = 0
        history = emptyList()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Marco Alejandro Díaz",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Contador(
            counter = counter,
            onIncrement = {
                counter++
                totalIncrements++
                updateStatistics(counter, "incremento")
            },
            onDecrement = {
                if (counter > 0) {
                    counter--
                    totalDecrements++
                    updateStatistics(counter, "decremento")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Estadísticas:",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            StatisticItem("Total increments:", totalIncrements)
            StatisticItem("Total decrements:", totalDecrements)
            StatisticItem("Valor máximo:", maxValue)
            StatisticItem("Valor mínimo:", minValue)
            StatisticItem("Total cambios:", totalIncrements + totalDecrements)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Historial:",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            if (history.isNotEmpty()) {
                HistoryGrid(
                    history = history,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Text(
                    text = "No hay historial aún",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = { reset() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Reiniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevScreen() {
    Lab6Theme {
        Screen1()
    }
}