package com.padc.grocery.dialogs

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padc.grocery.R
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.mvp.presenters.impls.MainPresenterImpl
import com.padc.grocery.mvp.presenters.MainPresenter
import com.padc.grocery.util.load
import kotlinx.android.synthetic.main.dialog_add_grocery.*
import kotlinx.android.synthetic.main.dialog_add_grocery.view.*
import java.io.IOException

class GroceryDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_GROCERY_DIALOG = "TAG_ADD_GROCERY_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_IMAGE = "BUNDLE_IMAGE"
       // const val BUNDLE_BITMAP = "BUNDLE_BITMAP"

        const val PICK_IMAGE_FROM_DIALOG = 2222

        fun newFragment(): GroceryDialogFragment {
            return GroceryDialogFragment()
        }
    }

    private lateinit var mPresenter: MainPresenter
    val groceryVO = GroceryVO()
    private var mbitMap : Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_grocery,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        view.etGroceryName?.setText(arguments?.getString(BUNDLE_NAME))
        view.etDescription?.setText(arguments?.getString(BUNDLE_DESCRIPTION))
        view.etAmount?.setText(arguments?.getString(BUNDLE_AMOUNT))
        arguments?.getString(BUNDLE_IMAGE)?.toUri()?.let { imageView.load(it) }

        view.btnAddGrocery.setOnClickListener {
            groceryVO.name =  etGroceryName?.text.toString()
            groceryVO.amount = etAmount.text.toString().toInt()
            groceryVO.description = etDescription.text.toString()
            mbitMap?.let {
                mPresenter.onTapAddGrocery(groceryVO,it)
            }
            dismiss()
        }

        imageView.setOnClickListener {
        //   mPresenter.onTabImageView(1)
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_FROM_DIALOG)
          }
    }

    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(MainPresenterImpl::class.java)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_DIALOG && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data
            try {

                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source? =
                            activity?.contentResolver?.let { it1 -> ImageDecoder.createSource(it1, it) }
                        val bitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                         mbitMap = bitmap
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, it
                        )
                        mbitMap =  bitmap
                        imageView.load(it)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


}