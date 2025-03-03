/*package nibm.hdse.newsreporterapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(private var articles: List<Article>, private val context: Context) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.tvTitle.text = article.title
        holder.tvStatus.text = article.status
        holder.tvDate.text = article.date
        holder.tvCategory.text = article.category

        // Add Click Listener to Open Article Details
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra("title", article.title)
                putExtra("status", article.status)
                putExtra("date", article.date)
                putExtra("category", article.category)
                putExtra("userRole", "Editor")
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
    }

    // New function to update the list
    fun updateList(newList: List<Article>) {
        articles = newList
        notifyDataSetChanged() // Refresh the RecyclerView
    }
}*/

package nibm.hdse.newsreporterapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(private var articles: List<Article>, private val context: Context) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.tvTitle.text = article.title
        holder.tvStatus.text = article.status
        holder.tvDate.text = article.date
        holder.tvCategory.text = article.category

        // In your RecyclerView Adapter (ArticleAdapter.kt)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra("id", article.id)
                putExtra("title", article.title)
                putExtra("status", article.status)
                putExtra("date", article.date)
                putExtra("category", article.category)
                putExtra("userRole", "Editor") // Pass the correct role here (e.g., "Editor" or "Reporter")
                putExtra("reporterId", article.reporterId) // Pass reporterId for notifications
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateList(newList: List<Article>) {
        articles = newList
        notifyDataSetChanged()
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
    }
}