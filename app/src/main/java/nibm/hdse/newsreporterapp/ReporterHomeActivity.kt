/*package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReporterHomeActivity : AppCompatActivity() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var fabCreateReport: FloatingActionButton
    private lateinit var ivNotifications: ImageView
    private lateinit var ivProfile: ImageView
    private lateinit var btnAnalytics: Button  // Added Analytics Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activirty_reporter_home)

        // Initialize views
        rvArticles = findViewById(R.id.rvArticles)
        fabCreateReport = findViewById(R.id.fabCreateReport)
        ivNotifications = findViewById(R.id.ivNotifications)
        ivProfile = findViewById(R.id.ivProfile)
        btnAnalytics = findViewById(R.id.btnGoToApproval) // Initialize Analytics Button

        // Set up RecyclerView
        setupRecyclerView()

        // Floating Action Button Click Listener (Create Report)
        fabCreateReport.setOnClickListener {
            startActivity(Intent(this, CreateReportActivity1::class.java))
        }

        // Notifications Click Listener
        ivNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        // Profile Click Listener
        ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Analytics Button Click Listener
        btnAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val articles = listOf(
            Article("Article 1", "Pending", "2023-10-15", "Politics"),
            Article("Article 2", "Approved", "2023-10-14", "Politics"),
            Article("Article 3", "Rejected", "2023-10-13", "Politics")
        )

        val adapter = ArticleAdapter(articles, this) // Pass 'this' as context
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.adapter = adapter
    }
}*/

package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class ReporterHomeActivity : AppCompatActivity() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var fabCreateReport: FloatingActionButton
    private lateinit var ivNotifications: ImageView
    private lateinit var ivProfile: ImageView
    private lateinit var btnAnalytics: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activirty_reporter_home)

        // Initialize views
        rvArticles = findViewById(R.id.rvArticles)
        fabCreateReport = findViewById(R.id.fabCreateReport)
        ivNotifications = findViewById(R.id.ivNotifications)
        ivProfile = findViewById(R.id.ivProfile)
        btnAnalytics = findViewById(R.id.btnGoToApproval)

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch notifications
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            fetchNotifications(userId)
        }

        // Floating Action Button Click Listener (Create Report)
        fabCreateReport.setOnClickListener {
            startActivity(Intent(this, CreateReportActivity1::class.java))
        }

        // ReporterHomeActivity.kt and EditorHomeActivity.kt

        ivNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        // Profile Click Listener
        ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Analytics Button Click Listener
        btnAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        val adapter = ArticleAdapter(emptyList(), this) // Pass 'this' as context
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.adapter = adapter

        // Fetch all articles from Firebase
        FirebaseHelper.fetchArticles(
            onSuccess = { articles ->
                adapter.updateList(articles)
            },
            onFailure = { e ->
                Toast.makeText(this, "Failed to fetch articles: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun fetchNotifications(userId: String) {
        FirebaseHelper.fetchNotifications(
            userId,
            onSuccess = { notifications ->
                if (notifications.isNotEmpty()) {
                    // Show notification badge on the bell icon
                    ivNotifications.setImageResource(R.drawable.notification_bell_active)
                } else {
                    // Show default bell icon
                    ivNotifications.setImageResource(R.drawable.notification_bell)
                }
            },
            onFailure = { e ->
                Toast.makeText(this, "Failed to fetch notifications: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}