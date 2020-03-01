package my.app.githubapp.domain

import java.util.Date

class GitHubRepo(
    name: String,
    id: Int,
    owner: BasicGitHubUser,
    val fullName: String,
    val pageUrl: String,
    val staredNumber: Int,
    val forks: Int,
    val openIssues: Int,
    val watcherNumber: Int,
    val createdAt: Date,
    val language: String?
) : BasicGitHubRepo(name, id, owner) {

    override fun equals(other: Any?): Boolean {
        if (other is GitHubRepo) {
            return this.id == other.id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id
    }
}
