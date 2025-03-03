/*package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val tvTitle: TextView = findViewById(R.id.tvTitle)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val tvDate: TextView = findViewById(R.id.tvDate)
        val tvCategory: TextView = findViewById(R.id.tvCategory)
        val tvContent: TextView = findViewById(R.id.tvContent)
        val ivBack: ImageView = findViewById(R.id.ivBack)

        // 🔹 Buttons Layout & Buttons
        val llButtons: LinearLayout = findViewById(R.id.llButtons) // Make sure this is included!
        val btnApprove: Button = findViewById(R.id.btnApprove)
        val btnReject: Button = findViewById(R.id.btnReject)


        val title = intent.getStringExtra("title")
        val status = intent.getStringExtra("status")
        val date = intent.getStringExtra("date")
        val category = intent.getStringExtra("category")
        val content = "This is a sample article content for: $title. You can fetch actual content from the database."

        tvTitle.text = title
        tvStatus.text = "Status: $status"
        tvDate.text = "Date: $date"
        tvCategory.text = "Category: $category"
        tvContent.text = content

        // Handle Back Button Click
        ivBack.setOnClickListener {
            onBackPressed()
        }

        // Show buttons only if the user is an Editor
        if (getUserRole() == "Editor") {
            llButtons.visibility = View.VISIBLE  //Make sure the entire layout is visible
            btnApprove.visibility = View.VISIBLE
            btnReject.visibility = View.VISIBLE
        } else {
            llButtons.visibility = View.GONE
        }

        // Handle Approve Button Click
        btnApprove.setOnClickListener {
            updateArticleStatus(title!!, "Approved")
        }

        // Handle Reject Button Click
        btnReject.setOnClickListener {
            updateArticleStatus(title!!, "Rejected")
        }
    }

    // Keep only ONE getUserRole() function
    private fun getUserRole(): String {
        return intent.getStringExtra("userRole") ?: "Reporter" // Default to "Reporter" if no role is passed
    }


    // 🔹 Function to update article status and send notification
    /*private fun updateArticleStatus(title: String, newStatus: String) {
        Toast.makeText(this, "Article '$title' marked as $newStatus", Toast.LENGTH_SHORT).show()
        sendNotification(title, newStatus)
        finish()
    }*/

    // 🔹 Function to send notification
    private fun sendNotification(title: String, newStatus: String) {
        val notificationMessage = "Your article '$title' has been $newStatus."
        NotificationHelper.sendNotification(this, notificationMessage)
    }

    /*private fun updateArticleStatus(title: String, newStatus: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("articles")
            .whereEqualTo("title", title)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("articles").document(document.id)
                        .update("status", newStatus)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Article '$title' marked as $newStatus", Toast.LENGTH_SHORT).show()
                            sendNotification(title, newStatus)
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error updating status", Toast.LENGTH_SHORT).show()
                        }
                }
            }
    }*/
    private fun updateArticleStatus(articleId: String, newStatus: String) {
        val database = FirebaseDatabase.getInstance().reference.child("articles").child(articleId)

        database.child("status").setValue(newStatus)
            .addOnSuccessListener {
                Toast.makeText(this, "Article marked as $newStatus", Toast.LENGTH_SHORT).show()
                finish() // Close activity after update
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error updating status", Toast.LENGTH_SHORT).show()
            }
    }
}*/

