package com.padc.grocery.networks

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padc.grocery.data.vos.GroceryVO
import java.io.ByteArrayOutputStream
import java.util.*

object RealTimeCloudFireStoreApiImpl : FirebaseApi{

    val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    override fun getGroceries(
        onSuccess: (groceries: List<GroceryVO>) -> Unit,
        onFialure: (String) -> Unit
    ) {
//        db.collection("groceries")
//            .get()
//            .addOnSuccessListener {result ->
//                val grodericeList : MutableList<GroceryVO> = arrayListOf()
//                for(document in result){
//                    val data = document.data
//                    var grocery = GroceryVO()
//                    grocery.name = data["name"] as String
//                    grocery.description = data["description"] as String
//                    grocery.amount = (data["amount"] as Long).toInt()
//                    grodericeList.add(grocery)
//                }
//                onSuccess(grodericeList)
//            }
//            .addOnFailureListener{
//                onFialure(it.message ?: "please check connection")
//            }
        db.collection("groceries")
            .addSnapshotListener{value,error ->
                error?.let {
                    onFialure(it.message ?: "Please check internet connection")
                } ?: run {
                    val grodericeList : MutableList<GroceryVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                for(document in result){
                    val data = document.data
                    data?.let {

                    }
                    val grocery = GroceryVO()
                    grocery.name = data?.get("name") as String
                    grocery.description = data["description"] as String
                    grocery.amount = (data["amount"] as Long).toInt()
                    grocery.image = data["image"] as String?
                    grodericeList.add(grocery)
                }
                onSuccess(grodericeList)
                }
            }
    }

    override fun addGrocery(name: String, description: String, amount: Int,image : String) {
        val groceryMap = hashMapOf(
            "name" to name,
            "description" to description,
            "amount" to amount.toLong(),
            "image" to image
        )
        db.collection("groceries")
            .document(name)
            .set(groceryMap)
            .addOnSuccessListener {
                Log.d("success","Successfully add grocery")
            }
            .addOnFailureListener{Log.d("onFailure","Failed to add Grocery")}
    }

    override fun deleteGrocery(name: String) {
      db.collection("groceries")
          .document(name)
          .delete()
          .addOnSuccessListener {
              Log.d("success","Successfully delete grocery")
          }
          .addOnFailureListener{Log.d("onFailure","Failed to delete Grocery")}
    }

    override fun uploadImageAndEditGrocery(image: Bitmap, grocery: GroceryVO) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            //
        }.addOnSuccessListener { taskSnapshot ->
            //
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            addGrocery(
                grocery.name ?: "",
                grocery.description ?: "",
                grocery.amount ?: 0,
                imageUrl ?: ""
            )
        }
    }
}