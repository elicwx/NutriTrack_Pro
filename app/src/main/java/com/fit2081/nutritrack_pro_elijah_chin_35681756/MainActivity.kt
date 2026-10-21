package com.fit2081.nutritrack_pro_elijah_chin_35681756

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.AuthManager
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.NutriCoachFruityApiComposable
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.NutriCoachGenAiComposable
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.GenAiViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.HomeViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.InsightsViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.NutriCoachViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels.SettingsViewModel
import com.fit2081.nutritrack_pro_elijah_chin_35681756.ui.theme.NutriTrack_Pro_Elijah_Chin_35681756Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra("startDestination") ?: "welcome"

        setContent {

            val snackbarHostState = remember { SnackbarHostState() }


            /**
             * ChatGPT was used to generate the following code to get HomeViewModel, InsightsViewModel,
             * SettingsViewModel, GenAiViewModel and NutriCoachViewModel instances to use in the
             * MainActivity. These instances are passed to the various composables.
             */
            val homeViewModel: HomeViewModel = ViewModelProvider(
                this, HomeViewModel.HomeViewModelFactory(this@MainActivity)
            )[HomeViewModel::class.java]

            val insightsViewModel: InsightsViewModel = ViewModelProvider(
                this, InsightsViewModel.InsightsViewModelFactory(this@MainActivity)
            )[InsightsViewModel::class.java]

            val settingsViewModel: SettingsViewModel = ViewModelProvider(
                this, SettingsViewModel.SettingsViewModelFactory(this@MainActivity)
            )[SettingsViewModel::class.java]

            val genAiViewModel: GenAiViewModel = ViewModelProvider(
                this, GenAiViewModel.GenAiViewModelFactory(this@MainActivity)
            )[GenAiViewModel::class.java]

            val nutriCoachViewModel: NutriCoachViewModel = ViewModelProvider(
                this, NutriCoachViewModel.NutriCoachViewModelFactory(this@MainActivity)
            )[NutriCoachViewModel::class.java]

            val userId = AuthManager.getUserId()

            NutriTrack_Pro_Elijah_Chin_35681756Theme {
                val navController: NavHostController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                val showBottomBar = currentRoute != "welcome"
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (showBottomBar) {BottomBar(navController)}},
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

                    Column(modifier = Modifier.padding(innerPadding)){
                        NavHostSection(navController, homeViewModel, innerPadding, this@MainActivity,
                            startDestination, userId, insightsViewModel, settingsViewModel, snackbarHostState,
                            genAiViewModel, nutriCoachViewModel)

                    }
                }
            }
        }
    }
}

@Composable
fun NavHostSection(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    innerPadding: PaddingValues,
    context: Context,
    startDestination: String,
    userId: String?,
    insightsViewModel: InsightsViewModel,
    settingsViewModel: SettingsViewModel,
    snackbarHostState: SnackbarHostState,
    genAiViewModel: GenAiViewModel,
    nutriCoachViewModel: NutriCoachViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("welcome") {
            WelcomeScreen(modifier = Modifier.padding(innerPadding), context, navController)
        }

        composable("home") {
            HomeScreen(context, navController, homeViewModel, userId)
        }

        composable("insights") {
            InsightsScreen(userId, insightsViewModel, navController)
        }

        composable("nutricoach") {
            NutriCoachScreen(genAiViewModel, nutriCoachViewModel, userId, snackbarHostState)
        }

        composable("settings") {
            SettingsScreen(settingsViewModel, userId, navController, snackbarHostState)
        }

        composable("clinicianlogin") {
            ClinicianLogin(innerPadding, snackbarHostState)
        }

    }
}

