package my.app.githubapp.mvp.model.caching.networkDataSource

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.mvp.model.caching.key.ContributorsKey
import my.app.githubapp.mvp.model.retrofitService.RepositoryGitHubService
import my.app.githubapp.mvp.model.retrofitService.responses.GitHubRepoContributorResponse
import my.app.githubapp.utils.mapper.BasicListMapper
import my.app.githubapp.utils.mapper.GitHubRepoContributorsResponseMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.UnknownHostException


@ExtendWith(MockKExtension::class)
class ContributorsNetworkDataSourceTest(@MockK val mServiceDOC: RepositoryGitHubService,@MockK val mMapperDOC : BasicListMapper<GitHubRepoContributorResponse, GitHubContributor>) {

    private val stringForPopulatedResponse = "stringForPopulatedResponse"
    private val stringForNoNetworkErrorResponse = "stringForErrorResponse"


    private val testNumbersOfContributions = 5
    private val testIdNumber = 6
    private val testIsSiteAdmin = false


    private val populatedList = listOf(
        GitHubRepoContributorResponse(
            "avatarURL",
            testNumbersOfContributions,
            "eventsURL",
            "folowersUrl",
            "followingUrl",
            "gistsUI",
            "gravatarId",
            "htmlUrl",
            testIdNumber,
            "login",
            "nodeId",
            "organizazionUrl",
            "receivedEventsUrl",
            "reposUrl",
            testIsSiteAdmin,
            "starredUrl",
            "subscriptionsUrl",
            "type",
            "url"
        )
    )

    @BeforeEach
    fun mockService() {
        every {
            mServiceDOC.getRepositoryContributors(
                stringForPopulatedResponse,
                stringForPopulatedResponse
            )
        } returns Single.just(populatedList)
        every {
            mServiceDOC.getRepositoryContributors(
                stringForNoNetworkErrorResponse,
                stringForNoNetworkErrorResponse
            )
        } returns Single.error(UnknownHostException())
    }

    @Test
    fun sendsValidOutputForPopulatedServiceResponse() {
        val dataSourceSUT = ContributorsNetworkDataSource(mServiceDOC,mMapperDOC)

        val testObserver: TestObserver<List<GitHubContributor>> = dataSourceSUT.getData(
            ContributorsKey(
                stringForPopulatedResponse,
                stringForPopulatedResponse
            )
        ).test()

        testObserver.assertValue(
            GitHubRepoContributorsResponseMapper.convertList(populatedList)
        )
    }

    @Test
    fun sendsUnknownHostExceptionForNoNetworkServiceResponse() {
        val dataSourceSUT = ContributorsNetworkDataSource(mServiceDOC,mMapperDOC)

        val testObserver: TestObserver<List<GitHubContributor>> = dataSourceSUT.getData(
            ContributorsKey(
                stringForNoNetworkErrorResponse,
                stringForNoNetworkErrorResponse
            )
        ).test()

        testObserver.assertError(
            UnknownHostException::class.java
        )
    }

}