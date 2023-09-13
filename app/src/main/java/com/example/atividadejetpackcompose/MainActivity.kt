package com.example.atividadejetpackcompose

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atividadejetpackcompose.ui.theme.AtividadeJetpackComposeTheme
import com.example.atividadejetpackcompose.ui.theme.DebugButtonColors
import com.example.atividadejetpackcompose.ui.theme.ErrorButtonColors
import com.example.atividadejetpackcompose.ui.theme.InfoButtonColors
import com.example.atividadejetpackcompose.ui.theme.WarningButtonColors
import java.lang.RuntimeException

import androidx.activity.ComponentActivity
import androidx.annotation.RestrictTo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            AtividadeJetpackComposeTheme {

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                            Snackbar(
                                modifier = Modifier.padding(16.dp),
                                shape = RoundedCornerShape(8.dp),
                                snackbarData = snackbarData,
                                containerColor = Color(0xFF7AD4FD),
                                contentColor = Color.Black,
                                actionColor = Color(0xFF333536),
                                dismissActionContentColor = Color(0xFF333536),

                            )
                        }
                    },
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Aplicativo Botões",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color(0xFF333536)
                                )
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF7AD4FD)),
                            navigationIcon = {},
                            actions = {
                                IconButton(
                                    onClick = {
                                        showNotification(context, "Botão de Configuração clicado.", R.drawable.configuracao)
                                        Log.i(TAG, "Clicou em Configuração!")
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Botão de Configuração clicado.", duration = SnackbarDuration.Short, withDismissAction = true)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Build,
                                        contentDescription = null,
                                        tint = Color(0xFF333536)
                                    )
                                }
                            }
                        )
                    },
                    content = {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(80.dp))
                            // Lista de alarmes
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(1) { index ->
                                    App(snackbarHostState, scope)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun App(snackbarHostState: SnackbarHostState, scope: CoroutineScope) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardInfo(name = "Desenvolvido pela Google",
                description = "Framework de desenvolvimento de interface do usuário para aplicativos Android. ",
                date = "Criado em: 2019",
                R.drawable.icon_compose,
                titulo = "Jetpack Compose",
                snackbarHostState,
                scope,
                textnoti = "Clicou em Jetpack Compose.")
            Greeting("JetPack")
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                ActionButton(
                    text = "Debug",
                    buttonColors = DebugButtonColors(),
                ) {
                    // Debug button action
                    Log.d(TAG, "App: Clicou em DEBUG!")
                    showNotification(context, "Botão de Debug clicado.", R.drawable.debug)
                    scope.launch {
                        snackbarHostState.showSnackbar("Botão de Debug clicado.", duration = SnackbarDuration.Short, withDismissAction = true)
                    }
                }

                Spacer(modifier = Modifier.padding(end = 8.dp))

                ActionButton(
                    text = "Info",
                    buttonColors = InfoButtonColors(),
                ) {
                    Log.i(TAG, "Clicou em INFO!")
                    showNotification(context, "Botão de Info clicado.", R.drawable.info)
                    scope.launch {
                        snackbarHostState.showSnackbar("Botão de Info clicado.", duration = SnackbarDuration.Short, withDismissAction = true)
                    }
                }
            }

            Row() {
                ActionButton(
                    text = "Warning",
                    buttonColors = WarningButtonColors(),
                ) {
                    Log.w(TAG, "Clicou em WARNING!")
                    showNotification(context, "Botão de Warning clicado.", R.drawable.warning)
                    scope.launch {
                        snackbarHostState.showSnackbar("Botão de Warning clicado.", duration = SnackbarDuration.Short, withDismissAction = true)
                    }
                }

                Spacer(modifier = Modifier.padding(end = 8.dp))

                ActionButton(
                    text = "Error",
                    buttonColors = ErrorButtonColors(),
                ) {
                    Log.e(TAG, "Clicou em ERROR!")
                    showNotification(context, "Botão de Error clicado.", R.drawable.error)
                    scope.launch {
                        snackbarHostState.showSnackbar("Botão de Error clicado.", duration = SnackbarDuration.Short, withDismissAction = true)
                    }
                }
            }


                CardInfo(name = "Ana Beatriz Martins Batista",
                    description = "Registro de Matrícula: 22284",
                    date = "Aplicativo desenvolvido em: 13/09/2023",
                    R.drawable.menina,
                    titulo = "Desenvolvedora",
                    snackbarHostState,
                    scope,
                    textnoti = "Clicou em Desenvolvedora.")

        }
    }
}


private fun showNotification(context: Context, message: String, iconRes: Int) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle("Botão Clicado!")
        .setContentText(message)
        .setSmallIcon(iconRes)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(1, notification)
}


@Composable
fun CardInfo(
    name: String,
    description: String,
    date: String,
    iconRes: Int,
    titulo: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    textnoti: String,


) {
    val context = LocalContext.current
    var showInfo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clickable { showInfo = !showInfo
                Log.i(TAG, textnoti)
                showNotification(context, textnoti, iconRes)
                scope.launch {
                    snackbarHostState.showSnackbar(textnoti, duration = SnackbarDuration.Short, withDismissAction = true)
                }

            }
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
            .border(
                border = BorderStroke(2.dp, Color(0xFF7AD4FD)),
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Text(text = titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .padding(start = 18.dp, top = 18.dp)
                .fillMaxWidth(),
           textAlign = TextAlign.Center
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()) {

            Image(
                painter = painterResource(iconRes),
                contentDescription = stringResource(id = R.string.fotoperfil_content_description),
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxWidth()
            )

        }
        if (showInfo) {
            Column (  modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
             ) {


            Spacer(modifier = Modifier.width(8.dp))
            Text(text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFFFFFFF)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFFFFFFFF))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = date,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFFFFFFFF))
        }
        }
    }
}



@Composable
fun ActionButton(
    text: String,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    modifier: Modifier = Modifier,
    block: () -> Unit
) {
    Button(
        onClick = block,
        shape = MaterialTheme.shapes.medium,
        colors = buttonColors,
        modifier = modifier.width(150.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFFFFFFFF))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Aula de $name!",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        fontSize = 20.sp,
        color = Color(0xFFFFFFFF)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    AtividadeJetpackComposeTheme {
//        App()
//    }
//}

