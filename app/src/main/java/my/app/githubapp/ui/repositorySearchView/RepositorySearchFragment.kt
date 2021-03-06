package my.app.githubapp.ui.repositorySearchView

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.app.githubapp.BaseApplication
import my.app.githubapp.R
import my.app.githubapp.adapter.RepositorySearchAdapter
import my.app.githubapp.databinding.FragmentRepositorySearchBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterAbstraction
import my.app.githubapp.utils.AUTH_LINK
import my.app.githubapp.utils.CLIENT_ID
import javax.inject.Inject
import kotlin.system.exitProcess

@Suppress("TooManyFunctions")
class RepositorySearchFragment : Fragment(),
    RepositorySearchContract.RepositorySearchView,
    SearchView.OnQueryTextListener,
    RepositorySearchViewAdapterCallback {

    @Inject
    lateinit var mPresenter: RepositorySearchPresenterAbstraction

    private lateinit var mBinding: FragmentRepositorySearchBinding

    private lateinit var mAdapter: RepositorySearchAdapter

    private var mStateView: RepositorySearchContract.RepositorySearchViewStateInterface? = null

    private lateinit var mSnackbar: Snackbar

    private val mOnSearchActionClickListener = { it: View ->
        (it as SearchView).setQuery(
            mPresenter.getState().getQuery(),
            false
        )
        val width = Resources.getSystem().displayMetrics.widthPixels
        it.maxWidth = width
    }

    //region lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getRepositorySearchSubcomponent()
            .inject(this)

        super.onCreate(savedInstanceState)

        mAdapter = RepositorySearchAdapter(this, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRepositorySearchBinding.inflate(inflater, container, false)

        mBinding.githubBasicInfoRecyclerView.adapter = mAdapter

        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as SearchView)
            .setOnQueryTextListener(this)

        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as SearchView)
            .setOnSearchClickListener(mOnSearchActionClickListener)

        setUpSortMenu()
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            mStateView = savedInstanceState.getParcelable(getString(R.string.viewState))
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this, mStateView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(getString(R.string.viewState), mPresenter.getState())
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    //endregion

    //region view-presenter methods

    override fun showData(data: List<GitHubRepo>) {
        mAdapter.setData(data)
    }

    override fun queryError() {
        mSnackbar = Snackbar
            .make(mBinding.root, R.string.query_error, Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                mPresenter.tryQueryAgain()
            }
    }

    override fun fatalError() {
        exitProcess(1)
    }

    //endregion

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            mPresenter.searchForRepos(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun navigateToUserDetailView(userLogin: String) {
        val action =
            RepositorySearchFragmentDirections.actionRepositorySearchFragment2ToUserDetailsFragment(
                userLogin
            )
        (activity!!.application as BaseApplication).releaseRepositorySearchSubcomponent()
        findNavController().navigate(action)
    }

    override fun navigateToRepoDetailView(ownerLogin: String, repoName: String) {
        val action =
            RepositorySearchFragmentDirections.actionRepositorySearchFragment2ToRepositoryDetailsScreen(
                ownerLogin,
                repoName
            )
        (activity!!.application as BaseApplication).releaseRepositorySearchSubcomponent()
        findNavController().navigate(action)
    }

    private fun setUpSortMenu() {
        (mBinding.searchToolbar.menu.findItem(R.id.action_sort).subMenu as Menu)
            .findItem(R.id.sort_by_repo_name)
            .setOnMenuItemClickListener {
                mPresenter.sortShowingRepos(SORT_BY_REPO_NAME)
                return@setOnMenuItemClickListener true
            }

        (mBinding.searchToolbar.menu.findItem(R.id.action_sort).subMenu as Menu)
            .findItem(R.id.sort_by_star_item)
            .setOnMenuItemClickListener {
                mPresenter.sortShowingRepos(SORT_BY_STAR)
                return@setOnMenuItemClickListener true
            }

        (mBinding.searchToolbar.menu.findItem(R.id.action_sort).subMenu as Menu)
            .findItem(R.id.sort_by_updated_item)
            .setOnMenuItemClickListener {
                mPresenter.sortShowingRepos(SORT_BY_DATE)
                return@setOnMenuItemClickListener true
            }
        (mBinding.searchToolbar.menu.findItem(R.id.action_sort).subMenu as Menu)
            .findItem(R.id.sort_by_forks_item)
            .setOnMenuItemClickListener {
                mPresenter.sortShowingRepos(SORT_BY_FORKED)
                return@setOnMenuItemClickListener true
            }
        (mBinding).searchToolbar.menu.findItem(R.id.login_item).setOnMenuItemClickListener {

            val builder = CustomTabsIntent.Builder()
                .build()
            builder.launchUrl(context!!, Uri.parse(AUTH_LINK + CLIENT_ID))
            return@setOnMenuItemClickListener true
        }
    }
}
