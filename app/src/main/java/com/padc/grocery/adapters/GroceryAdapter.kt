package com.padc.grocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.grocery.R
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.delegates.GroceryViewItemActionDelegate
import com.padc.grocery.viewholders.BaseViewHolder
import com.padc.grocery.viewholders.GroceryGridViewHolder
import com.padc.grocery.viewholders.GroceryViewHolder

class GroceryAdapter(private val mDelegate: GroceryViewItemActionDelegate) :
    BaseRecyclerAdapter<BaseViewHolder<GroceryVO>, GroceryVO>() {
    private var mViewType: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GroceryVO> {
        return when (mViewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_grocery_item, parent, false)
                GroceryViewHolder(view, mDelegate)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_gird_grocery_item, parent, false)
                GroceryGridViewHolder(view, mDelegate)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_holder_grocery_item, parent, false)
                GroceryViewHolder(view, mDelegate)
            }
        }

    }

    fun setViewType(viewType: Int) {
        mViewType = viewType
    }
}