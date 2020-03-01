package my.app.githubapp.domain

open class BasicGitHubRepo(
    val name: String,
    val id: Int,
    val owner: BasicGitHubUser
)
