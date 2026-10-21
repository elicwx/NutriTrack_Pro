package com.fit2081.nutritrack_pro_elijah_chin_35681756.data

import androidx.compose.runtime.Composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.NutriCoachViewModel
import kotlinx.coroutines.launch

@Composable
fun NutriCoachFruityApiComposable(nutriCoachViewModel: NutriCoachViewModel = viewModel(),
                                  snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var fruitInput by remember { mutableStateOf("") }
    val fruitState by nutriCoachViewModel.fruit.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            nutriCoachViewModel.clearFruitCards()
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = "Fruit Name",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            OutlinedTextField(

                value = fruitInput,

                onValueChange = { fruitInput = it },
                placeholder = {
                    Text("Enter a fruit",
                    color = Color.Gray,
                        fontSize = 15.sp)},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    if (fruitInput.isBlank()){
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter a fruit!")
                        }
                    } else {
                        nutriCoachViewModel.getFruitByName(fruitInput.trim())
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                modifier = Modifier.height(50.dp)
            ) {
                Icon(
                    Icons.Filled.Search, "Fruity API search",
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Details",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )

            }
        }

        fruitState?.let { fruit ->

            val nutritionList = mapOf(
                "Carbohydrates" to fruit.nutritions.carbohydrates.toFloat(),
                "Protein" to fruit.nutritions.protein.toFloat(),
                "Fat" to fruit.nutritions.fat.toFloat(),
                "Calories" to fruit.nutritions.calories.toFloat(),
                "Sugar" to fruit.nutritions.sugar.toFloat()
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(2.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                    ) {
                        Text(
                            text = "Family",
                            textAlign = TextAlign.Left,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        Text(
                            text = ":  ${fruit.family}",
                            textAlign = TextAlign.Left,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                for ((name, value) in nutritionList) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = name,
                                textAlign = TextAlign.Left,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(8.dp)
                            )

                            Text(
                                text = ":  $value",
                                textAlign = TextAlign.Left,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}