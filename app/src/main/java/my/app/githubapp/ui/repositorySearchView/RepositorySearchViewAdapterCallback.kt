package my.app.githubapp.ui.repositorySearchView

interface RepositorySearchViewAdapterCallback {
    fun navigateToUserDetailView(userLogin: String)
    fun navigateToRepoDetailView(ownerLogin: String, repoName: String)
}
