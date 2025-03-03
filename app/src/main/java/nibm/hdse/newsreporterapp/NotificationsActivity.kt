package nibm.hdse.newsreporterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class NotificationsActivity : AppCompatActivity() {

    private lateinit var rvNotifications: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        // Initialize RecyclerView
        rvNotifications = findViewById(R.id.rvNotifications)
        rvNotifications.layoutManager = LinearLayoutManager(this)

        // Fetch notifications for the current user
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            fetchNotifications(userId)
        }
    }

    private fun fetchNotifications(userId: String) {
        FirebaseHelper.fetchNotifications(
            userId,
            onSuccess = { notifications ->
                notificationAdapter = NotificationAdapter(notifications)
                rvNotifications.adapter = notificationAdapter
            },
            onFailure = { e ->
                // Handle failure
            }
        )
    }
}