package com.fit2081.nutritrack_pro_elijah_chin_35681756

import android.app.TimePickerDialog
import android.os.Bundle
import android.content.Intent
import android.icu.util.Calendar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.AuthManager
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.FoodIntake
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.QuestionnaireViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.ui.theme.NutriTrack_Pro_Elijah_Chin_35681756Theme
import kotlinx.coroutines.launch

class QuestionnaireScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: QuestionnaireViewModel = viewModel(
                factory = QuestionnaireViewModel.QuestionnaireViewModelFactory(this@QuestionnaireScreen)
            )

            val snackbarHostState = remember { SnackbarHostState() }

            val scope = rememberCoroutineScope()

            val patientId = AuthManager.getUserId()

            LaunchedEffect(patientId) {
                patientId?.let { viewModel.getFoodIntake(it) }
            }

            val foodIntake by viewModel.foodIntake.collectAsState()

            val context = LocalContext.current

            val CheckBoxFruits = remember { mutableStateOf(false) }
            val CheckBoxVege = remember { mutableStateOf(false) }
            val CheckBoxGrains = remember { mutableStateOf(false) }
            val CheckBoxMeat = remember { mutableStateOf(false) }
            val CheckBoxSeafood = remember { mutableStateOf(false) }
            val CheckBoxPoultry = remember { mutableStateOf(false) }
            val CheckBoxFish = remember { mutableStateOf(false) }
            val CheckBoxEggs = remember { mutableStateOf(false) }
            val CheckBoxNutsSeeds = remember { mutableStateOf(false) }

            val TimeEat = remember { mutableStateOf("00:00") }
            val TimeNight = remember { mutableStateOf("00:00") }
            val TimeWake = remember { mutableStateOf("00:00") }

            var timePickerDialog1 = timePickerFun(TimeEat)
            var timePickerDialog2 = timePickerFun(TimeNight)
            var timePickerDialog3 = timePickerFun(TimeWake)

            var selectedOption by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var healthDevotee = "Health Devotee"
            var mindfulEater = "Mindful Eater"
            var wellnessStriver = "Wellness Striver"
            var balanceSeeker = "Balance Seeker"
            var healthProcrastinator = "Health Procrastinator"
            var foodCarefree = "Food Carefree"

            LaunchedEffect(foodIntake) {
                foodIntake?.let {
                    CheckBoxFruits.value = it.checkBoxFruits
                    CheckBoxVege.value = it.checkBoxVegetable
                    CheckBoxGrains.value = it.checkBoxGrains
                    CheckBoxMeat.value = it.checkBoxMeat
                    CheckBoxSeafood.value = it.checkBoxSeafood
                    CheckBoxPoultry.value = it.checkBoxPoultry
                    CheckBoxFish.value = it.checkBoxFish
                    CheckBoxEggs.value = it.checkBoxEggs
                    CheckBoxNutsSeeds.value = it.checkBoxNutsSeeds
                    selectedOption = it.selectedPersona
                    TimeEat.value = it.timeEat
                    TimeNight.value = it.timeNight
                    TimeWake.value = it.timeWake
                }
            }

            NutriTrack_Pro_Elijah_Chin_35681756Theme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = { TopBar() },
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
                    }) { innerPadding ->
                    Box {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Tick all the food categories you can eat",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 40.dp)
                                    .align(Alignment.Start)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = CheckBoxFruits.value,
                                    onCheckedChange = { CheckBoxFruits.value = it }
                                )
                                Text(text = "Fruits")

                                Spacer(modifier = Modifier.width(10.dp))

                                Checkbox(
                                    checked = CheckBoxVege.value,
                                    onCheckedChange = { CheckBoxVege.value = it }
                                )
                                Text(text = "Vegetables")
                                Spacer(modifier = Modifier.width(10.dp))

                                Checkbox(
                                    checked = CheckBoxGrains.value,
                                    onCheckedChange = { CheckBoxGrains.value = it }
                                )
                                Text(text = "Grains")
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.width(16.dp))

                                Checkbox(
                                    checked = CheckBoxMeat.value,
                                    onCheckedChange = { CheckBoxMeat.value = it }
                                )
                                Text(text = "Red Meat")

                                Spacer(modifier = Modifier.width(8.dp))

                                Checkbox(
                                    checked = CheckBoxSeafood.value,
                                    onCheckedChange = { CheckBoxSeafood.value = it }
                                )
                                Text(text = "Seafood")

                                Spacer(modifier = Modifier.width(8.dp))

                                Checkbox(
                                    checked = CheckBoxPoultry.value,
                                    onCheckedChange = { CheckBoxPoultry.value = it }
                                )
                                Text(text = "Poultry")
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Spacer(modifier = Modifier.width(16.dp))

                                Checkbox(
                                    checked = CheckBoxFish.value,
                                    onCheckedChange = { CheckBoxFish.value = it }
                                )
                                Text(text = "Fish")

                                Spacer(modifier = Modifier.width(10.dp))

                                Checkbox(
                                    checked = CheckBoxEggs.value,
                                    onCheckedChange = { CheckBoxEggs.value = it }
                                )
                                Text(text = "Eggs")

                                Spacer(modifier = Modifier.width(10.dp))

                                Checkbox(
                                    checked = CheckBoxNutsSeeds.value,
                                    onCheckedChange = { CheckBoxNutsSeeds.value = it }
                                )
                                Text(text = "Nuts/Seeds")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Personas()

                            Spacer(modifier = Modifier.height(8.dp))

                            Divider(
                                color = Color.Gray.copy(alpha = 0.5f),
                                thickness = 1.dp,
                                modifier = Modifier.width(400.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Which persona best fits you?",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 40.dp)
                                    .align(Alignment.Start)
                            )

                            Box {
                                OutlinedTextField(
                                    value = selectedOption,
                                    onValueChange = {},
                                    label = { Text("Select option") },
                                    readOnly = true,
                                    trailingIcon = {
                                        IconButton(onClick = { expanded = true }) {
                                            Icon(
                                                Icons.Default.ArrowDropDown,
                                                contentDescription = "Personas"
                                            )
                                        }
                                    },
                                    modifier = Modifier.width(300.dp),
                                    shape = RoundedCornerShape(30.dp)
                                )

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    )
                                    {
                                        DropdownMenuItem(
                                            text = { Text(healthDevotee) },
                                            onClick = {
                                                selectedOption = healthDevotee
                                                expanded = false
                                            }
                                        )

                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier.width(200.dp)
                                                .padding(horizontal = 8.dp)
                                        )

                                        DropdownMenuItem(
                                            text = { Text(mindfulEater) },
                                            onClick = {
                                                selectedOption = mindfulEater
                                                expanded = false
                                            }
                                        )

                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier.width(200.dp)
                                                .padding(horizontal = 8.dp)
                                        )

                                        DropdownMenuItem(
                                            text = { Text(wellnessStriver) },
                                            onClick = {
                                                selectedOption = wellnessStriver
                                                expanded = false
                                            }
                                        )

                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier.width(200.dp)
                                                .padding(horizontal = 8.dp)
                                        )

                                        DropdownMenuItem(
                                            text = { Text(balanceSeeker) },
                                            onClick = {
                                                selectedOption = balanceSeeker
                                                expanded = false
                                            }
                                        )

                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier.width(200.dp)
                                                .padding(horizontal = 8.dp)
                                        )

                                        DropdownMenuItem(
                                            text = { Text(healthProcrastinator) },
                                            onClick = {
                                                selectedOption = healthProcrastinator
                                                expanded = false
                                            }
                                        )

                                        HorizontalDivider(
                                            color = Color.LightGray,
                                            modifier = Modifier.width(200.dp)
                                                .padding(horizontal = 8.dp)
                                        )

                                        DropdownMenuItem(
                                            text = { Text(foodCarefree) },
                                            onClick = {
                                                selectedOption = foodCarefree
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Timings",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                //textAlign = TextAlign.Left,
                                modifier = Modifier.padding(horizontal = 40.dp)
                                    .align(Alignment.Start)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "What time of day approx. do you\n" +
                                            "normally eat your biggest meal?",
                                    fontSize = 13.sp,
                                    modifier = Modifier.width(200.dp)
                                )

                                Spacer(modifier = Modifier.width(40.dp))

                                Button(
                                    onClick = {
                                        timePickerDialog1.show()
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                                ) {
                                    Text(
                                        text = "${TimeEat.value}",
                                        color = Color.DarkGray
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "What time of day approx. do you\n" +
                                            "go to sleep at night?",
                                    fontSize = 13.sp,
                                    modifier = Modifier.width(200.dp)
                                )

                                Spacer(modifier = Modifier.width(40.dp))

                                Button(
                                    onClick = {
                                        timePickerDialog2.show()
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                                ) {
                                    Text(
                                        text = "${TimeNight.value}",
                                        color = Color.DarkGray
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "What time of day approx. do you\n" +
                                            "wake up in the morning?",
                                    fontSize = 13.sp,
                                    modifier = Modifier.width(200.dp)
                                )

                                Spacer(modifier = Modifier.width(40.dp))

                                Button(
                                    onClick = {
                                        timePickerDialog3.show()
                                    },
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                                ) {
                                    Text(
                                        text = "${TimeWake.value}",
                                        color = Color.DarkGray
                                    )
                                }
                            }

                            Button(onClick = {
                                val foodIntake = patientId?.let {
                                    FoodIntake(
                                        patientId = it,
                                        checkBoxFruits = CheckBoxFruits.value,
                                        checkBoxVegetable = CheckBoxVege.value,
                                        checkBoxGrains = CheckBoxGrains.value,
                                        checkBoxMeat = CheckBoxMeat.value,
                                        checkBoxSeafood = CheckBoxSeafood.value,
                                        checkBoxPoultry = CheckBoxPoultry.value,
                                        checkBoxFish = CheckBoxFish.value,
                                        checkBoxEggs = CheckBoxEggs.value,
                                        checkBoxNutsSeeds = CheckBoxNutsSeeds.value,
                                        selectedPersona = selectedOption,
                                        timeEat = TimeEat.value,
                                        timeNight = TimeNight.value,
                                        timeWake = TimeWake.value
                                    )
                                }

                                if (foodIntake != null) {
                                    viewModel.setFoodIntake(foodIntake)
                                }

                                val intent = Intent(context, MainActivity::class.java)
                                intent.putExtra("startDestination", "home")
                                startActivity(intent)

                                scope.launch {
                                    snackbarHostState.showSnackbar("Food preferences saved! :D")
                                }
                            },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                                Text(text = "Save",
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar()
{
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Food Intake Questionnaire",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    AuthManager.logout()
                    context.startActivity(Intent(context, LogInScreen::class.java))
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}


@Composable
fun Personas() {
    var showDialog by remember { mutableStateOf(false)}
    var selectedOption by remember { mutableStateOf(0)}

    Text(
        text = "Your Persona",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 40.dp)
    )

    Text(
        text = "People can be broadly classified into 6 different types based on\n" +
                "their eating preferences. Click on each button below to find out\n" +
                "the different types, and select the type that best fits you!",
        fontSize = 11.sp,
        modifier = Modifier.padding(horizontal = 40.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                showDialog = true
                selectedOption = 1
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)

        ) {
            Text(
                text = "Health Devotee",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                showDialog = true
                selectedOption = 2
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)

        ) {
            Text(
                text = "Mindful Eater",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                showDialog = true
                selectedOption = 3
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)

        ) {
            Text(
                text = "Wellness Striver",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                showDialog = true
                selectedOption = 4
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)


        ) {
            Text(
                text = "Balance Seeker",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                showDialog = true
                selectedOption = 5
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(
                text = "Health Procrastinator",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                showDialog = true
                selectedOption = 6
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(
                text = "Food CareFree",
                fontSize = 9.sp,
                color = Color.DarkGray
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("")},
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    if (selectedOption == 1) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_1),
                            contentDescription = "Persona 1",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Health Devotee",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I’m passionate about healthy eating & health plays a big" +
                                    " part in my life. I use social media to follow active lifestyle" +
                                    " personalities or get new recipes/exercise ideas. I may even" +
                                    " buy superfoods or follow a particular type of diet. I like to" +
                                    " think I am super healthy.",
                            fontSize = 12.sp
                        )
                    }

                    if (selectedOption == 2) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_2),
                            contentDescription = "Persona 2",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Mindful Eater",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I’m health-conscious and being healthy and eating healthy " +
                                    "is important to me. Although health means different things " +
                                    "to different people, I make conscious lifestyle decisions" +
                                    " about eating based on what I believe healthy means. I look" +
                                    " for new recipes and healthy eating information on social media.",
                            fontSize = 12.sp
                        )
                    }

                    if (selectedOption == 3) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_3),
                            contentDescription = "Persona 3",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Wellness Striver",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I aspire to be healthy (but struggle sometimes). Healthy " +
                                    "eating is hard work! I’ve tried to improve my diet, but " +
                                    "always find things that make it difficult to stick with the " +
                                    "changes. Sometimes I notice recipe ideas or healthy eating " +
                                    "hacks, and if it seems easy enough, I’ll give it a go.",
                            fontSize = 12.sp
                        )
                    }

                    if (selectedOption == 4) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_4),
                            contentDescription = "Persona 4",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Balance Seeker",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I try and live a balanced lifestyle, and I think that " +
                                    "all foods are okay in moderation. I shouldn’t have to " +
                                    "feel guilty about eating a piece of cake now and again. " +
                                    "I get all sorts of inspiration from social media like " +
                                    "finding out about new restaurants, fun recipes and sometimes " +
                                    "healthy eating tips.",
                            fontSize = 12.sp
                        )
                    }

                    if (selectedOption == 5) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_5),
                            contentDescription = "Persona 5",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Health Procrastinator",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I’m contemplating healthy eating but it’s not a priority " +
                                    "for me right now. I know the basics about what it means " +
                                    "to be healthy, but it doesn’t seem relevant to me right " +
                                    "now. I have taken a few steps to be healthier but I am not " +
                                    "motivated to make it a high priority because I have too " +
                                    "many other things going on in my life.",
                            fontSize = 12.sp
                        )
                    }

                    if (selectedOption == 6) {
                        Image(
                            painter = painterResource(id = R.drawable.persona_6),
                            contentDescription = "Persona 6",
                            modifier = Modifier.size(200.dp).padding(15.dp)
                        )

                        Text(
                            text = "Health Procrastinator",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "I’m not bothered about healthy eating. I don’t really see " +
                                    "the point and I don’t think about it. I don’t really notice " +
                                    "healthy eating tips or recipes and I don’t care what I eat.",
                            fontSize = 12.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false},
                    colors =  ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                    Text(text = "Close",
                        color = Color.DarkGray)
                }
            }
        )
    }
}


@Composable
fun timePickerFun(mTime: MutableState<String>): TimePickerDialog {

    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    return TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )
}