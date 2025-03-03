package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchNotifications: Switch
    private lateinit var switchDarkMode: Switch
    private lateinit var btnChangePassword: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnLogout = findViewById(R.id.btnLogout)

        // Toggle Notifications
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // Save notification preference (e.g., SharedPreferences)
            Toast.makeText(this, "Notifications ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        // Toggle Dark Mode
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Apply dark mode theme
            Toast.makeText(this, "Dark Mode ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        // Change Password Button Click Listener
        btnChangePassword.setOnClickListener {
            // Navigate to Change Password Screen
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        // Logout Button Click Listener
        btnLogout.setOnClickListener {
            // Perform logout logic (e.g., clear session)
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close the SettingsActivity
        }
    }
}