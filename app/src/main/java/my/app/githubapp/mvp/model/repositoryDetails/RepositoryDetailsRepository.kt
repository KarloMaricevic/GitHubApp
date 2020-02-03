package my.app.githubapp.mvp.model.repositoryDetails

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.utils.mapper.LanguageResponseMapper
import javax.inject.Inject

class RepositoryDetailsRepository @Inject constructor(private val mRepositoryDetailsGitHubService: RepositoryDetailsGitHubService,private val mCache : RepositoryDetailsCache){

    private var mCachingGitHubRepoDisposable : Disposable? = null
    private var mCachingGitHubUserDisposable : Disposable? = null

    fun getGitHubRepo(ownerLogin : String,repoName : String) : Observable<GitHubRepo>{

        if(mCache.isGitHubRepoCached()){
            if(ownerLogin == mCache.getGitHubRepo().owner.login && repoName == mCache.getGitHubRepo().name){
                return Observable.just(mCache.getGitHubRepo())
            }
        }

          val gitHubRepoObservable = mRepositoryDetailsGitHubService
                .getRepositoryInformation(ownerLogin,repoName)
                .subscribeOn(Schedulers.io())
                .flatMap { gitHubRepo -> getRepoWatchersCount(gitHubRepo.owner.login,gitHubRepo.name)
                .map { gitHubRepo.copy(watcherNumber = it) } }



        mCachingGitHubRepoDisposable?.dispose()
        mCachingGitHubRepoDisposable = gitHubRepoObservable.subscribe(
            {
                mCache.cacheGitHubRepo(it)
            },
            {}
        )
        return gitHubRepoObservable
    }


    private fun getRepoWatchersCount(repoOwnerLogin : String,repoName: String) : Observable<Int>{
        return mRepositoryDetailsGitHubService
            .getRepoWatchers(repoOwnerLogin,repoName)
            .subscribeOn(Schedulers.io())
            .map {
                it.count()
            }
    }

    fun getRepoLanguages(ownerLogin : String,repoName: String) : Observable<List<LanguagePercentile>>{

        if(mCache.areRepoLanguagesCached()){
            if(ownerLogin == mCache.getGitHubRepo().owner.login && repoName == mCache.getGitHubRepo().name){
                return Observable.just(mCache.getGitHubRepo().languagePercentile)
            }
        }


        mCachingGitHubRepoDisposable?.dispose()
        val getRepoLanguages = mRepositoryDetailsGitHubService
            .getRepositoryLanguage(ownerLogin,repoName)
            .subscribeOn(Schedulers.io())
            .map {
                LanguageResponseMapper.toLanguagePercentileList(it)
            }
        mCachingGitHubRepoDisposable = getRepoLanguages.subscribe(
            {
                if(mCache.isGitHubRepoCached()) {
                    val gitHubRepo = mCache.getGitHubRepo().copy(languagePercentile = it)
                    mCache.cacheGitHubRepo(gitHubRepo)
                }
            },
            {

            }
        )

        return getRepoLanguages
    }




    fun getOwnerUserInfo(owner : String) : Observable<GitHubUser> {
        if(mCache.isGitHubRepoOwnerCached()){
            if(mCache.getGitHubRepoOwner().login == owner)
                return Observable.just(mCache.getGitHubRepoOwner())
        }

        val getRepoOwnerObservable = mRepositoryDetailsGitHubService
            .getUserInfo(owner)
            .subscribeOn(Schedulers.io())

        mCachingGitHubUserDisposable?.dispose()
        mCachingGitHubUserDisposable = getRepoOwnerObservable.subscribe(
            {
                mCache.cacheGitHubOwner(it)
            },
            {

            }
        )
        return getRepoOwnerObservable
    }
}