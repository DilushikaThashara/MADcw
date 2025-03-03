package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class HomeDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dashboard)

        // Get the user role from the intent
        val userRole = intent.getStringExtra("USER_ROLE")

        // Initialize views
        val cardReporter = findViewById<CardView>(R.id.cardReporter)
        val cardEditor = findViewById<CardView>(R.id.cardEditor)

        // Show/hide cards based on the user role
        if (userRole == "Reporter") {
            cardEditor.visibility = View.GONE // Hide Editor card for Reporters
        } else if (userRole == "Editor") {
            cardReporter.visibility = View.GONE // Hide Reporter card for Editors
        }

        // Reporter Card Click Listener
        cardReporter.setOnClickListener {
            startActivity(Intent(this, ReporterHomeActivity::class.java))
        }

        // Editor Card Click Listener
        cardEditor.setOnClickListener {
            startActivity(Intent(this, EditorHomeActivity::class.java))
        }
    }
}