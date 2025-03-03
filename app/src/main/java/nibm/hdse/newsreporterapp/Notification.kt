package nibm.hdse.newsreporterapp

data class Notification(
    val id: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)