@Composable
fun HomeScreen(context: Context, navController: NavHostController,
               viewModel: HomeViewModel = viewModel(), userId: String?) {

    if (userId != null) {
        val userName by viewModel.getPatientName(userId).collectAsState(initial = null)

        var foodScore by remember { mutableStateOf(0f) }

        LaunchedEffect(userId) {
            foodScore = viewModel.getPatientTotalFoodScore(userId)
        }

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Text(
                text = "Hello,",
                fontSize = 17.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = if (userName.isNullOrBlank()) "Hello!" else "$userName!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You've already filled in your Food Intake\n " +
                            "Questionnaire, but you can change details here:",
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        context.startActivity(Intent(context, QuestionnaireScreen::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(20.dp),
                        tint = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "Edit",
                        color = Color.DarkGray
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.banana_leaf_rice),
                contentDescription = "Banana leaf rice",
                modifier = Modifier.size(300.dp).padding(15.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "My Score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(150.dp))

                Row(
                    modifier = Modifier
                        .clickable { navController.navigate("insights") },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "See all scores",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_up),
                    contentDescription = "Upward Arrow",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Your Food Quality score",
                    fontSize = 18.sp,
                )

                Spacer(modifier = Modifier.width(20.dp))


                Text(
                    text = "$foodScore/100",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )

            }

            Spacer(modifier = Modifier.height(13.dp))

            Divider(
                color = Color.Gray.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.width(400.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "What is the Food Quality Score?",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Text(
                text = "Your Food Quality Score provides a snapshot of how well your" +
                        " eating patterns align with established food guidelines, helping" +
                        "you identify both strengths and opportunities for improvement in" +
                        "your diet.\n" +
                        "\n" +
                        "This personalized measurement considers various food groups " +
                        "including egetables, fruits, whole grains, and proteins to give" +
                        "you practical insights for making healthier food choices.",
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.Start)
            )

        }
    }
}

@Composable
fun InsightsScreen(userId: String?, viewModel: InsightsViewModel = viewModel(),
                   navController: NavHostController) {
    if (userId != null) {
        val context = LocalContext.current

        val scores by viewModel.getPatientScores(userId).collectAsState(initial = emptyList())

        val listOfSliders = listOf(
            Triple("Vegetables", scores.getOrNull(0) ?: 0f, 10),
            Triple("Fruits", scores.getOrNull(1) ?: 0f, 10),
            Triple("Grains & Cereals", scores.getOrNull(2) ?: 0f, 5),
            Triple("Whole Grains", scores.getOrNull(3) ?: 0f, 5),
            Triple("Meat & Alternatives", scores.getOrNull(4) ?: 0f, 10),
            Triple("Dairy", scores.getOrNull(5) ?: 0f, 10),
            Triple("Water", scores.getOrNull(6) ?: 0f, 5),
            Triple("Saturated Fats", scores.getOrNull(7) ?: 0f, 5),
            Triple("Unsaturated Fats", scores.getOrNull(8) ?: 0f, 5),
            Triple("Sodium", scores.getOrNull(9) ?: 0f, 10),
            Triple("Sugar", scores.getOrNull(10) ?: 0f, 10),
            Triple("Alcohol", scores.getOrNull(11) ?: 0f, 5),
            Triple("Discretionary Foods", scores.getOrNull(12) ?: 0f, 10),
        )

        val foodScore = scores.lastOrNull() ?: 0f

        Scaffold(topBar = { TopInsightsBar() }) { innerPadding ->
            Column(
                modifier = Modifier.padding(10.dp).padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                listOfSliders.forEach { entry ->
                    Sliders(name = entry.first, score = entry.second, scoreCap = entry.third)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Total Good Quality Score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = foodScore,
                        onValueChange = {},
                        valueRange = 0f..100f,
                        modifier = Modifier.weight(3f).size(30.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.DarkGray,
                            activeTrackColor = Color.Green,
                            inactiveTrackColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$foodScore/100",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                var shareText by remember { mutableStateOf("") }

                LaunchedEffect(foodScore) {
                    shareText = "My score is ${foodScore}! What's yours? :D"
                }

                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)

                        context.startActivity(Intent.createChooser(shareIntent, "Share text via"))
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),

                    ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send button",
                        modifier = Modifier.size(18.dp),
                        tint = Color.DarkGray

                    )

                    Spacer(modifier = Modifier.width(7.dp))

                    Text(
                        text = "Share with someone",
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navController.navigate("nutriCoach") },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.width(180.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),


                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.new_rocket_ship),
                        contentDescription = "Rocket",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Improve my diet!",
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun Sliders(name: String, score: Float, scoreCap: Int)
{
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Text( text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start)

        Slider(
            value = score,
            onValueChange = {},
            valueRange = 0f..scoreCap.toFloat(),
            modifier = Modifier.weight(2f).size(20.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.DarkGray,
                activeTrackColor = Color.Green,
                inactiveTrackColor = Color.LightGray
            )
        )

        Text( text = "$score/$scoreCap",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.End)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopInsightsBar()
{
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Insights: Food Score"
            )
        }
    )
}

@Composable
fun NutriCoachScreen(genAiViewModel: GenAiViewModel = viewModel(),
                     nutriCoachViewModel: NutriCoachViewModel = viewModel(), userId: String?,
                     snackbarHostState: SnackbarHostState) {

    var fruitVariationScore by remember { mutableStateOf(0f) }
    var fruitServeSize by remember { mutableStateOf(0f) }

    LaunchedEffect(userId) {
        userId?.let {
            fruitVariationScore = nutriCoachViewModel.getFruitVariationScore(it)
            fruitServeSize = nutriCoachViewModel.getFruitServeSize(it)
        }
    }

    if (userId != null) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "NutriCoach",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (fruitVariationScore >= 2 && fruitServeSize >= 2) {
                AsyncImage(
                    model = "https://picsum.photos/300/300",
                    contentDescription = "Random Image",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
            } else {
                NutriCoachFruityApiComposable(nutriCoachViewModel, snackbarHostState)
            }

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

            NutriCoachGenAiComposable(genAiViewModel, nutriCoachViewModel, userId)

        }
    }
}


