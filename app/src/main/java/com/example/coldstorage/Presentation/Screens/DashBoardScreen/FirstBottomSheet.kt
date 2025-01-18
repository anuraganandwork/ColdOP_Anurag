import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coldstorage.DataLayer.Api.FarmerData
import com.example.coldstorage.Presentation.Screens.PeopleScreen.Components.ColdOpTextField
import com.example.coldstorage.R
import com.example.coldstorage.R.*
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.AuthViewmodel
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstBottomSheet(onContinue: () -> Unit, viewmodel: FunctionStoreOwner  , viewModel: AuthViewmodel = hiltViewModel()) {
    var query by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val variety = viewmodel.variety.collectAsState()
    val lotSize = viewmodel.lotsize.collectAsState()
    val Ration = viewmodel.Ration.collectAsState()
    val seedBags = viewmodel.seedBags.collectAsState()
    val twelveNumber = viewmodel.twelveNumber.collectAsState()
    val Goli = viewmodel.goli.collectAsState()
    val cutAndTok = viewmodel.cuttok.collectAsState()
    val currentReceiptNum by viewmodel.currentRecieptNum.collectAsState(0)
    val isNameSelected = remember { mutableStateOf(false) }


    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
   val accountHolderName = remember {mutableStateOf("")}
    val mobileNumber =remember {mutableStateOf("")}

    //states for quickest add farmer route
    var address by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var mobileNumberr by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mobileNumberError by remember { mutableStateOf("") }
    val loading = viewModel.loadingAddFarmer.collectAsState()
    val statusAdding = viewModel.farmerAddStatus.collectAsState()
    var showMobileError by remember { mutableStateOf(false) }


//    LaunchedEffect(key1 = keyboardHeight) {
//        coroutineScope.launch {
//            scrollState.scrollBy(keyboardHeight.toFloat())
//        }
//    }

    LaunchedEffect(Unit) {
        viewmodel.getRecieptNumbers()
    }

    LaunchedEffect(statusAdding.value ){
        if(statusAdding.value){
            address = ""
            name = ""
            mobileNumberr = ""
            imageUrl = ""
            password = ""

        }    }
    val searchResults = viewmodel.searchResults.collectAsState()
    val openAddFarmerDailog = remember{
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
           // .verticalScroll(scrollState)

        ,

                verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header
        item {
            Text(
                text = "Create Order",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Current Receipt Number :")
                if (currentReceiptNum != 0) {
                    Text(text = currentReceiptNum.toString(), color = Color.Blue)
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
            }
        }

        // Account Name Section
        item {
            Text(text = "Enter Account Name (search and select)")
            Row(modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewmodel.onSearchQuery(query)
                    },
                    modifier = Modifier.weight(.9f),
                    label = { Text("Search farmers") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = Color.Black, // Text color inside the TextField
                        disabledTextColor = Color.Gray,
                        cursorColor = Color.Black, // Cursor color
                        containerColor = Color.Transparent ,
                        errorCursorColor = Color.Red, // Cursor color in error state
                        focusedIndicatorColor = primeGreen, // Border color when focused
                        unfocusedIndicatorColor = Color.Gray, // Border color when not focused
                        errorIndicatorColor = Color.Red, // Border color in error state
                        focusedLeadingIconColor = Color.Black,
                        focusedTrailingIconColor = Color.Black,
                        focusedLabelColor = primeGreen, // Label color when focused
                        unfocusedLabelColor = Color.Gray, // Label color when not focused
                        errorLabelColor = Color.Red // Label color in error state
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier
                    .weight(.2f)
                    .fillMaxHeight()
                    .clickable {
                        openAddFarmerDailog.value = true
                    }, verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.plus) ,
                        contentDescription = "New farmer add icon" , modifier = Modifier.size(32.dp),tint= primeGreen )
                   Text(text = "New Farmer" , fontSize = 8.sp  , color = primeGreen , fontWeight = FontWeight.Medium)
                }
                //10:06
               // add dialog here
           if(openAddFarmerDailog.value){
               AlertDialog(onDismissRequest = {
                   openAddFarmerDailog.value = false
                   viewModel.resetAddFarmerStatus()
                                              }, confirmButton = { /*TODO*/ } ,
               title = { Text(text = "Create New Account")} , text = {
                   Column(modifier = Modifier.padding(top = 20.dp)) {

                       Row {
                           Text(
                               text = "Name:",
                               modifier = Modifier
                                   .weight(0.3f)
                                   .padding(end = 8.dp),
                           )

                           // Input TextField
                           ColdOpTextField(
                               value = name,
                               onValueChange = { text ->
                                   name = text
                               },
                               modifier = Modifier.weight(0.7f),
                               placeholder = "Enter name"
                           )
                       }
          Spacer(modifier = Modifier.padding(5.dp))
                       Column {
                           Row(
                               modifier = Modifier.fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically,
                               horizontalArrangement = Arrangement.Start
                           ) {
                               Text(
                                   text = "Contact: ",
                                   modifier = Modifier
                                       .weight(0.3f)
                                       .padding(end = 8.dp),
                               )
                               Column(modifier = Modifier.weight(0.7f)) {
                                   ColdOpTextField(
                                       value = mobileNumberr,
                                       onValueChange = { text ->
                                           mobileNumberr = text
                                           showMobileError = text.length != 10 && text.isNotEmpty() // Show error if not 10 digits
                                       },
                                       placeholder = "Enter mobile no.",
                                       keyboardOptions = KeyboardOptions.Default.copy(
                                           keyboardType = KeyboardType.Number
                                       )
                                   )
                                   if (showMobileError) {
                                       Text(
                                           text = "Mobile no. should be of 10 digits",
                                           color = Color.Red,
                                           fontSize = 10.sp,
                                           modifier = Modifier.padding(top = 2.dp)
                                       )
                                   }
                               }
                           }
                       }

                       Spacer(modifier = Modifier.padding(10.dp))

                       Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.End) {
                           Button(
                               onClick = {
                                   val farmerData = FarmerData(
                                       name = name,
                                       mobileNumber = mobileNumberr,
                                       address = "Added from quickest route",
                                       password = "Added from quickest route",
                                       imageUrl = "Added from quickest route"
                                   )
                                   try {
                                       viewModel.quickRegister(farmerData)
                                       Log.d("SuccessfullLOG", "farmer added quickly")
                                   } catch (e: Exception) {
                                       Log.d("ErrorLog", e.message.toString())
                                   }
                               },
                               enabled = (mobileNumberr.length == 10 && name.isNotBlank()) || statusAdding.value,
                               colors = ButtonDefaults.buttonColors(
                                   contentColor = Color.Black,
                                   containerColor = primeGreen,
                                   disabledContentColor = Color.White,
                                   disabledContainerColor = Color.Gray
                               )
                           ) {
                               AnimatedContent(targetState = Pair(loading.value, statusAdding.value),
                                   label = ""
                               ) { (isLoading, isAdded, ) ->
                                   when {
                                       isLoading -> {
                                           CircularProgressIndicator(
                                               modifier = Modifier.size(20.dp),
                                               color = Color.Black
                                           )
                                       }
                                       isAdded -> {
                                           Text(text = "Saved!", color = Color.Black)
                                       }
                                       else -> {
                                           Text(text = "Save", color = Color.Black)
                                       }
                                   }
                               }
                           }


                       }
                   }
                   } , shape = RoundedCornerShape(10.dp),containerColor = Color.White)
           }
            }

        }

        // Search Results
        if (!isNameSelected.value) {
//            item{
//                Column {
//
//                    Text("New Farmer")
//                }
//            }
//            if(searchResults.value.size < 1) {
//               item{
//
//                   Column {
//
//
//                ColdOpTextField(value = accountHolderName.value, onValueChange = {
//                    accountHolderName.value = it
//                } , placeholder = "Enter name" )
//                       Spacer(modifier = Modifier.padding(10.dp))
//                   Row {
//
//
//                       ColdOpTextField(value = mobileNumber.value, onValueChange = {
//                           mobileNumber.value = it
//                       } , modifier = Modifier.weight(.75f), placeholder = "Enter mobile number")
//                       Button(onClick = { query = accountHolderName.value
//                                        isNameSelected.value = true }, enabled = accountHolderName.value.length > 0 && mobileNumber.value.length == 10
//
//                           , colors = ButtonColors(containerColor = primeGreen , contentColor = Color.White , disabledContainerColor = Color.Gray , disabledContentColor = Color.White)) {
//                           Text(text = "Save")
//                       }
//
//                   }
//
//                   }
//            }
//
//
//
//
//            }
            items(searchResults.value) { result ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            query = result.name
                            isNameSelected.value = true
                            viewmodel.updateFarmerAcc(result._id)
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = result.name)
                    Text(text = result.mobileNumber)
                }
            }
        }

        // Lot Number Section
        item {
            Text(text = "Enter Variety")
            OutlinedTextField(
                value = variety.value,
                onValueChange = {
                    viewmodel.updateVariety(it)
                },
                label = { Text("Enter Variety") },
                modifier = Modifier.fillMaxWidth() ,
                colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black, // Text color inside the TextField
                    disabledTextColor = Color.Gray,
                    cursorColor = Color.Black, // Cursor color
                    containerColor = Color.Transparent ,
                    errorCursorColor = Color.Red, // Cursor color in error state
                    focusedIndicatorColor = primeGreen, // Border color when focused
                    unfocusedIndicatorColor = Color.Gray, // Border color when not focused
                    errorIndicatorColor = Color.Red, // Border color in error state
                    focusedLeadingIconColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    focusedLabelColor = primeGreen, // Label color when focused
                    unfocusedLabelColor = Color.Gray, // Label color when not focused
                    errorLabelColor = Color.Red // Label color in error state
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Quantities Section Header
        item {
            Text(text = "Enter Quantities", fontWeight = FontWeight.Bold)
            Text(text = "Set the quantities for each size")
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Quantity Input Fields
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuantityInputField(
                    "Ration/Table bags",
                    Ration.value,
                    viewmodel::updateRation,
                    keyboardController
                )
                QuantityInputField(
                    "Seed bags",
                    seedBags.value,
                    viewmodel::updateSeedBags,
                    keyboardController
                )
                QuantityInputField(
                    "12 No. seed bags",
                    twelveNumber.value,
                    viewmodel::updateTwelveNumber,
                    keyboardController
                )
                QuantityInputField(
                    "Goli bags",
                    Goli.value,
                    viewmodel::updateGoli,
                    keyboardController
                )
                QuantityInputField(
                    "Cut & Tok bags",
                    cutAndTok.value,
                    viewmodel::updateCutAndTok,
                    keyboardController
                )
            }
        }

        // Continue Button
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { onContinue() },
                shape = RoundedCornerShape(10.dp),
                color = primeGreen
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            // Add some bottom padding to ensure the button is not too close to the bottom
            Spacer(modifier = Modifier.height(216.dp))
        }
    }
}

@Composable
private fun QuantityInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(1.dp, SolidColor(Color.Gray)),
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 5.dp, vertical = 3.dp)

                .width(134.dp)
                .height(40.dp),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center , ),


        )
    }
}

//enter variety textfield in the form
// solve for the ui layer , from chatgpt

//@Composable
//fun currentKeyboardHeight(): Dp {
//    val keyboardVisibilityState: KeyboardVisibilityState = keyboardAsState()
//    return keyboardVisibilityState.keyboardHeight.toDp()
//}

//9:23