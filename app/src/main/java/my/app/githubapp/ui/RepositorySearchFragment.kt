package my.app.githubapp.ui


import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import my.app.githubapp.R

import my.app.githubapp.adapter.RepositorySearchAdapter
import my.app.githubapp.core.BaseView
import my.app.githubapp.databinding.FragmentRepositorySearchBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.mvp.contract.RepositorySearchContract
import my.app.githubapp.mvp.contract.RepositorySearchContract.RepositorySearchPresenterInterface
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchInteractor
import my.app.githubapp.mvp.model.repositorySearch.RepositorySearchRepository
import my.app.githubapp.mvp.presenter.RepositorySearchPresenter



class RepositorySearchFragment : Fragment(), RepositorySearchContract.RepositorySearchView,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {



    lateinit var mBinding : FragmentRepositorySearchBinding
    lateinit var mAdapter :RepositorySearchAdapter
    lateinit var mPresenter : RepositorySearchPresenterInterface
    var mStateView : RepositorySearchContract.RepositorySearchViewStateInterface? = null


    val onSearchClickListener = {  it : View ->
        (it as androidx.appcompat.widget.SearchView).setQuery(mPresenter.getState().getQuery(),false)
        val width = Resources.getSystem().displayMetrics.widthPixels
        it.maxWidth = width
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRepositorySearchBinding.inflate(inflater,container,false)
        mAdapter = RepositorySearchAdapter(context!!)

        mBinding.githubBasicInfoRecyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.githubBasicInfoRecyclerView.adapter = mAdapter

        mPresenter = RepositorySearchPresenter(RepositorySearchInteractor(RepositorySearchRepository()))

        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView).setOnQueryTextListener(this)
        (mBinding.searchToolbar.menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView).setOnSearchClickListener(onSearchClickListener)

        return mBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null){
            mStateView = savedInstanceState.getParcelable("viewState")
        }
    }


    override fun onResume() {
        super.onResume()
        mPresenter.subscribe(this,mStateView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("viewState",mPresenter.getState())
    }

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
}
