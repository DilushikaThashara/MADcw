package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        // Initialize views
        val tvArticlesSubmitted = findViewById<TextView>(R.id.tvArticlesSubmitted)
        val tvAverageRating = findViewById<TextView>(R.id.tvAverageRating)
        val tvApprovalRatio = findViewById<TextView>(R.id.tvApprovalRatio)

        // Fetch and display analytics data
        tvArticlesSubmitted.text = "Articles Submitted: 10"
        tvAverageRating.text = "Average Rating: 4.5"
        tvApprovalRatio.text = "Approval Ratio: 80%"
    }
}