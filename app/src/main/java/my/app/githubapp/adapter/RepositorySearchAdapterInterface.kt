package my.app.githubapp.adapter

interface RepositorySearchAdapterInterface {
    fun clickedOnOwner(ownerLogin: String)
    fun clickedOnRepo(ownerLogin: String, repoName: String)
}
