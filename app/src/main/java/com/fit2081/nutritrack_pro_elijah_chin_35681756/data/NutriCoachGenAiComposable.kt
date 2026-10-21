package com.fit2081.nutritrack_pro_elijah_chin_35681756.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.genai.UiState
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips.NutriCoachTips
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.Patient
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.GenAiViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.NutriCoachViewModel

@Composable
fun NutriCoachGenAiComposable(genAiViewModel: GenAiViewModel = viewModel(),
                              nutriCoachViewModel: NutriCoachViewModel = viewModel(), userId: String) {

    val uiState by genAiViewModel.uiState.collectAsState()

    var patients by remember { mutableStateOf<List<Patient>>(emptyList()) }

    var showDialog by remember { mutableStateOf(false)}

    LaunchedEffect(Unit) {
        patients = genAiViewModel.getAllPatientData()
    }

    val patientTips by nutriCoachViewModel.getPatientTips(userId).collectAsState(initial = emptyList())

    val prompt = "Generate a unique short encouraging message to help someone improve their fruit intake" +
            " that you have never generated before."

    Box(
        modifier = Modifier.padding(16.dp).fillMaxSize(),

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Button(
                onClick = {
                    genAiViewModel.sendPrompt(prompt)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)

            ) {
                Icon(
                    Icons.Filled.MailOutline, "Motivational Message",
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Motivational Message (AI)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

            }

            if (uiState is UiState.Loading) {
                CircularProgressIndicator()
            } else if (uiState is UiState.Success) {
                val textColor = MaterialTheme.colorScheme.onSurface
                val results = (uiState as UiState.Success).outputText

                LazyColumn() {
                    items(results) { tip ->
                        Text(
                            text = tip,
                            textAlign = TextAlign.Left,
                            color = textColor,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                val combinedString = results.joinToString(separator = "")

                val tip = NutriCoachTips(userId = userId, tipEntry = combinedString)

                LaunchedEffect(tip) {
                    nutriCoachViewModel.insertPatientTips(tip)
                }

            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("AI Tips")},
                text = {
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(max = 350.dp)
                    ){
                        items(patientTips) { tip ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                border = BorderStroke(1.dp, Color.Gray),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                Text(
                                    text = tip,
                                    textAlign = TextAlign.Left,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false},
                        colors = ButtonDefaults.buttonColors(Color.Green))
                    {
                        Text(text = "Done",
                            color = Color.Gray)
                    }
                }
            )
        }


        Button(
            onClick = {
                showDialog = true
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Tip Bank",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}
