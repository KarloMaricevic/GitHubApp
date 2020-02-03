package my.app.githubapp.ui.RepositorySearchView

interface RepositorySearchViewAdapterCallback {
    fun navigateToUserDetailView(userLogin : String)
    fun navigateToRepoDetailView(ownerLogin : String,repoName : String)
}