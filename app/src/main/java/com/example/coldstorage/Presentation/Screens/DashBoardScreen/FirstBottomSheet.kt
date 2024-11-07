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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.FunctionStoreOwner
import com.example.coldstorage.ui.theme.primeGreen
import kotlinx.coroutines.launch

@Composable
fun FirstBottomSheet(onContinue: () -> Unit, viewmodel: FunctionStoreOwner) {
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

//    LaunchedEffect(key1 = keyboardHeight) {
//        coroutineScope.launch {
//            scrollState.scrollBy(keyboardHeight.toFloat())
//        }
//    }

    LaunchedEffect(Unit) {
        viewmodel.getRecieptNumbers()
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
                Text(text = "Current Reciept Number :")
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
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewmodel.onSearchQuery(query)
                },
                label = { Text("Search farmers") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Search Results
        if (!isNameSelected.value) {
            items(viewmodel.searchResults) { result ->
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
                modifier = Modifier.fillMaxWidth()
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
                .padding(horizontal = 5.dp , vertical = 3.dp)

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

//1214