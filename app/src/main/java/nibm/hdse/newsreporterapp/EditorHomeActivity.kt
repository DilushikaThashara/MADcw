package nibm.hdse.newsreporterapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class EditorHomeActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var etSearch: EditText
    private lateinit var ivNotifications: ImageView
    private lateinit var ivProfile: ImageView
    private lateinit var rvCategories: RecyclerView // RecyclerView for categories
    private lateinit var btnGoToApproval: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_home)

        // Initialize views
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        etSearch = findViewById(R.id.etSearch)
        ivNotifications = findViewById(R.id.ivNotifications)
        ivProfile = findViewById(R.id.ivProfile)
        rvCategories = findViewById(R.id.rvCategories) // Initialize RecyclerView for categories
        btnGoToApproval = findViewById(R.id.btnGoToApproval)

        // Set up ViewPager and Tabs
        setupViewPager()

        // Set up Categories RecyclerView
        setupCategories()

        // Set up Search Bar Listener
        setupSearchBar()

        // Set up Notifications
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            fetchNotifications(userId)
        }

        // Set up Button Click Listeners
        btnGoToApproval.setOnClickListener {
            startActivity(Intent(this, ApprovalDashboardActivity::class.java))
        }

        ivNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        // Attach TabLayout to ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Pending"
                1 -> tab.text = "Approved"
                2 -> tab.text = "Rejected"
            }
        }.attach()
    }

    private fun setupCategories() {
        val categories = listOf(
            Category("Sports", R.drawable.ic_sports),
            Category("Education", R.drawable.ic_education),
            Category("Weather", R.drawable.ic_weather),
            Category("Politics", R.drawable.ic_politics),
            Category("Technology", R.drawable.ic_technology)
        )

        val categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            filterArticlesByCategory(selectedCategory.name)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.layoutManager = layoutManager
        rvCategories.adapter = categoryAdapter
    }

    private fun setupSearchBar() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterArticles(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterArticles(query: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f${viewPager.currentItem}")
        when (currentFragment) {
            is PendingArticlesFragment -> currentFragment.filterArticles(query)
            is ApprovedArticlesFragment -> currentFragment.filterArticles(query)
            is RejectedArticlesFragment -> currentFragment.filterArticles(query)
        }
    }

    private fun filterArticlesByCategory(category: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f${viewPager.currentItem}")
        when (currentFragment) {
            is PendingArticlesFragment -> currentFragment.filterByCategory(category)
            is ApprovedArticlesFragment -> currentFragment.filterByCategory(category)
            is RejectedArticlesFragment -> currentFragment.filterByCategory(category)
        }
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