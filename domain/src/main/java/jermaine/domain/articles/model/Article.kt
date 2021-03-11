package jermaine.domain.articles.model


data class Article(
        var id: String = "",
        var source: Source = Source(),
        var author: String = "",
        var title: String = "",
        var description: String = "",
        var url: String = "",
        var urlToImage: String = "",
        var publishedAt: String = "", 
        var bookmarked: Boolean = false
) {
    companion object {
        fun getTestData() = """[{"title": "My Article", "description": "My Article Description"}, {"title": "My Article", "description": "My Article Description"}, {"title": "My Article", "description": "My Article Description"}, {"title": "My Article", "description": "My Article Description"}, {"title": "My Article", "description": "My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}, {"title": "My Article", "description": "My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description My Article Description"}]""".trimMargin()
    }
}