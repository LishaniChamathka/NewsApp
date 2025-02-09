package com.example.newsapploginpage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class CreateActivity : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    private lateinit var contentEditText: EditText
    private val undoManager = UndoManager()

    private var selectionStart = 0
    private var selectionEnd = 0

    // Declare UI elements
    private lateinit var imageUploadFrame: FrameLayout
    private lateinit var uploadedImage: ImageView
    private lateinit var addIcon: ImageView
    private lateinit var backIcon: ImageView
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        contentEditText = findViewById(R.id.contentInput)

        contentEditText.addTextChangedListener(object : TextWatcher {
            private var beforeText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeText = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (beforeText != s.toString()) {
                    undoManager.addState(beforeText)
                }
            }
        })

        contentEditText.setOnClickListener {
            updateSelectionPositions()
        }

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
        undoBtn.setOnClickListener { val previousState = undoManager.undo()
            if (previousState != null) {
                contentEditText.setText(previousState)
                contentEditText.setSelection(contentEditText.length())
            } }

        redoBtn.setOnClickListener { val nextState = undoManager.redo()
            if (nextState != null) {
                contentEditText.setText(nextState)
                contentEditText.setSelection(contentEditText.length())
            } }

        boldBtn.setOnClickListener {  updateSelectionPositions()
            applyStyle(Typeface.BOLD) }

        italicBtn.setOnClickListener { updateSelectionPositions()
            applyStyle(Typeface.ITALIC)}

        linkBtn.setOnClickListener { showLinkDialog() }
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


    private fun updateSelectionPositions() {
        selectionStart = contentEditText.selectionStart
        selectionEnd = contentEditText.selectionEnd
    }

    private fun applyStyle(style: Int) {
        if (selectionStart == selectionEnd) return // No text selected

        val spannableString = SpannableStringBuilder(contentEditText.text)

        when (style) {
            Typeface.BOLD -> {
                // Toggle bold
                val existingSpans =
                    spannableString.getSpans(selectionStart, selectionEnd, StyleSpan::class.java)
                        .filter { it.style == Typeface.BOLD }

                if (existingSpans.isEmpty()) {
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        selectionStart,
                        selectionEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    existingSpans.forEach { spannableString.removeSpan(it) }
                }
            }

            Typeface.ITALIC -> {
                // Toggle italic
                val existingSpans =
                    spannableString.getSpans(selectionStart, selectionEnd, StyleSpan::class.java)
                        .filter { it.style == Typeface.ITALIC }

                if (existingSpans.isEmpty()) {
                    spannableString.setSpan(
                        StyleSpan(Typeface.ITALIC),
                        selectionStart,
                        selectionEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    existingSpans.forEach { spannableString.removeSpan(it) }
                }
            }
        }

        contentEditText.text = spannableString
        contentEditText.setSelection(selectionStart, selectionEnd)
    }

    private fun showLinkDialog() {
        updateSelectionPositions()
        if (selectionStart == selectionEnd) {
            Toast.makeText(this, "Please select text to add link", Toast.LENGTH_SHORT).show()
            return
        }

        // Create custom dialog
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_link)

        // Make dialog background transparent
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Initialize views
        val urlInput = dialog.findViewById<EditText>(R.id.urlInput)
        val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)
        val addButton = dialog.findViewById<Button>(R.id.addButton)

        // Set button click listeners
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        addButton.setOnClickListener {
            val url = urlInput.text.toString()
            if (url.isNotEmpty()) {
                val spannableString = SpannableStringBuilder(contentEditText.text)
                spannableString.setSpan(
                    URLSpan(url),
                    selectionStart,
                    selectionEnd,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                contentEditText.text = spannableString
                contentEditText.setSelection(selectionStart, selectionEnd)
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}

