package my.app.githubapp.mvp.presenter

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.reactivex.Single
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchView
import my.app.githubapp.testExtensition.SchedulersProviderInterfaceResolution
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.util.*


@ExtendWith(MockKExtension::class, SchedulersProviderInterfaceResolution::class)
class RepositorySearchPresenterUnitTest(

    private val mThreadDOC: SchedulersProviderInterface,
    @MockK val mInteractorDOC: RepositorySearchInteractorInterface,
    @RelaxedMockK val mViewDOC: RepositorySearchView

) {


    private val emptyResponseForStringQuery = "String that returns empty query response"
    private val populatedResponseForStringQuery = "String that returns populated query response"
    private val noNetworkErrorResponse = "String that returns network error query response"


    private val populatedList = listOf<GitHubRepo>(
        GitHubRepo(
            "RepoName1",
            1,
            BasicGitHubUser("UserLogin1", 1, "htpps://pictureURL1"),
            "RepoFullName1",
            "htpps://RepoURL1",
            0,
            0,
            0,
            0,
            Date.from(Instant.now()),
            null
        ),
        GitHubRepo(
            "RepoName2",
            2,
            BasicGitHubUser("UserLogin2", 2, "htpps://pictureURL2"),
            "RepoFullName2",
            "htpps://RepoURL2",
            0,
            0,
            0,
            0,
            Date.from(Instant.now()),
            null
        )
    )


    @BeforeEach
    fun mockInteractorDOC() {
        every { mInteractorDOC.getReposForQuery(emptyResponseForStringQuery) }.returns(
            Single.just(
                listOf()
            )
        )
        every { mInteractorDOC.getReposForQuery(populatedResponseForStringQuery) } returns (Single.just(
            populatedList
        ))
        every { mInteractorDOC.getReposForQuery(noNetworkErrorResponse) }.returns(
            Single.error(
                ArrayIndexOutOfBoundsException()
            )
        )
    }


    @Test
    fun searchForQueryCallsShowDataOnEmptyResponse() {
        val mPresenterSUT = RepositorySearchPresenter(mInteractorDOC, mThreadDOC)

        mPresenterSUT.searchForRepos(emptyResponseForStringQuery)

        //Verification failed but I can see it makes calls
        verify {
            mViewDOC.showData(emptyList())
        }
    }


    @Test
    fun searchForQueryCallsShowDataOnPopulatedResponse() {

        val mPresenterSUT = RepositorySearchPresenter(mInteractorDOC, mThreadDOC)

        mPresenterSUT.searchForRepos(populatedResponseForStringQuery)

        //Verification failed but I can see it makes calls
        verify {
            mViewDOC.showData(populatedList)
        }
    }

    @Test
    fun searchForQueryCallsShowDataOnErrorResponse() {

        val mPresenterSUT = RepositorySearchPresenter(mInteractorDOC, mThreadDOC)

        mPresenterSUT.searchForRepos(noNetworkErrorResponse)

        //Verification failed but I can see it makes calls
        verify {
            mViewDOC.queryError()
        }
    }
}