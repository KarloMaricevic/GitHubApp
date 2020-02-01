package my.app.githubapp.mvp.model.repositorySearch

import android.app.DownloadManager
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import javax.inject.Inject

@PerFragment
class RepositorySearchCache @Inject constructor() {

    private var mGitHubRepoList: ArrayList<GitHubRepo>? = null
    private var mQuery = ""
    private var mIsSomethingCached: Boolean = false


    fun isSomethingCached() = mIsSomethingCached

    fun getCachedGitHubRepoList() = mGitHubRepoList

    fun getCachedQuery() = mQuery

    fun getGitHubRepoCount(): Int {
        return if (mGitHubRepoList == null) {
            -1
        } else {
            mGitHubRepoList!!.count()
        }

    }


    @Synchronized
    fun cacheData(gitHubRepoList: List<GitHubRepo>, query: String) {
        mGitHubRepoList = ArrayList(gitHubRepoList)
        mQuery = query
        mIsSomethingCached = true
    }





    
    
}