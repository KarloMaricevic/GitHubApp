package my.app.githubapp.ui.RepositorySearchView

interface RepositorySearchViewAdapterCallback {
    fun navigateToUserDetailView(userId : Int)
    fun navigateToRepoDetailView(repoId : Int)
}