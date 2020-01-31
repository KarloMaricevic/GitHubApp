package my.app.githubapp.mvp.model.repositorySearch

import io.reactivex.Observable
import io.reactivex.rxkotlin.combineLatest
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.model.GitHubApiQueryResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RepositorySearchRepository {


    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val gitHubService = retrofit.create(RepositorySearchGitHubService::class.java)

    fun getGitHubRepositories(query : String) : Observable<List<GitHubRepo>>
     {
        val obs =  gitHubService.getReposWithQueryInName(query)
          return obs.flatMap {gitHubRepoResponse ->
             val observableList = ArrayList<Observable<HashMap<String,Int>>>()

              for (gitHubRepo in gitHubRepoResponse.responseItems){
                 observableList.add(getRepoWatchersNumber(gitHubRepo.owner.login,gitHubRepo.name,gitHubRepo.id))
             }

             return@flatMap observableList
                 .combineLatest {it}
                 .map {
                     val gitHubRepoList = ArrayList<GitHubRepo>()

                     for (hashMap in it){
                         for(gitHubRepo in gitHubRepoResponse.responseItems) {
                             if (hashMap.get("RepoId") == gitHubRepo.id){
                                 gitHubRepoList.add(gitHubRepo.copy(watcherNumber = hashMap.get("NumberOfWatchers")))
                                 break
                             }
                         }
                        }
                     return@map gitHubRepoList
             }
         }
        }



    private fun getRepoWatchersNumber(ownerName : String, repoName : String, repoId : Int) : Observable<HashMap<String,Int>>{
        return gitHubService.getRepoWatchers(ownerName,repoName).map {
            val hashMap = HashMap<String,Int>()
            hashMap.put("RepoId",repoId)
            hashMap.put("NumberOfWatchers",it.count())
            return@map hashMap
        }
    }
}
