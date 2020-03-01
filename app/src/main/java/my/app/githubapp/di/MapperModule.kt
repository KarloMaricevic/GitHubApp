package my.app.githubapp.di

import dagger.Module
import dagger.Provides
import my.app.githubapp.domain.BasicGitHubRepo
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentage
import my.app.githubapp.mvp.model.retrofitService.responses.BasicGitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.responses.BasicGitHubUserResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoContributorResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoResponse
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubUserResponse
import my.app.githubapp.mvp.model.retrofitService.responses.LanguageResponse
import my.app.githubapp.utils.mapper.BasicGitHubRepoResponseMapper
import my.app.githubapp.utils.mapper.BasicGitHubUserResponseMapper
import my.app.githubapp.utils.mapper.BasicListMapper
import my.app.githubapp.utils.mapper.GitHubRepoContributorsResponseMapper
import my.app.githubapp.utils.mapper.GitHubRepoResponseMapper
import my.app.githubapp.utils.mapper.GitHubUserResponseMapper
import my.app.githubapp.utils.mapper.LanguageResponseMapper
import my.app.githubapp.utils.mapper.MapperInterface
import javax.inject.Singleton

@Suppress("MaxLineLength")
@Module
interface MapperModule {

    companion object {

        @Provides
        @Singleton
        fun providesBasicGitHubRepoResponseMapper(): MapperInterface<BasicGitHubRepoResponse, BasicGitHubRepo> {
            return BasicGitHubRepoResponseMapper
        }

        @Provides
        @Singleton
        fun providesBasicGitHubUserResponseMapper(): MapperInterface<BasicGitHubUserResponse, BasicGitHubUser> {
            return BasicGitHubUserResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubRepoContributorsResponseMapper(): BasicListMapper<GitHubRepoContributorResponse, GitHubContributor> {
            return GitHubRepoContributorsResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubRepoResponseMapper(): BasicListMapper<GitHubRepoResponse, GitHubRepo> {
            return GitHubRepoResponseMapper
        }

        @Provides
        @Singleton
        fun providesGitHubUserResponseMapper(): MapperInterface<GitHubUserResponse, GitHubUser> {
            return GitHubUserResponseMapper
        }

        @Provides
        @Singleton
        fun providesLanguageResponseMapper(): MapperInterface<LanguageResponse, List<LanguagePercentage>> {
            return LanguageResponseMapper
        }
    }
}
