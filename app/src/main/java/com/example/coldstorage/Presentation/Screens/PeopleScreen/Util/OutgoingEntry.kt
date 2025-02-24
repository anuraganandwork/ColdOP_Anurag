package com.example.coldstorage.Presentation.Screens.PeopleScreen.Util

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.coldstorage.DataLayer.Api.OutgoingData.BagUpdate
import com.example.coldstorage.DataLayer.Api.OutgoingData.OutgoingDataClassItem
import com.example.coldstorage.ViewModel.StoreOwnerViewmodel.ReceiptRow

fun outgoingEntry(inputState:MutableState<String>  , reciptRow: ReceiptRow , outgoingResponseBody: MutableList<OutgoingDataClassItem> , index :Int){


    if (inputState.value.toIntOrNull() != null && inputState.value.toIntOrNull()!! <= reciptRow.size.getOrNull(index)?.quantity?.currentQuantity?.toInt()!!) {

        val existingItem = outgoingResponseBody.find { it.orderId == reciptRow.orderId }
        if (existingItem != null) {
            val existingBagSize = existingItem.bagUpdates.find { it?.size == reciptRow.size.getOrNull(index)?.size }
            Log.d("Outgoingsecscrn" ,existingBagSize.toString() )
            if (existingBagSize != null) {
                val updatedBagWithOldBag = existingBagSize.copy(quantityToRemove = inputState.value.toInt())
                val updatedBagUpdates = existingItem.bagUpdates.toMutableList().apply {
                    val indexOfBag = indexOf(existingBagSize)
                    if (indexOfBag != -1) this[indexOfBag] = updatedBagWithOldBag
                }
                val updatedElement = existingItem.copy(bagUpdates = updatedBagUpdates)
                val index = outgoingResponseBody.indexOf(existingItem)
                if (index != -1) outgoingResponseBody[index] = updatedElement

            } else {
                val updatedBagUpdates = existingItem.bagUpdates.toMutableList().apply {
                    reciptRow.size.getOrNull(index)?.size?.let { add(BagUpdate(size = it, quantityToRemove = inputState.value.toInt())) }
                }

                val updatedElement = existingItem.copy(bagUpdates = updatedBagUpdates)
                val index = outgoingResponseBody.indexOf(existingItem)
                if (index != -1) outgoingResponseBody[index] = updatedElement
            }
        } else {
            // Add a new order with the bag size
            outgoingResponseBody.add(

                OutgoingDataClassItem(
                    orderId = reciptRow.orderId,
                    variety = reciptRow.variety,
                    address = reciptRow.address,
                    currQty= reciptRow.size.getOrNull(index)!!.quantity.currentQuantity.toString(),
                    bagUpdates = listOf(
                         BagUpdate(size =  reciptRow.size.getOrNull(index)!!.size, quantityToRemove = inputState.value.toInt())
                    )
                )
            ) }


    } else{
        //Toast.makeText(context, "Please a enter a value less than ${pair.currentQuantity.toInt()}!", Toast.LENGTH_SHORT).show()
        Log.d("message","In the else block of outgoing helper function")
    }




}