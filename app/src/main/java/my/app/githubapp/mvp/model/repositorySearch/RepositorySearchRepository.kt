package my.app.githubapp.mvp.model.repositorySearch

import android.util.Log
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.rxkotlin.toFlowable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.reactive.asFlow
import my.app.githubapp.di.scope.PerFragment
import my.app.githubapp.domain.GitHubRepo
import retrofit2.Call
import javax.inject.Inject


@PerFragment
class RepositorySearchRepository @Inject constructor(private val mRepositorySearchCache: RepositorySearchCache, private val  mRepositorySearchGitHubService: RepositorySearchGitHubService) {


    private lateinit var disposable : Disposable

    fun getGitHubRepositories(query : String) : Observable<List<GitHubRepo>>
     {
         if(mRepositorySearchCache.isSomethingCached()){
             if(query == mRepositorySearchCache.getCachedQuery()) {
                 return Observable.just(mRepositorySearchCache.getCachedGitHubRepoList())
             }
         }
         val observableRequest =  mRepositorySearchGitHubService.getReposWithQueryInName(query).subscribeOn(Schedulers.io())
         val completedGitHubRepoObservable =   observableRequest.flatMap {gitHubRepoResponse ->

             if(gitHubRepoResponse.totalCountOfRepositories == 0){
                 return@flatMap Observable.just(emptyList<GitHubRepo>())
             }

             val observableList = ArrayList<Observable<HashMap<String,Int>>>()

              for (gitHubRepo in gitHubRepoResponse.responseItems){
                 observableList.add(getRepoWatchersNumber(gitHubRepo.owner.login,gitHubRepo.name,gitHubRepo.id))
             }

             return@flatMap observableList
                 .combineLatest {it}
                 .map<List<GitHubRepo>> {
                     val gitHubRepoList = ArrayList<GitHubRepo>()

                     for (hashMap in it){
                         for(gitHubRepo in gitHubRepoResponse.responseItems) {
                             if (hashMap["RepoId"] == gitHubRepo.id){
                                 gitHubRepoList.add(gitHubRepo.copy(watcherNumber = hashMap["NumberOfWatchers"]))
                                 break
                             }
                         }
                        }
                     return@map gitHubRepoList
             }
         }
         cacheObservableEmit(completedGitHubRepoObservable,query)

         return completedGitHubRepoObservable
        }



    private fun getRepoWatchersNumber(ownerName : String, repoName : String, repoId : Int) : Observable<HashMap<String,Int>>{
        return mRepositorySearchGitHubService.getRepoWatchers(ownerName,repoName).map {
            val hashMap = HashMap<String,Int>()
            hashMap["RepoId"] = repoId
            hashMap["NumberOfWatchers"] = it.count()
            return@map hashMap
        }
    }

    private fun cacheObservableEmit (observable : Observable<List<GitHubRepo>>,query : String){
        disposeCacherDisposable()
        disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cacheGitHubRepoList(it,query) },
                { Log.e("Caching error:",it?.message) },
                {disposable.dispose()})
    }


    private fun cacheGitHubRepoList(gitHubRepoList : List<GitHubRepo>,query : String){
        mRepositorySearchCache.cacheData(gitHubRepoList,query)
    }
    
    private fun disposeCacherDisposable(){
        if(::disposable.isInitialized){
            if(!disposable.isDisposed){
                disposable.dispose()
            }
        }
    }
}