@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel(), userId: String?,
                   navController: NavHostController, snackbarHostState: SnackbarHostState) {
    if (userId != null) {
        val userName by viewModel.getPatientName(userId).collectAsState(initial = null)

        var phoneNumber by remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        viewModel.getSpecificPhoneNumber(userId) { returnedPhoneNumber ->
            if (returnedPhoneNumber != null) {
                phoneNumber = returnedPhoneNumber
            }
        }

        Scaffold(topBar = { TopSettingsBar() }) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "ACCOUNT",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = "User name")

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = if (userName.isNullOrBlank()) "" else "$userName",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(Icons.Outlined.Phone, contentDescription = "Phone number")

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = phoneNumber,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Icon(Icons.Outlined.AccountBox, contentDescription = "User Id")

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = userId,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
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

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "OTHER SETTINGS",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.clickable {
                    AuthManager.logout()

                    scope.launch {
                        snackbarHostState.showSnackbar("Logged out. Byebye!")
                    }

                    context.startActivity((Intent(context, LogInScreen::class.java)))

                    },
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Outlined.ExitToApp, "Log Out")

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "Logout",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.clickable { navController.navigate("clinicianlogin") },
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(Icons.Outlined.Person, "Clinician Login")

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "Clinician Login",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ClinicianLogin(innerPadding: PaddingValues, snackbarHostState: SnackbarHostState) {

    var keyInput by remember { mutableStateOf("") }
    val requiredKeyInput = "dollar-entry-apples"
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Clinician Login",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(

            value = keyInput,

            onValueChange = { keyInput = it },
            label = { Text("Clinician Key") },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text("Enter your clinician key",
                color = Color.Gray)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.width(300.dp)

        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (keyInput == requiredKeyInput) {
                    context.startActivity((Intent(context, ClinicianDashboardScreen::class.java)))
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Incorrect key! :(")
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {

            Icon(
                imageVector = Icons.Outlined.ExitToApp,
                "Clinician Login",
                tint = Color.DarkGray)

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "Clinician Login",
                fontSize = 20.sp,
                color = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSettingsBar()
{
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Settings"
            )
        }
    )
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier,
                  context: Context, navController: NavHostController){
    var userId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        AuthManager.checkId(context.applicationContext)
        userId = AuthManager.getUserId()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            "NutriTrack",
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.sprout_transparent_image),
            contentDescription = "Quiz Image",
            modifier = Modifier
                .size(200.dp)
                .padding(15.dp)
        )

        Text("This app provides general health and nutrition information for " +
                "educational purposes only. It is not intended as medical advice, " +
                "diagnosis, or treatment. Always consult a qualified healthcare " +
                "professional before making any changes to your diet, exercise, or " +
                "health regimen.\n" + "Use this app at your own risk.\n" +
                "If you’d like to an Accredited Practicing Dietitian (APD), please" +
                "visit the Monash Nutrition/Dietetics Clinic (discounted rates for" +
                "students):\n",
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.width(300.dp)
        )

        monashNutritionClinic()

        Spacer( modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (userId != null) {
                    navController.navigate("home")
                } else {
                    context.startActivity((Intent(context, LogInScreen::class.java)))
                }
            },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text("Login",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.width(300.dp),
                color = Color.DarkGray
            )

        }

        Spacer( modifier = Modifier.height(50.dp))

        Text(
            text = "Created by Chin Elijah Wei Xin (35681756) 🐳",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {

    val items = listOf(
        "home",
        "insights",
        "nutricoach",
        "settings"
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentRoute = currentDestination?.route

    NavigationBar {
        items.forEach { screen ->

            NavigationBarItem(
                icon = {
                    when (screen) {
                        "home" -> Icon(Icons.Filled.Home, contentDescription = "Home")

                        "insights" -> Icon(Icons.Filled.Star, contentDescription = "Insights")

                        "nutricoach" -> Icon(Icons.Filled.Face, contentDescription = "Insights")

                        "settings" -> Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },

                label = { Text(screen.capitalize()) },

                selected = currentRoute == screen,


                /**
                 * ChatGPT was used to generate this onClick code.
                 * It navigates to a new screen if it is not already there, clears the back stack up
                 * to "welcome," and avoids duplicate screens.
                 */
                onClick = {
                    if (currentRoute != screen) {
                        navController.navigate(screen) {
                            popUpTo("welcome") {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    }
                }
            )
        }
    }
}

@Composable
fun monashNutritionClinic() {
    val uriHandler = LocalUriHandler.current

    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition")
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append("https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
        style = TextStyle(fontSize = 13.sp, textAlign = TextAlign.Center)
    )
}