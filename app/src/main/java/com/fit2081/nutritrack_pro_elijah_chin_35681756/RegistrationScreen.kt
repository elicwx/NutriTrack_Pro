package com.fit2081.nutritrack_pro_elijah_chin_35681756

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.RegistrationViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.ui.theme.NutriTrack_Pro_Elijah_Chin_35681756Theme
import kotlinx.coroutines.launch

class RegistrationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("") }
            var requiredPhoneNumber by remember { mutableStateOf("") }
            val snackbarHostState = remember { SnackbarHostState() }
            var name by remember { mutableStateOf("") }
            var userId by remember { mutableStateOf("") }

            val viewModel: RegistrationViewModel = viewModel(
                factory = RegistrationViewModel.RegistrationViewModelFactory(this@RegistrationScreen)
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
                        Spacer( modifier = Modifier.height(100.dp))

                        Text("Register",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        UserIdDropdownMenu(
                            viewModel,
                            onPhoneNumberSelected = { selectedPhone ->
                                requiredPhoneNumber = selectedPhone
                            },
                            onUserIdSelected = { selectedUserId ->
                                userId = selectedUserId
                            }
                        )

                        UserPhoneNumberDropdownMenu(
                            viewModel,
                            onPhoneNumberSelected = { selectedPhone ->
                                phoneNumber = selectedPhone
                            })

                        OutlinedTextField(

                            value = name,

                            onValueChange = { name = it },
                            label = { Text("Name") },
                            placeholder = { Text("Enter your name",
                                color = Color.Gray)},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.width(300.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

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

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(

                            value = confirmPassword,

                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm password") },
                            visualTransformation = PasswordVisualTransformation(),
                            placeholder = { Text("Enter your password again",
                                color = Color.Gray)},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.width(300.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        SaveRegistrationButton(this@RegistrationScreen, viewModel, userId, name,
                            password, confirmPassword, snackbarHostState)

                        Spacer(modifier = Modifier.height(16.dp))

                        LoginButton(this@RegistrationScreen)

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
fun UserIdDropdownMenu(
    viewModel: RegistrationViewModel,
    onPhoneNumberSelected: (String) -> Unit,
    onUserIdSelected: (String) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a User ID") }
    var phoneNumber by remember { mutableStateOf("") }
    val userIds by viewModel.userIds.collectAsState()

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
                userIds.forEachIndexed { index, userId ->
                    DropdownMenuItem(
                        text = { Text(userId) },
                        onClick = {
                            selectedOption = userId
                            onUserIdSelected(userId)
                            expanded = false

                            viewModel.getSpecificPhoneNumber(userId) { returnedPhoneNumber ->
                                phoneNumber = returnedPhoneNumber ?: "Not found"
                                onPhoneNumberSelected(phoneNumber)
                            }
                        }
                    )
                    if (index < userIds.size - 1) {
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
fun UserPhoneNumberDropdownMenu(
    viewModel: RegistrationViewModel,
    onPhoneNumberSelected: (String) -> Unit)
{

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select Phone Number") }
    val phoneNumbers by viewModel.phoneNumbers.collectAsState()

    Box(modifier = Modifier.padding(16.dp)) {
        Column {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                label = { Text("Select Phone Number") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Phone Numbers")
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
                phoneNumbers.forEachIndexed { index, phoneNumber ->
                    DropdownMenuItem(
                        text = { Text(phoneNumber) },
                        onClick = {
                            selectedOption = phoneNumber
                            expanded = false

                            onPhoneNumberSelected(phoneNumber)

                        }
                    )
                    if (index < phoneNumbers.size - 1) {
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
fun SaveRegistrationButton(context: Context, viewModel: RegistrationViewModel, userId: String,
                           name: String, password: String, confirmPassword: String,
                           snackbarHostState: SnackbarHostState)
{
    val scope = rememberCoroutineScope()

    Button(
        onClick = {

            if (password != confirmPassword) {
                scope.launch {
                    snackbarHostState.showSnackbar("Passwords do not match! :/")
                }
            } else {

                viewModel.registerUser(
                    userId = userId,
                    registered = true,
                    name = name,
                    password = password
                )

                scope.launch {
                    snackbarHostState.showSnackbar("Successfully registered! :D")
                }

                context.startActivity((Intent(context, LogInScreen::class.java)))
            }

        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
    ) {
        Text(
            "Create account",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp).width(270.dp)
        )
    }
}

@Composable
fun LoginButton(context: Context)
{
    Button(
        onClick = {
            context.startActivity((Intent(context, LogInScreen::class.java)))
        },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
    ) {
        Text(
            "Back to Login",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(16.dp).width(270.dp)
        )
    }
}