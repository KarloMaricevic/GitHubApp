package my.app.githubapp.mvp.presenter

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyOrder
import io.reactivex.Single
import my.app.githubapp.domain.BasicGitHubUser
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchInteractorInterface
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchView
import my.app.githubapp.testExtensition.SchedulersProviderInterfaceResolution
import my.app.githubapp.ui.repositorySearchView.SORT_BY_DATE
import my.app.githubapp.ui.repositorySearchView.SORT_BY_FORKED
import my.app.githubapp.ui.repositorySearchView.SORT_BY_REPO_NAME
import my.app.githubapp.ui.repositorySearchView.SORT_BY_STAR
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import my.app.githubapp.utils.sorter.SorterInterface
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Instant
import java.util.*

@ExtendWith(MockKExtension::class, SchedulersProviderInterfaceResolution::class)
class RepositorySearchPresenterUnitTest(

    mThreadDOC: SchedulersProviderInterface,
    @MockK val mInteractorDOC: RepositorySearchInteractorInterface,
    @RelaxedMockK val mViewDOC: RepositorySearchView,
    @MockK val mSorterDOC : SorterInterface<GitHubRepo>

) {

    val mPresenterSUT = RepositorySearchPresenter(mInteractorDOC,mThreadDOC,mSorterDOC)


    private val emptyResponseForStringQuery = "String that returns empty query response"
    private val populatedResponseForStringQuery = "String that returns populated query response"
    private val noNetworkErrorResponse = "String that returns network error query response"

    private val populatedList = listOf(
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
    fun subscribeViewDOCtoPresenterSUT(){
        mPresenterSUT.subscribe(mViewDOC)
    }

    @BeforeEach
    fun mockInteractorDOC(){
        every { mInteractorDOC.getReposForQuery(populatedResponseForStringQuery) } returns (Single.just(populatedList))
    }

    @BeforeEach
    fun mockSorterDOC()
    {
        every { mSorterDOC.sortIterable(populatedList, SORT_BY_REPO_NAME) } returns populatedList.sortedBy { it.name }
        every { mSorterDOC.sortIterable(populatedList, SORT_BY_FORKED) } returns populatedList.sortedBy { it.forks }
        every { mSorterDOC.sortIterable(populatedList, SORT_BY_DATE) } returns populatedList.sortedBy { it.createdAt }
        every { mSorterDOC.sortIterable(populatedList, SORT_BY_STAR) } returns populatedList.sortedBy { it.watcherNumber }

        every { mSorterDOC.sortIterable(listOf(), SORT_BY_REPO_NAME) } returns listOf()
        every { mSorterDOC.sortIterable(listOf(), SORT_BY_FORKED) } returns listOf()
        every { mSorterDOC.sortIterable(listOf(), SORT_BY_DATE) } returns listOf()
        every { mSorterDOC.sortIterable(listOf(), SORT_BY_STAR) } returns listOf()


    }


    @Nested
    inner class GetReposForQueryMethodTest {

        @BeforeEach
        fun mockInteractorDOC() {
            every { mInteractorDOC.getReposForQuery(emptyResponseForStringQuery) }.returns(Single.just(listOf()))
            every { mInteractorDOC.getReposForQuery(noNetworkErrorResponse) }.returns(Single.error(ArrayIndexOutOfBoundsException()))
        }

        @TestFactory
        fun searchForQueryOnEmptyResponse() : Collection<DynamicTest> {
            mPresenterSUT.searchForRepos(emptyResponseForStringQuery)

            return listOf(
                DynamicTest.dynamicTest("Changed value of state to query in presenter") { Assertions.assertEquals(emptyResponseForStringQuery,mPresenterSUT.getState().getQuery()) },
                DynamicTest.dynamicTest("View's showData method(with right parameters) called") {
                    verify {
                        mViewDOC.showData(emptyList())
                    }}
            )
        }


        @TestFactory
        fun searchForQueryOnPopulatedResponse() : Collection<DynamicTest> {

            mPresenterSUT.searchForRepos(populatedResponseForStringQuery)

            return listOf(
                DynamicTest.dynamicTest("Changed value of state to query in presenter") { Assertions.assertEquals(populatedResponseForStringQuery,mPresenterSUT.getState().getQuery()) },
                DynamicTest.dynamicTest("View's showData method(with right parameters) called") {
                    verify {
                        mViewDOC.showData(populatedList)
                    }}
            )
        }

        @TestFactory
        fun searchForQueryOnErrorResponse() : Collection<DynamicTest> {

            mPresenterSUT.searchForRepos(noNetworkErrorResponse)

            return listOf(
                DynamicTest.dynamicTest("Changed value of state to query in presenter") { Assertions.assertEquals(noNetworkErrorResponse,mPresenterSUT.getState().getQuery()) },
                DynamicTest.dynamicTest("View's queryError method called") {
                    verify {
                        mViewDOC.queryError()
                    }}
            )
        }
    }

    @Nested
    inner class SortShowingReposTest{

        @TestFactory
        fun callingSortShowingWithSortByRepoName() : Collection<DynamicTest>{
            mPresenterSUT.searchForRepos(populatedResponseForStringQuery)

            mPresenterSUT.sortShowingRepos(SORT_BY_REPO_NAME)

            return listOf(
                DynamicTest.dynamicTest("View's showData method(with right parameters) called"){
                    verify {
                        mViewDOC.showData(populatedList.sortedBy { it.name })
                    }
                },
                DynamicTest.dynamicTest("Sorter sort method(with right parameters) called"){
                 verify {
                     mSorterDOC.sortIterable(populatedList, SORT_BY_REPO_NAME)
                 }
                },

                DynamicTest.dynamicTest("dependency methods called in right order"){
                    verifyOrder {
                        mSorterDOC.sortIterable(any(), any())
                        mViewDOC.showData(any())
                    }
                },

                DynamicTest.dynamicTest("Changed SortBy state"){Assertions.assertEquals(SORT_BY_REPO_NAME,mPresenterSUT.getState().getSortBy())}
            )
        }
    }

}