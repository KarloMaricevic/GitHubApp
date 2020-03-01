package my.app.githubapp.domain

class GitHubContributor constructor(
    userLogin: String,
    userId: Int,
    userThumbnailUrl: String,
    val numberOfCommits: Int
) : BasicGitHubUser(userLogin, userId, userThumbnailUrl) {

    constructor(author: BasicGitHubUser, numberOfCommits: Int) : this(
        author.login,
        author.id,
        author.thumbnailUrl,
        numberOfCommits
    )

    override fun equals(other: Any?): Boolean {
        if (other is GitHubContributor) {
            return other.id == id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id
    }
}
