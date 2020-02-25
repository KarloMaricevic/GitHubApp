package my.app.githubapp.ui.userDetailsView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import my.app.githubapp.BaseApplication
import my.app.githubapp.R
import my.app.githubapp.adapter.UsersRepositoriesAdapter
import my.app.githubapp.databinding.FragmentUserDetailsBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.mvp.contract.UserDetailsContract.*
import javax.inject.Inject


class UserDetailsFragment : Fragment(), UserDetailsView {

    @Inject
    lateinit var mPresenter: UserDetailsPresenterAbstraction

    private lateinit var mBinding: FragmentUserDetailsBinding

    private var mViewState: UserDetailsViewStateInterface? = null

    private val mAdapter = UsersRepositoriesAdapter()

    private val contributorsButtonOnClick = View.OnClickListener {
        if (!it.isActivated) {
            loadUsersRepositories()
        } else {
            mPresenter.hideUserRepositories()
        }
    }

    private val toWebImageClick = View.OnClickListener {
        openBrowserWithLink(Uri.parse(mBinding.gitHubUser?.htmlUrl))
    }

    private lateinit var mSnackbar: Snackbar


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getUserDetailSubcomponent()
            .inject(this)

        val navigationArgs: UserDetailsFragmentArgs by navArgs()
        mPresenter.setUserLogin(navigationArgs.userLogin)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        mBinding.showContributorsButton.setOnClickListener(contributorsButtonOnClick)
        mBinding.goToWebImageView.setOnClickListener(toWebImageClick)

        mBinding.usersRepositoriesRecyclerView.adapter = mAdapter

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            mViewState = savedInstanceState.getParcelable(getString(R.string.viewState))
        }
    }


    override fun onStart() {
        mPresenter.subscribe(this, mViewState)
        super.onStart()
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(getString(R.string.viewState), mPresenter.getState())
    }

    //region viewImpl

    override fun showUserInfo(gitHubUser: GitHubUser) {
        mBinding.gitHubUser = gitHubUser
        Glide
            .with(this)
            .load(gitHubUser.thumbnailUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(mBinding.userThumbnailImageView)
        mBinding.executePendingBindings()
    }

    override fun showUsersRepositories(gitHubRepoList: List<GitHubRepo>) {
        mAdapter.setData(gitHubRepoList)
        mBinding.showContributorsButton.isClickable = true
        mBinding.showContributorsButton.isActivated = true
    }

    override fun hideUsersRepositories() {
        mAdapter.setData(listOf())
        mBinding.showContributorsButton.isClickable = true
        mBinding.showContributorsButton.isActivated = false
    }

    override fun showErrorLoadingRepositories() {
        mSnackbar = Snackbar.make(
            mBinding.root,
            R.string.error_loading_user_repositories,
            Snackbar.LENGTH_LONG
        )
            .setAction(R.string.try_again) {
                loadUsersRepositories()
                mSnackbar.dismiss()
            }
        mSnackbar.show()
    }


    override fun fatalError() {
        navigateBack()
    }

    //endregion

    private fun loadUsersRepositories() {
        mBinding.showContributorsButton.isClickable = false
        mBinding.showContributorsButton.isActivated = true
        mPresenter.presentUserRepositories()
    }

    private fun openBrowserWithLink(uri: Uri) {
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        ContextCompat.startActivity(context!!, browserIntent, null)
    }

    private fun navigateBack() {
        (activity!!.application as BaseApplication)
            .releaseUserDetailSubcomponent()
        findNavController().popBackStack()
    }
}
