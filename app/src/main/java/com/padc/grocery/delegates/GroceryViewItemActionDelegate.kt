package com.padc.grocery.delegates

import com.padc.grocery.data.vos.GroceryVO

interface GroceryViewItemActionDelegate{
    fun onTapDeleteGrocery(name : String)
    fun onTapEditGrocery(name: String, description: String, amount: Int,image: String)
    fun onTapFileUpload(grocery: GroceryVO,tapFrom:Int)


}