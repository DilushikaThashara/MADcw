package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.appName)

        // Load logo animation
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logo.startAnimation(logoAnimation)

        // Load app name animation with a delay
        val appNameAnimation = AnimationUtils.loadAnimation(this, R.anim.app_name_animation)
        appName.postDelayed({
            appName.alpha = 1f // Make app name visible
            appName.startAnimation(appNameAnimation)
        }, 500) // Delay of 500ms

        // Navigate to LoginActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the SplashActivity
        }, 3000) // 3 seconds delay
    }
}