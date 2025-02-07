package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class CreateActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    // Declare UI elements
    private lateinit var imageUploadFrame: FrameLayout
    private lateinit var uploadedImage: ImageView
    private lateinit var addIcon: ImageView
    private lateinit var backIcon: ImageView
    private lateinit var dotMenu: ImageView
    private lateinit var spinner: Spinner
    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var cancelBtn: Button
    private lateinit var submitBtn: Button
    private lateinit var undoBtn: ImageView
    private lateinit var redoBtn: ImageView
    private lateinit var boldBtn: ImageView
    private lateinit var italicBtn: ImageView
    private lateinit var linkBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // Initialize UI elements
        initializeViews()
        setupSpinner()
        setupClickListeners()
    }

    private fun initializeViews() {
        imageUploadFrame = findViewById(R.id.imageUploadFrame)
        uploadedImage = findViewById(R.id.uploadedImage)
        addIcon = findViewById(R.id.addIcon)
        backIcon = findViewById(R.id.backIcon)
        dotMenu = findViewById(R.id.dotMenu)
        spinner = findViewById(R.id.spinner)
        titleInput = findViewById(R.id.titleInput)
        contentInput = findViewById(R.id.contentInput)
        cancelBtn = findViewById(R.id.cancelBtn)
        submitBtn = findViewById(R.id.submitBtn)
        undoBtn = findViewById(R.id.undoBtn)
        redoBtn = findViewById(R.id.redoBtn)
        boldBtn = findViewById(R.id.boldBtn)
        italicBtn = findViewById(R.id.italicBtn)
        linkBtn = findViewById(R.id.linkBtn)
    }

    private fun setupSpinner() {
        val newsTypes = arrayOf("Breaking News", "General News", "Technology", "Sports", "Entertainment")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupClickListeners() {
        imageUploadFrame.setOnClickListener { openImagePicker() }

        backIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        cancelBtn.setOnClickListener { finish() }

        submitBtn.setOnClickListener {
            showCenteredDialog()
        }

        // Rich text formatting buttons
        undoBtn.setOnClickListener { /* Implement undo functionality */ }
        redoBtn.setOnClickListener { /* Implement redo functionality */ }
        boldBtn.setOnClickListener { /* Implement bold functionality */ }
        italicBtn.setOnClickListener { /* Implement italic functionality */ }
        linkBtn.setOnClickListener { /* Implement link functionality */ }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in favor of Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            uploadedImage.setImageURI(selectedImageUri)
            uploadedImage.visibility = View.VISIBLE
            addIcon.visibility = View.GONE
        }
    }

    private fun showCenteredDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_submit, null)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)

        val okButton = view.findViewById<Button>(R.id.okButton)
        val cancelText = view.findViewById<TextView>(R.id.cancelText)

        okButton.setOnClickListener {
            dialog.dismiss()
            showSuccessDialog()
        }

        cancelText.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }

    private fun showSuccessDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_success, null)

        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val viewArticleButton = view.findViewById<Button>(R.id.viewArticleButton)
        val backToHomeButton = view.findViewById<Button>(R.id.backToHomeButton)

        viewArticleButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, UserArticleActivity::class.java))
        }

        backToHomeButton.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        dialog.show()
    }
}

