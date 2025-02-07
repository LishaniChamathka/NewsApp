package com.example.newsapploginpage

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapploginpage.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class SubmitBottomSheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show the bottom sheet dialog when this activity starts
        showSubmitBottomSheet()
    }

    private fun showSubmitBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_submit, null)

        val btnOk = view.findViewById<Button>(R.id.okButton)
        val btnCancel = view.findViewById<TextView>(R.id.cancelText)

        btnOk.setOnClickListener {
            // Close the bottom sheet and finish this activity
            bottomSheetDialog.dismiss()
            finish()
        }

        btnCancel.setOnClickListener {
            // Just dismiss the bottom sheet
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }
}