package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArticleDetailActivity : AppCompatActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val tvTitle: TextView = findViewById(R.id.tvTitle)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val tvDate: TextView = findViewById(R.id.tvDate)
        val tvCategory: TextView = findViewById(R.id.tvCategory)
        val tvContent: TextView = findViewById(R.id.tvContent)
        val ivBack: ImageView = findViewById(R.id.ivBack)
        val llButtons: LinearLayout = findViewById(R.id.llButtons)
        val btnApprove: Button = findViewById(R.id.btnApprove)
        val btnReject: Button = findViewById(R.id.btnReject)

        val title = intent.getStringExtra("title")
        val status = intent.getStringExtra("status")
        val date = intent.getStringExtra("date")
        val category = intent.getStringExtra("category")
        val content = "This is a sample article content for: $title. You can fetch actual content from the database."

        tvTitle.text = title
        tvStatus.text = "Status: $status"
        tvDate.text = "Date: $date"
        tvCategory.text = "Category: $category"
        tvContent.text = content

        ivBack.setOnClickListener {
            onBackPressed()
        }

        if (getUserRole() == "Editor") {
            llButtons.visibility = View.VISIBLE
            btnApprove.visibility = View.VISIBLE
            btnReject.visibility = View.VISIBLE
        } else {
            llButtons.visibility = View.GONE
        }

        btnApprove.setOnClickListener {
            updateArticleStatus(title!!, "Approved")
        }

        btnReject.setOnClickListener {
            updateArticleStatus(title!!, "Rejected")
        }
    }

    private fun getUserRole(): String {
        return intent.getStringExtra("userRole") ?: "Reporter"
    }

    private fun updateArticleStatus(title: String, newStatus: String) {
        val articleId = intent.getStringExtra("id") // Get article ID from intent

        if (articleId != null) {
            FirebaseHelper.updateArticleStatus(
                articleId,
                newStatus,
                onSuccess = {
                    Toast.makeText(this, "Article '$title' marked as $newStatus", Toast.LENGTH_SHORT).show()
                    sendNotification(title, newStatus)
                    finish()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Failed to update status: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            Toast.makeText(this, "Article ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendNotification(title: String, newStatus: String) {
        val notificationMessage = "Your article '$title' has been $newStatus."
        NotificationHelper.sendNotification(this, notificationMessage)
    }*/



        /*override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_article_detail)



            val btnApprove: Button = findViewById(R.id.btnApprove)
            val btnReject: Button = findViewById(R.id.btnReject)

            // Check user role
            if (getUserRole() == "Editor") {
                btnApprove.visibility = View.VISIBLE
                btnReject.visibility = View.VISIBLE
            } else{
                btnApprove.visibility = View.GONE
                btnReject.visibility = View.GONE
            }

            // Handle Approve Button Click
            btnApprove.setOnClickListener {
                showRatingDialog()
            }

            // Handle Reject Button Click
            btnReject.setOnClickListener {
                showRejectReasonDialog()
            }
        }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val ivBack: ImageView = findViewById(R.id.ivBack)
        val btnApprove: Button = findViewById(R.id.btnApprove)
        val btnReject: Button = findViewById(R.id.btnReject)
        val llButtons: LinearLayout = findViewById(R.id.llButtons)

        val tvTitle: TextView = findViewById(R.id.tvTitle)
        val tvStatus: TextView = findViewById(R.id.tvStatus)
        val tvDate: TextView = findViewById(R.id.tvDate)
        val tvCategory: TextView = findViewById(R.id.tvCategory)
        val tvContent: TextView = findViewById(R.id.tvContent)

        val userRole = intent.getStringExtra("userRole") ?: "reporter"
        val articleId = intent.getStringExtra("articleId")

        if (articleId != null) {
            fetchArticleDetails(articleId, tvTitle, tvStatus, tvDate, tvCategory, tvContent)
        } else {
            Log.e("ArticleDetailActivity", "Article ID is null!")
        }

        // Check user role and control visibility of buttons
        if (userRole == "Editor") {
            llButtons.visibility = View.VISIBLE
            btnApprove.visibility = View.VISIBLE
            btnReject.visibility = View.VISIBLE
        } else {
            llButtons.visibility = View.GONE // Hide the entire button layout for reporters
        }
        // Handle Approve Button Click
            btnApprove.setOnClickListener {
                showRatingDialog()
            }

            // Handle Reject Button Click
            btnReject.setOnClickListener {
                showRejectReasonDialog()
            }
            // Handle Back Button Click
            ivBack.setOnClickListener {
                onBackPressed() // Close the activity
            }
        }

        private fun showRatingDialog() {
            // Show a dialog to input rating
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Approve Article")
            dialog.setMessage("Enter a rating for the article (1-5):")

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            dialog.setView(input)

            dialog.setPositiveButton("Submit") { _, _ ->
                val rating = input.text.toString().toIntOrNull() ?: 0
                if (rating in 1..5) {
                    updateArticleStatus("Approved", rating)
                } else {
                    Toast.makeText(this, "Invalid rating. Please enter a number between 1 and 5.", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.setNegativeButton("Cancel", null)
            dialog.show()
        }

        private fun showRejectReasonDialog() {
            // Show a dialog to input reject reason
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Reject Article")
            dialog.setMessage("Enter the reason for rejection:")

            val input = EditText(this)
            dialog.setView(input)

            dialog.setPositiveButton("Submit") { _, _ ->
                val reason = input.text.toString()
                if (reason.isNotEmpty()) {
                    updateArticleStatus("Rejected", reason)
                } else {
                    Toast.makeText(this, "Please enter a reason for rejection.", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.setNegativeButton("Cancel", null)
            dialog.show()
        }

        private fun updateArticleStatus(newStatus: String, data: Any) {
            val articleId = intent.getStringExtra("id")
            if (articleId != null) {
                FirebaseHelper.updateArticleStatus(
                    articleId,
                    newStatus,
                    data,
                    onSuccess = {
                        Toast.makeText(this, "Article status updated to $newStatus", Toast.LENGTH_SHORT).show()
                        sendNotification(newStatus, data)
                        finish()
                    },
                    onFailure = { e ->
                        Toast.makeText(this, "Failed to update status: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        private fun sendNotification(status: String, data: Any) {
            val title = intent.getStringExtra("title")
            val reporterId = intent.getStringExtra("reporterId") // Pass reporterId from the intent
            val notificationMessage = when (status) {
                "Approved" -> "Your article '$title' has been approved with a rating of $data."
                "Rejected" -> "Your article '$title' has been rejected. Reason: $data"
                else -> "Your article '$title' has been updated to $status."
            }

            // Save notification to Firebase
            FirebaseHelper.sendNotification(reporterId, notificationMessage)
        }

        private fun getUserRole(): String {
            return intent.getStringExtra("userRole") ?: "Reporter"
        }

    private fun formatTimestamp(timestamp: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = Date(timestamp.toLong()) // Convert string to Date
            sdf.format(date) // Format the date
        } catch (e: Exception) {
            "Unknown Date" // Fallback if the timestamp is invalid
        }
    }

    private fun fetchArticleDetails(
        articleId: String,
        tvTitle: TextView,
        tvStatus: TextView,
        tvDate: TextView,
        tvCategory: TextView,
        tvContent: TextView
    ) /*{
        val database = FirebaseDatabase.getInstance()
        val articleRef = database.getReference("articles").child(articleId)

        articleRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val title = snapshot.child("title").getValue(String::class.java) ?: "Unknown Title"
                val status = snapshot.child("status").getValue(String::class.java) ?: "Pending"
                val date = snapshot.child("date").getValue(String::class.java) ?: "Unknown Date"
                val category = snapshot.child("category").getValue(String::class.java) ?: "No Category"
                val content = snapshot.child("content").getValue(String::class.java) ?: "No Content"

                tvTitle.text = title
                tvStatus.text = "Status: $status"
                tvDate.text = "Date: $date"
                tvCategory.text = "Category: $category"
                tvContent.text = content
            } else {
                Log.e("Firebase", "Article not found in database")
                Toast.makeText(this, "Article not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Log.e("Firebase", "Failed to load article: ${e.message}")
            Toast.makeText(this, "Failed to load article", Toast.LENGTH_SHORT).show()
        }
    }*/
    {val database = FirebaseDatabase.getInstance().getReference("articles").child(articleId)

        database.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val title = snapshot.child("title").getValue(String::class.java) ?: "Unknown Title"
                val status = snapshot.child("status").getValue(String::class.java) ?: "Pending"
                val timestamp = snapshot.child("date").getValue(String::class.java) ?: "0"
                val formattedDate = formatTimestamp(timestamp) // Format the timestamp
                val category = snapshot.child("category").getValue(String::class.java) ?: "No Category"
                val content = snapshot.child("content").getValue(String::class.java) ?: "No Content"

                tvTitle.text = title
                tvStatus.text = "Status: $status"
                tvDate.text = "Date: $formattedDate" // Display formatted date
                tvCategory.text = "Category: $category"
                tvContent.text = content
            } else {
                Toast.makeText(this, "Article not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to load article", Toast.LENGTH_SHORT).show()
        }}
}