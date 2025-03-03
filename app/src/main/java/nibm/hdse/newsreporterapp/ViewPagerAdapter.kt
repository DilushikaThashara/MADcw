package nibm.hdse.newsreporterapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle)
{

    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingArticlesFragment() // Fragment for Pending articles
            1 -> ApprovedArticlesFragment() // Fragment for Approved articles
            2 -> RejectedArticlesFragment() // Fragment for Rejected articles
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}