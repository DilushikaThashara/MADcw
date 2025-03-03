/*package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApprovedArticlesFragment : Fragment() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter // Add a class-level adapter property
    private val articles = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)

        rvArticles = view.findViewById(R.id.rvArticles)
        setupRecyclerView()

        return view
    }

    private fun setupRecyclerView() {
        // Add sample data to articles
        articles.addAll(
            listOf(
                Article("Article 5", "Pending", "2023-10-11", "Politics"),
                Article("Article 6", "Pending", "2023-10-10", "Sports")
            )
        )

        // Fix: Use requireContext() instead of TODO()
        articleAdapter = ArticleAdapter(articles, requireContext())

        rvArticles.layoutManager = LinearLayoutManager(requireContext())
        rvArticles.adapter = articleAdapter
    }

    fun filterByCategory(category: String) {
        val filteredList = articles.filter { it.category == category }
        articleAdapter.updateList(filteredList) // Update the adapter with new data
    }

    fun filterArticles(query: String) {
        val filteredArticles = articles.filter { it.title.contains(query, true) }
        articleAdapter.updateList(filteredArticles) // Update the adapter with filtered articles
    }

}*/

package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApprovedArticlesFragment : Fragment() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private val articles = mutableListOf<Article>() // List to hold fetched articles

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)

        rvArticles = view.findViewById(R.id.rvArticles)
        setupRecyclerView()

        // Fetch approved articles from Firebase
        fetchApprovedArticles()

        return view
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(articles, requireContext())
        rvArticles.layoutManager = LinearLayoutManager(requireContext())
        rvArticles.adapter = articleAdapter
    }

    private fun fetchApprovedArticles() {
        FirebaseHelper.fetchArticlesByStatus(
            "Approved", // Fetch articles with status "Approved"
            onSuccess = { fetchedArticles ->
                articles.clear()
                articles.addAll(fetchedArticles)
                articleAdapter.updateList(articles)
            },
            onFailure = { e ->
                Toast.makeText(requireContext(), "Failed to fetch articles: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun filterByCategory(category: String) {
        val filteredList = articles.filter { it.category == category }
        articleAdapter.updateList(filteredList) // Update the adapter with new data
    }

    fun filterArticles(query: String) {
        val filteredArticles = articles.filter { it.title.contains(query, true) }
        articleAdapter.updateList(filteredArticles) // Update the adapter with filtered articles
    }
}