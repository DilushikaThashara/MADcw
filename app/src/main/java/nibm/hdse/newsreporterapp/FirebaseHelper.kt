/*package nibm.hdse.newsreporterapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://news-reporter-app-58bdb-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    // Add a new article to the database
    fun addArticle(article: Article, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val articleId = database.child("articles").push().key // Generate a unique ID
        articleId?.let {
            article.id = it
            database.child("articles").child(it).setValue(article)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        }
    }

    // Fetch all articles from the database
    fun fetchArticles(onSuccess: (List<Article>) -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val articles = mutableListOf<Article>()
                for (child in snapshot.children) {
                    val article = child.getValue(Article::class.java)
                    article?.let { articles.add(it) }
                }
                onSuccess(articles)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(Exception(error.message))
            }
        })
    }

    // Fetch articles by status (Pending, Approved, Rejected)
    fun fetchArticlesByStatus(status: String, onSuccess: (List<Article>) -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").orderByChild("status").equalTo(status)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val articles = mutableListOf<Article>()
                    for (child in snapshot.children) {
                        val article = child.getValue(Article::class.java)
                        article?.let { articles.add(it) }
                    }
                    onSuccess(articles)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception(error.message))
                }
            })
    }

    // Update article status in the database
    fun updateArticleStatus(articleId: String, newStatus: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").child(articleId).child("status").setValue(newStatus)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}*/

package nibm.hdse.newsreporterapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseHelper {
    private val database = FirebaseDatabase.getInstance("https://news-reporter-app-58bdb-default-rtdb.asia-southeast1.firebasedatabase.app").reference

    // Add a new article to the database
    fun addArticle(article: Article, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val articleId = database.child("articles").push().key // Generate a unique ID
        articleId?.let {
            article.id = it
            database.child("articles").child(it).setValue(article)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        }
    }

    // Fetch all articles from the database
    fun fetchArticles(onSuccess: (List<Article>) -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val articles = mutableListOf<Article>()
                for (child in snapshot.children) {
                    val article = child.getValue(Article::class.java)
                    article?.let { articles.add(it) }
                }
                onSuccess(articles)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(Exception(error.message))
            }
        })
    }

    // Fetch articles by status (Pending, Approved, Rejected)
    fun fetchArticlesByStatus(status: String, onSuccess: (List<Article>) -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").orderByChild("status").equalTo(status)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val articles = mutableListOf<Article>()
                    for (child in snapshot.children) {
                        val article = child.getValue(Article::class.java)
                        article?.let { articles.add(it) }
                    }
                    onSuccess(articles)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception(error.message))
                }
            })
    }

    // Update article status in the database
    fun updateArticleStatus(articleId: String, newStatus: String, data: Any, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        database.child("articles").child(articleId).child("status").setValue(newStatus)
            .addOnSuccessListener {
                if (newStatus == "Rejected") {
                    database.child("articles").child(articleId).child("rejectReason").setValue(data)
                } else if (newStatus == "Approved") {
                    database.child("articles").child(articleId).child("rating").setValue(data)
                }
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    // Send notification to a specific user
    fun sendNotification(userId: String?, message: String) {
        if (userId != null) {
            val notificationId = database.child("notifications").child(userId).push().key
            notificationId?.let {
                val notification = Notification(it, message)
                database.child("notifications").child(userId).child(it).setValue(notification)
            }
        }
    }

    // Fetch Notifications
    fun fetchNotifications(userId: String, onSuccess: (List<Notification>) -> Unit, onFailure: (Exception) -> Unit) {
        database.child("notifications").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notifications = mutableListOf<Notification>()
                    for (child in snapshot.children) {
                        val notification = child.getValue(Notification::class.java)
                        notification?.let { notifications.add(it) }
                    }
                    onSuccess(notifications)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception(error.message))
                }
            })
    }
}