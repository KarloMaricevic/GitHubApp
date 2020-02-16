package my.app.githubapp.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import my.app.githubapp.mvp.model.retrofitService.authGitHubService.AuthGitHubService
import my.app.githubapp.mvp.model.retrofitService.queryGitHubService.QueryGitHubService
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.ContributorsResponse
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.LanguageResponse
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.UserGitHubService
import my.app.githubapp.utils.desirilizer.ContributorsResponseDeserializer
import my.app.githubapp.utils.desirilizer.LanguageResponseDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
interface AppComponentModule {


    companion object{
        @Singleton
        @Provides
        @Named("GitHubApiRetrofit")
        fun providesApiRetrofit(): Retrofit {
            val repoLanguageDeserializer = GsonBuilder()
                .setLenient()
                .registerTypeAdapter(
                    ContributorsResponse::class.java,
                    ContributorsResponseDeserializer())
                .registerTypeAdapter(
                    LanguageResponse::class.java,
                    LanguageResponseDeserializer())
                .create()

            return Retrofit
                .Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(repoLanguageDeserializer))
                .build()
        }

        @Singleton
        @Provides
        @Named("GitHubAuthRetrofit")
        fun providesAuthRetrofit() : Retrofit{
            return  Retrofit
                .Builder()
                .baseUrl("https://github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        @Singleton
        @Provides
        fun providesQueryGitHubService(@Named("GitHubApiRetrofit") retrofit: Retrofit) : QueryGitHubService {
            return retrofit.create(QueryGitHubService::class.java)
        }


        @Singleton
        @Provides
        fun providesRepositoryGitHubService(@Named("GitHubApiRetrofit") retrofit: Retrofit) : RepositoryGitHubService {

            return retrofit.create(RepositoryGitHubService::class.java)
        }

        @Singleton
        @Provides
        fun providesUserGitHubService(@Named("GitHubApiRetrofit") retrofit: Retrofit) : UserGitHubService {
            return retrofit.create(UserGitHubService::class.java)
        }

        @Provides
        @Singleton
        fun providesAuthGitHubService(@Named("GitHubAuthRetrofit") retrofit: Retrofit) : AuthGitHubService{
            return retrofit.create(AuthGitHubService::class.java)
        }

        @Provides
        @Singleton
        @Named("MainThread")
        fun providesMainThread() : Scheduler {
            return AndroidSchedulers.mainThread()
        }

    }
}