package  com.dmark123.lab7

import Notification
import NotificationType
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import generateFakeNotifications
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationsScreen()
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen() {
    // Estado para las notificaciones
    var notifications by remember { mutableStateOf(emptyList<Notification>()) }
    var isLoading by remember { mutableStateOf(true) }

    // Estado para los filtros seleccionados
    var showGeneral by remember { mutableStateOf(true) }
    var showNewMeeting by remember { mutableStateOf(true) }

    // Cargar notificaciones al iniciar
    LaunchedEffect(Unit) {
        isLoading = true
        delay(1000) // Simular carga de datos
        notifications = generateFakeNotifications()
        isLoading = false
    }

    // Filtrar notificaciones según los tipos seleccionados
    val filteredNotifications = notifications.filter { notification ->
        (showGeneral && notification.type == NotificationType.GENERAL) ||
                (showNewMeeting && notification.type == NotificationType.NEW_MEETING)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Título de la pantalla
        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Filtros por tipo de notificación
        Text(
            text = "Tipos de notificaciones",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        )

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            FilterChip(
                selected = showGeneral,
                onClick = { showGeneral = !showGeneral },
                leadingIcon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Generales"
                    )
                },
                label = { Text("Generales") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                selected = showNewMeeting,
                onClick = { showNewMeeting = !showNewMeeting },
                leadingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Nuevas Reuniones"
                    )
                },
                label = { Text("Nuevas Reuniones") }
            )
        }

        // Mostrar indicador de carga si está cargando
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        // Mostrar lista de notificaciones o mensaje vacío
        else if (filteredNotifications.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredNotifications) { notification ->
                    NotificationItem(notification = notification)
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay notificaciones para mostrar")
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (notification.type) {
                NotificationType.GENERAL -> MaterialTheme.colorScheme.primaryContainer
                NotificationType.NEW_MEETING -> MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Icono, título y fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono según el tipo
                Icon(
                    imageVector = when (notification.type) {
                        NotificationType.GENERAL -> Icons.Default.Notifications
                        NotificationType.NEW_MEETING -> Icons.Default.DateRange
                    },
                    contentDescription = when (notification.type) {
                        NotificationType.GENERAL -> "Notificación general"
                        NotificationType.NEW_MEETING -> "Nueva reunión"
                    },
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Título y fecha
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = notification.sendAt,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            Text(
                text = notification.body,
                style = MaterialTheme.typography.bodyMedium
            )

            // Indicador de tipo
            Text(
                text = when (notification.type) {
                    NotificationType.GENERAL -> "General"
                    NotificationType.NEW_MEETING -> "Nueva Reunión"
                },
                style = MaterialTheme.typography.labelSmall,
                color = when (notification.type) {
                    NotificationType.GENERAL -> MaterialTheme.colorScheme.primary
                    NotificationType.NEW_MEETING -> MaterialTheme.colorScheme.secondary
                },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

// Versión con lazy loading para cargar 4 notificaciones inicialmente
@Composable
fun LazyLoadingNotificationScreen() {
    var notifications by remember { mutableStateOf(emptyList<Notification>()) }
    var isLoading by remember { mutableStateOf(true) }
    var visibleCount by remember { mutableStateOf(4) } // Cargar 4 notificaciones inicialmente

    // Cargar notificaciones al iniciar
    LaunchedEffect(Unit) {
        isLoading = true
        delay(1000) // Simular carga de datos
        notifications = generateFakeNotifications()
        isLoading = false
    }

    val notificationsToShow = notifications.take(visibleCount)

    Column(modifier = Modifier.fillMaxSize()) {
        // Título y filtros (igual que en NotificationsScreen)
        Text(
            text = "Notificaciones",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(notificationsToShow) { notification ->
                    NotificationItem(notification = notification)
                }

                item {
                    if (visibleCount < notifications.size) {
                        Button(
                            onClick = {
                                visibleCount += 4 // Cargar 4 notificaciones más
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text("Cargar más notificaciones")
                        }
                    }
                }
            }
        }
    }
}