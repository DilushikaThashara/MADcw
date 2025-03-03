/*package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateReportActivity1 : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etContent: EditText
    private lateinit var btnUploadMedia: Button
    private lateinit var btnSaveDraft: Button
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report1)

        // Initialize views
        etTitle = findViewById(R.id.etTitle)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etContent = findViewById(R.id.etContent)
        btnUploadMedia = findViewById(R.id.btnUploadMedia)
        btnSaveDraft = findViewById(R.id.btnSaveDraft)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Set up category spinner
        val categories = arrayOf("Politics", "Sports", "Technology", "Entertainment")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerCategory.adapter = adapter

        // Upload Media Button Click Listener
        btnUploadMedia.setOnClickListener {
            // Open file picker or camera for media upload
            openMediaPicker()
        }

        // Save Draft Button Click Listener
        btnSaveDraft.setOnClickListener {
            saveDraft()
        }

        // Submit Button Click Listener
        btnSubmit.setOnClickListener {
            submitReport()
        }
    }

    private fun openMediaPicker() {
        // Implement media picker logic
        Toast.makeText(this, "Media Picker", Toast.LENGTH_SHORT).show()
    }

    private fun saveDraft() {
        val title = etTitle.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val content = etContent.text.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            Toast.makeText(this, "Draft Saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitReport() {
        val title = etTitle.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val content = etContent.text.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            Toast.makeText(this, "Report Submitted", Toast.LENGTH_SHORT).show()
            finish() // Close the activity
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}*/

package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateReportActivity1 : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etContent: EditText
    private lateinit var btnUploadMedia: Button
    private lateinit var btnSaveDraft: Button
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report1)

        // Initialize views
        etTitle = findViewById(R.id.etTitle)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etContent = findViewById(R.id.etContent)
        btnUploadMedia = findViewById(R.id.btnUploadMedia)
        btnSaveDraft = findViewById(R.id.btnSaveDraft)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Set up category spinner
        val categories = arrayOf("Politics", "Sports", "Technology", "Entertainment")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerCategory.adapter = adapter

        // Upload Media Button Click Listener
        btnUploadMedia.setOnClickListener {
            openMediaPicker()
        }

        // Save Draft Button Click Listener
        btnSaveDraft.setOnClickListener {
            saveDraft()
        }

        // Submit Button Click Listener
        btnSubmit.setOnClickListener {
            submitReport()
        }
    }

    private fun openMediaPicker() {
        Toast.makeText(this, "Media Picker", Toast.LENGTH_SHORT).show()
    }

    private fun saveDraft() {
        val title = etTitle.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val content = etContent.text.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            Toast.makeText(this, "Draft Saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun submitReport() {
        val title = etTitle.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val content = etContent.text.toString()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            val article = Article(
                title = title,
                status = "Pending", // Default status for new articles
                date = "2023-10-15", // Replace with actual date logic
                category = category
            )

            // Save article to Realtime Database
            FirebaseHelper.addArticle(
                article,
                onSuccess = {
                    Toast.makeText(this, "Report Submitted", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                },
                onFailure = { e ->
                    Toast.makeText(this, "Failed to submit report: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}