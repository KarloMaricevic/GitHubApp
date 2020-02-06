package my.app.githubapp.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.LanguageResponse
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.ContributorsResponse
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.responceModel.GitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.queryGitHubService.QueryGitHubService
import my.app.githubapp.mvp.model.retrofitService.repositoryGitHubService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.UserGitHubService
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUserResponse
import my.app.githubapp.mvp.model.retrofitService.userGitHubService.responceModel.GitHubUsersRepositoriesResponse
import my.app.githubapp.utils.desirilizer.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface AppComponentModule {


    companion object{
        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit {
            val repoLanguageDeserializer = GsonBuilder()
                .setLenient()
                .registerTypeAdapter(
                    ContributorsResponse::class.java,
                    ContributorsResponseDeserializer())
                .registerTypeAdapter(
                    LanguageResponse::class.java,
                    LanguageResponseDeserializer())
                .registerTypeAdapter(
                    GitHubRepoResponse::class.java,
                    GitHubRepoResponseDeserializer())
                .registerTypeAdapter(
                    GitHubUser::class.java,
                    GitHubUserDeserializer())
                .registerTypeAdapter(
                    GitHubUserResponse::class.java,
                    GitHubUserResponseDeserializer()
                )
                .registerTypeAdapter(
                    GitHubUsersRepositoriesResponse::class.java,
                    GitHubUsersRepositoryResponseDeserializer()

                )
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
        fun providesQueryGitHubService(retrofit: Retrofit) : QueryGitHubService {
            return retrofit.create(QueryGitHubService::class.java)
        }


        @Singleton
        @Provides
        fun providesRepositoryGitHubService(retrofit: Retrofit) : RepositoryGitHubService {
            return retrofit.create(RepositoryGitHubService::class.java)
        }

        @Singleton
        @Provides
        fun providesUserGitHubService(retrofit: Retrofit) : UserGitHubService {
            return retrofit.create(UserGitHubService::class.java)
        }

    }
}