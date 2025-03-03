/*package nibm.hdse.newsreporterapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApprovalDashboardActivity : AppCompatActivity() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var spinnerCategory: Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approval_dashboard)

        // Initialize views
        rvArticles = findViewById(R.id.rvArticles)
        etSearch = findViewById(R.id.etSearch)


        // Set up RecyclerView
        setupRecyclerView()

        // Search Bar Text Change Listener
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterArticles(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupRecyclerView() {
        val articles = listOf(
            Article("Article 1", "Pending", "2023-10-15", "Politics"),
            Article("Article 2", "Approved", "2023-10-14", "Sports"),
            Article("Article 3", "Rejected", "2023-10-13", "Technology")
        )

        val adapter = ArticleAdapter(
            articles,
            context = TODO()
        ) // Pass 'this' as context
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.adapter = adapter
    }

    private fun filterArticles(query: String) {
        val articles = getArticlesByCategory(spinnerCategory.selectedItem.toString())
        val filteredArticles = articles.filter { it.title?.contains(query, true) ?: false }
        val adapter = ArticleAdapter(
            filteredArticles,
            context = TODO()
        )
        rvArticles.adapter = adapter
    }
    private fun getArticlesByCategory(category: String): List<Article> {
        // Fetch articles from Firebase or a local list
        return listOf(
            Article("Article 1", "Pending", "2023-10-15", "Politics"),
            Article("Article 2", "Approved", "2023-10-14", "Sports"),
            Article("Article 3", "Rejected", "2023-10-13", "Technology")
        ).filter { it.category == category }
    }
    }*/

package nibm.hdse.newsreporterapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApprovalDashboardActivity : AppCompatActivity() {

    private lateinit var rvArticles: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var articleAdapter: ArticleAdapter
    private val articles = mutableListOf<Article>() // List to hold fetched articles

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approval_dashboard)

        // Initialize views
        rvArticles = findViewById(R.id.rvArticles)
        etSearch = findViewById(R.id.etSearch)
        spinnerCategory = findViewById(R.id.spinnerCategory)

        // Set up RecyclerView
        setupRecyclerView()

        // Fetch articles from Firebase
        fetchArticlesFromFirebase()

        // Search Bar Text Change Listener
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterArticles(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(articles, this) // Pass 'this' as context
        rvArticles.layoutManager = LinearLayoutManager(this)
        rvArticles.adapter = articleAdapter
    }

    private fun fetchArticlesFromFirebase() {
        FirebaseHelper.fetchArticles(
            onSuccess = { fetchedArticles ->
                articles.clear()
                articles.addAll(fetchedArticles)
                articleAdapter.updateList(articles)
            },
            onFailure = { e ->
                Toast.makeText(this, "Failed to fetch articles: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun filterArticles(query: String) {
        val filteredArticles = if (query.isEmpty()) {
            articles // Show all articles if the query is empty
        } else {
            articles.filter { it.title?.contains(query, true) ?: false }
        }
        articleAdapter.updateList(filteredArticles)
    }

    private fun getArticlesByCategory(category: String): List<Article> {
        return articles.filter { it.category == category }
    }
}