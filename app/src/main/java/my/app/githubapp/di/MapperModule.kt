package my.app.githubapp.di

import dagger.Module
import dagger.Provides
import my.app.githubapp.domain.*
import my.app.githubapp.mvp.model.retrofitService.responses.*
import my.app.githubapp.utils.mapper.*
import javax.inject.Singleton

@Module
interface MapperModule{
    companion object{


        @Provides
        @Singleton
        fun providesBasicGitHubRepoResponseMapper() : MapperInterface<BasicGitHubRepoResponse, BasicGitHubRepo>{
            return BasicGitHubRepoResponseMapper
        }

        @Provides
        @Singleton
        fun providesBasicGitHubUserResponseMapper() : MapperInterface<BasicGitHubUserResponse, BasicGitHubUser> {
            return BasicGitHubUserResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubRepoContributorsResponseMapper() : BasicListMapper<GitHubRepoContributorResponse, GitHubContributor> {
            return GitHubRepoContributorsResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubRepoResponseMapper() : BasicListMapper<GitHubRepoResponse, GitHubRepo> {
            return GitHubRepoResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubUserResponseMapper() : MapperInterface<GitHubUserResponse, GitHubUser> {
            return GitHubUserResponseMapper
        }

        @Provides
        @Singleton
        fun providesLanguageResponseMapper() : MapperInterface<LanguageResponse, List<LanguagePercentage>> {
            return LanguageResponseMapper
        }


    }


}

