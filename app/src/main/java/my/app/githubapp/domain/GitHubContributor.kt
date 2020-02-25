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
            return other.login == login && other.numberOfCommits == numberOfCommits && other.id == id && other.thumbnailUrl == other.thumbnailUrl
        }
        return super.equals(other)
    }
}