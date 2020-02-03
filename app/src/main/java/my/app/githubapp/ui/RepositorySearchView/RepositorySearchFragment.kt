package my.app.githubapp.ui.RepositorySearchView


import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import my.app.githubapp.BaseApplication
import my.app.githubapp.R

import my.app.githubapp.adapter.RepositorySearchAdapter
import my.app.githubapp.databinding.FragmentRepositorySearchBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterInterface
import javax.inject.Inject


class RepositorySearchFragment : Fragment(), RepositorySearchContract.RepositorySearchView,
    androidx.appcompat.widget.SearchView.OnQueryTextListener,
    RepositorySearchViewAdapterCallback {


    @Inject
    lateinit var mPresenter : RepositorySearchPresenterInterface

    private lateinit var mBinding : FragmentRepositorySearchBinding

    private lateinit var mAdapter :RepositorySearchAdapter

    private var mStateView : RepositorySearchContract.RepositorySearchViewStateInterface? = null


    private val mOnSearchActionClickListener = { it : View ->
        (it as androidx.appcompat.widget.SearchView).setQuery(mPresenter.getState().getQuery(),false)
        val width = Resources.getSystem().displayMetrics.widthPixels
        it.maxWidth = width
    }

    //region lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getRepositorySearchSubcomponent()
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         mBinding = FragmentRepositorySearchBinding.inflate(inflater,container,false)
        mAdapter = RepositorySearchAdapter(context!!,this)

        mBinding.githubBasicInfoRecyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.githubBasicInfoRecyclerView.adapter = mAdapter

        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView).setOnQueryTextListener(this)
        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView).setOnSearchClickListener(mOnSearchActionClickListener)

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
        return mBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null){
            mStateView = savedInstanceState.getParcelable("viewState")
        }
    }


    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this,mStateView)
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("viewState",mPresenter.getState())
    }


    //endregion

    //region view-presenter methods

    override fun showData(data: List<GitHubRepo>) {
        mAdapter.setData(data)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            mPresenter.searchForRepos(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    //endregion

    override fun navigateToUserDetailView(userLogin: String) {
        val action = RepositorySearchFragmentDirections.actionRepositorySearchFragment2ToUserDetailsFragment(userLogin)
        (activity!!.application as BaseApplication).releaseRepositorySearchSobcomponent()
        findNavController().navigate(action)
    }

    override fun navigateToRepoDetailView(ownerLogin: String, repoName: String) {
        val action = RepositorySearchFragmentDirections.actionRepositorySearchFragment2ToRepositoryDetailsScreen(ownerLogin,repoName)
        (activity!!.application as BaseApplication).releaseRepositorySearchSobcomponent()
        findNavController().navigate(action)
    }
}
