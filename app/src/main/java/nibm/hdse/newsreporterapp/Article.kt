package nibm.hdse.newsreporterapp

/*data class Article(
    var id: String = "",
    val title: String = "",
    val status: String = "",
    val date: String = "",
    val category: String = "",
    val content: String = "" // Add this field
) {
    // Add a no-argument constructor
    constructor() : this("", "", "", "", "", "")
}*/



data class Article(
    var id: String = "",
    val title: String = "",
    val status: String = "",
    //val date: String = "",
    val date: String = System.currentTimeMillis().toString(),
    val category: String = "",
    val content: String = "",
    val rating: Int = 0, // Add this field
    val rejectReason: String = "", // Add this field
    val reporterId: String = "" // Add this field
) {
    constructor() : this("", "", "", "", "", "", 0, "", "")
}
