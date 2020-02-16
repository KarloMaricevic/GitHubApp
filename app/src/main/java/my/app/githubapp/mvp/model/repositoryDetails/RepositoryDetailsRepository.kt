package my.app.githubapp.mvp.model.repositoryDetails

import io.reactivex.Single
import my.app.githubapp.cacher.DataSource
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsRepositoryInterface
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.caching.key.GitHubUserKey
import my.app.githubapp.mvp.model.caching.key.LanguageKey
import my.app.githubapp.mvp.model.caching.key.RepoKey
import javax.inject.Inject

class RepositoryDetailsRepository @Inject constructor(
    private val mContributorsSource : DataSource<ContributorsKey,List<GitHubContributor>>,
    private val mGitHubRepoSource : DataSource<RepoKey,GitHubRepo>,
    private val mLanguagesSource : DataSource<LanguageKey,List<LanguagePercentile>>,
    private val mOwnerSource : DataSource<GitHubUserKey,GitHubUser>

) : RepositoryDetailsRepositoryInterface {
    override fun getGitHubRepo(ownerLogin: String, repoName: String) : Single<GitHubRepo>{
        val key =
            RepoKey(ownerLogin, repoName)
        return mGitHubRepoSource.getFromMemory(key)
    }

    override fun getRepoContributors(ownerLogin : String, repoName : String ) : Single<List<GitHubContributor>> {
        val key = ContributorsKey(
            ownerLogin,
            repoName
        )
        return mContributorsSource.getFromMemory(key)
    }

    override fun getRepoLanguages(ownerLogin: String, repoName: String) : Single<List<LanguagePercentile>>{
        val key = LanguageKey(
            ownerLogin,
            repoName
        )
        return mLanguagesSource.getFromMemory(key)
    }

    override fun getOwnerDetails(ownerLogin: String) : Single<GitHubUser>{
        val key = GitHubUserKey(ownerLogin)
        return mOwnerSource.getFromMemory(key)
    }
}