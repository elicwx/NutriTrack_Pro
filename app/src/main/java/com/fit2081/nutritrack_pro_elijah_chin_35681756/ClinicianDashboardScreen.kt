package com.fit2081.nutritrack_pro_elijah_chin_35681756

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.genai.UiState
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.ClinicianDashViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.GenAiViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.ui.theme.NutriTrack_Pro_Elijah_Chin_35681756Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.ViewModelProvider
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.Patient


class ClinicianDashboardScreen: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val snackbarHostState = remember { SnackbarHostState() }

            val viewModel: ClinicianDashViewModel = viewModel(
                factory = ClinicianDashViewModel.ClinicianDashViewModelFactory(this@ClinicianDashboardScreen)
            )

            /**
             * ChatGPT was used to generate the following code to get GenAiViewModel instances to use.
             */
            val genAiViewModel: GenAiViewModel = ViewModelProvider(
                this, GenAiViewModel.GenAiViewModelFactory(this@ClinicianDashboardScreen)
            )[GenAiViewModel::class.java]

            var averageMaleScore by remember { mutableStateOf("") }
            var averageFemaleScore by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                averageMaleScore = viewModel.getAverageMaleScore("Male")
                averageFemaleScore = viewModel.getAverageFemaleScore("Female")
            }

            val context = LocalContext.current

            NutriTrack_Pro_Elijah_Chin_35681756Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
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

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(innerPadding)
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(30.dp))

                            Text(
                                text = "Clinician Dashboard",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                border = BorderStroke(1.dp, Color.Gray),
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "Average HEIFA (Male):",
                                        modifier = Modifier.padding(16.dp)
                                    )

                                    Text(
                                        text = averageMaleScore,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                border = BorderStroke(1.dp, Color.Gray),
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "Average HEIFA (Female):",
                                        modifier = Modifier.padding(16.dp)
                                    )

                                    Text(
                                        text = averageFemaleScore,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.width(350.dp)
                            ) {

                                Divider(
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    thickness = 1.dp,
                                    modifier = Modifier.weight(1f)
                                )

                            }

                            GenAiSection(genAiViewModel)

                        }
                        Button(
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                intent.putExtra("startDestination", "settings")
                                startActivity(intent)
                                      },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = "Done",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenAiSection(genAiViewModel: GenAiViewModel = viewModel())
{
    val uiState by genAiViewModel.uiState.collectAsState()

    var patients by remember { mutableStateOf<List<Patient>>(emptyList()) }

    LaunchedEffect(Unit) {
        patients = genAiViewModel.getAllPatientData()
    }

    val patientText = patients.joinToString("\n") { patient ->
        """
                    Name: ${patient.name}, 
                    Gender: ${patient.sex}, 
                    HEIFA Score: ${patient.totalFoodQualityScore}, 
                    Vegetable Score: ${patient.vegetableScore}, 
                    Fruit Score: ${patient.fruitsScore}, 
                    Grains and Cereals Score: ${patient.grainsAndCerealsScore}, 
                    Whole Grains Score: ${patient.wholeGrainsScore}, 
                    Meat and Alternatives Score: ${patient.meatAndAlternativesScore}, 
                    Dairy Score: ${patient.dairyScore}, 
                    Water Score: ${patient.waterScore}, 
                    Saturated Fats Score: ${patient.saturatedFatsScore}, 
                    Unsaturated Fats Score: ${patient.unsaturatedFatsScore}, 
                    Sodium Score: ${patient.sodiumScore}, 
                    Sugar Score: ${patient.sugarScore}, 
                    Alcohol Score: ${patient.alcoholScore}, 
                    Discretionary Foods Score: ${patient.discretionaryFoodsScore}
                    """.trimIndent()
    }

    val prompt = """
                    Given this patient nutritional data:
                    
                    $patientText
                    
                    Give me three random key interesting patterns in 2 sentences. Start each pattern
                    with the topic (e.g. "Topic title:") without any bolded lines. Let there 
                    be an empty line in between each pattern"

                """.trimIndent()

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Button(
            onClick = {
                genAiViewModel.sendPrompt(prompt)
            },
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ){
            Icon(Icons.Filled.Search, "Find Data Pattern", tint = Color.Gray)

            Text(
                text = "Find Data Pattern",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }

    if (uiState is UiState.Loading) {
        CircularProgressIndicator()
    } else if (uiState is UiState.Success){
        val textColor = MaterialTheme.colorScheme.onSurface
        val results = (uiState as UiState.Success).outputText

        LazyColumn(){
            items(results) { pattern ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                ) {
                    Text(
                        text = pattern,
                        textAlign = TextAlign.Left,
                        color = textColor,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}




