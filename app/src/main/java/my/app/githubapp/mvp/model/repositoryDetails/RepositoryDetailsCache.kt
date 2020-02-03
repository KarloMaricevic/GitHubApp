package my.app.githubapp.mvp.model.repositoryDetails

import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import java.lang.Exception
import javax.inject.Inject


@PerFragment
class RepositoryDetailsCache @Inject constructor() {
    private lateinit var mGitHubRepo: GitHubRepo
    private lateinit var mGitHubRepoOwner : GitHubUser




    fun isGitHubRepoCached(): Boolean = ::mGitHubRepo.isInitialized

    fun areRepoLanguagesCached(): Boolean {
        if(isGitHubRepoCached()){
            return mGitHubRepo.languagePercentile != null
        }
        else{
            return false
        }
    }



    fun getGitHubRepo() : GitHubRepo{
        if(isGitHubRepoCached())
            return mGitHubRepo
        else{
            throw Exception("Trying to access not initialized variable,check it First!")
        }
    }

    fun getLanguages(): List<LanguagePercentile>? {
        if(!areRepoLanguagesCached()){
            return null
        }
        else{
            return mGitHubRepo.languagePercentile
        }
    }

    @Synchronized
    fun cacheGitHubRepo(gitHubRepo: GitHubRepo) {
        mGitHubRepo = gitHubRepo
    }

    fun isGitHubRepoOwnerCached() = ::mGitHubRepoOwner.isInitialized

    fun getGitHubRepoOwner() : GitHubUser{
        if(isGitHubRepoOwnerCached()) {
            return mGitHubRepoOwner
        }
        else{
            throw Exception("Trying to access not initialized variable,check it First!")
        }
    }

    @Synchronized
    fun cacheGitHubOwner(owner: GitHubUser) {
        mGitHubRepoOwner = owner
    }

}