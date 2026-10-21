package com.fit2081.nutritrack_pro_elijah_chin_35681756

import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.AuthManager
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.LogInViewModel
import kotlinx.coroutines.launch
import com.fit2081.nutritrack_pro_elijah_chin_35681756.ui.theme.NutriTrack_Pro_Elijah_Chin_35681756Theme

class LogInScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var password by remember { mutableStateOf("") }
            var requiredPassword by remember { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }

            val viewModel: LogInViewModel = viewModel(
                factory = LogInViewModel.LogInViewModelFactory(this@LogInScreen)
            )

            NutriTrack_Pro_Elijah_Chin_35681756Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                                Snackbar(
                                    snackbarData,
                                    modifier = Modifier
                                        .height(80.dp)
                                        .width(300.dp)
                                        .padding(horizontal = 30.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    containerColor = Color.Gray
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Column( modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Spacer( modifier = Modifier.height(200.dp))

                        Text("Log in",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        RegisteredUserIdDropdownMenu(
                            viewModel,
                            onPasswordSelected = { selectedPassword ->
                                requiredPassword = selectedPassword
                            },
                        )

                        OutlinedTextField(

                            value = password,

                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            placeholder = { Text("Enter your password",
                                color = Color.Gray)},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.width(300.dp)

                        )

                        Text(
                            text = "This app is only for pre-registered users. Please enter your ID" +
                                    " and password or Register to claim your account on your first visit.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 16.dp).width(350.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                            ContinueButton(this@LogInScreen, password, requiredPassword,
                                snackbarHostState, userId = AuthManager.getUserId()
                            )

                        Spacer(modifier = Modifier.height(16.dp))

                        RegisterButton(this@LogInScreen)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(350.dp)
                        ) {

                            Divider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(40.dp))

                            Divider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegisteredUserIdDropdownMenu(
    viewModel: LogInViewModel,
    onPasswordSelected: (String) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a User ID") }

    var password by remember { mutableStateOf("") }

    val registeredUserIds by viewModel.registeredUserIds.collectAsState()

    Box(modifier = Modifier) {
        Column {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                label = { Text("Select User ID") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "User IDs")
                    }
                },
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                registeredUserIds.forEachIndexed { index, userId ->
                    DropdownMenuItem(
                        text = { Text(userId) },
                        onClick = {
                            selectedOption = userId
                            expanded = false
                            AuthManager.login(userId)

                            viewModel.getPassword(userId) { returnedPassword ->
                                password = returnedPassword ?: "Not found"
                                onPasswordSelected(password)
                            }
                        }
                    )
                    if (index < registeredUserIds.size - 1) {
                        HorizontalDivider(
                            color = Color.LightGray,
                            modifier = Modifier
                                .width(110.dp)
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContinueButton(context: Context, password: String, requiredPassword: String,
                   snackbarHostState: SnackbarHostState, userId: String?)
{

    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                when {
                    (userId == null) -> {
                        snackbarHostState.showSnackbar("Please select user ID >:(")
                    }

                    password.isBlank() -> {
                        snackbarHostState.showSnackbar("Please enter password >:(")
                    }

                    password != requiredPassword -> {
                        snackbarHostState.showSnackbar("Incorrect password! :(")
                    }

                    else -> {
                        snackbarHostState.showSnackbar("Login successful! :)")
                        context.startActivity(Intent(context, QuestionnaireScreen::class.java))
                    }
                }
            }
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
    ) {
        Text(
            "Continue",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp).width(270.dp)
        )
    }
}

@Composable
fun RegisterButton(context: Context)
{
    Button(
        onClick = {
            context.startActivity((Intent(context, RegistrationScreen::class.java)))
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
    ) {
        Text(
            "Register",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp).width(270.dp)
        )
    }
}