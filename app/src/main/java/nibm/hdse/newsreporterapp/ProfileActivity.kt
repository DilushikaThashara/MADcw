package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class ProfileActivity : AppCompatActivity() {

    private lateinit var btnEditProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        btnEditProfile = findViewById(R.id.btnEditProfile)

        // Edit Profile Button Click Listener
        btnEditProfile.setOnClickListener {
            // Navigate to Edit Profile Screen
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

}