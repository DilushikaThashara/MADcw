package nibm.hdse.newsreporterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import android.widget.FrameLayout

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize TabLayout and FrameLayout
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)

        // Set up TabLayout with fragments
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> loadFragment(LoginFragment()) // Load LoginFragment
                    1 -> loadFragment(RegisterFragment()) // Load RegisterFragment
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Load the default fragment (LoginFragment)
        loadFragment(LoginFragment())
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }
}