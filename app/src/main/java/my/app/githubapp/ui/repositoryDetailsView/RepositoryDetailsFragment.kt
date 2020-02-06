package my.app.githubapp.ui.repositoryDetailsView


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import my.app.githubapp.BaseApplication
import my.app.githubapp.R
import my.app.githubapp.adapter.RepositoryContributorsAdapter
import my.app.githubapp.adapter.RepositoryLanguagesAdapter
import my.app.githubapp.databinding.FragmentRepositoryDetailsScreenBinding
import my.app.githubapp.domain.GitHubContributor
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.contract.RepositoryDetailsContract
import my.app.githubapp.mvp.contract.RepositoryDetailsContract.RepositoryDetailsView
import my.app.githubapp.mvp.presenter.RepositoryDetailPresenter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RepositoryDetailsFragment :   Fragment(), RepositoryDetailsView {

    @Inject
    lateinit var mPresenter : RepositoryDetailPresenter

    private lateinit var mBinding : FragmentRepositoryDetailsScreenBinding

    private var mLanguagesAdapter = RepositoryLanguagesAdapter()

    private lateinit var mContributorsAdapter: RepositoryContributorsAdapter

    private var mViewState : RepositoryDetailsContract.RepositoryDetailsViewStateInterface? = null

    private lateinit var mSnackbar : Snackbar

    private lateinit var mBackCallback : OnBackPressedCallback

    private val mShowLanguagesOnClick = View.OnClickListener {
        if(!it.isActivated) {
            loadRepositoriesLanguages()
        }
        else{
            hideRepoLanguages()
        }
    }

    private val mShowConstructorsOnClick = View.OnClickListener{
        if (!it.isActivated) {
            loadRepositoriesContributors()
        } else {
            hideContributors()
        }
    }

    private val mGoToWebPictureClicked = View.OnClickListener {
        openBrowserWithLink(Uri.parse(mBinding.gitHubRepo?.pageUrl))
    }


    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        val navigationArgs : RepositoryDetailsFragmentArgs by navArgs()

        (activity!!.application as BaseApplication)
            .getRepositoryDetailSubcomponent()
            .inject(this)

        mPresenter.setLoginAndRepoName(navigationArgs.ownerLogin,navigationArgs.repoName)

        super.onCreate(savedInstanceState)

         mBackCallback = requireActivity().onBackPressedDispatcher.addCallback(this){ navigateBack() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRepositoryDetailsScreenBinding.inflate(inflater,container,false)
        mContributorsAdapter = RepositoryContributorsAdapter(context!!)

        mBinding.languagesRecyclerView.adapter = mLanguagesAdapter
        //mBinding.contributorsRecyclerView.layoutManager = GridLayoutManager(context,3)
        mBinding.contributorsRecyclerView.adapter = mContributorsAdapter

        mBinding.showLanguagesButton.setOnClickListener(mShowLanguagesOnClick)

        mBinding.showContributorsButton.setOnClickListener(mShowConstructorsOnClick)

        mBinding.toWebImageView.setOnClickListener (mGoToWebPictureClicked)

        requireActivity().onBackPressedDispatcher.addCallback(this,mBackCallback)

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this,mViewState)
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(getString(R.string.viewState),mPresenter.getState())
    }

    //endregion

    //region presenter-view

    override fun bindRepoDetails(gitHubRepo: GitHubRepo) {
        mBinding.gitHubRepo = gitHubRepo
        mBinding.executePendingBindings()

    }

    override fun bindUserInfo(owner: GitHubUser) {
        mBinding.owner = owner
        Glide.with(context!!)
            .load(owner.thumbnailUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(mBinding.ownerInfoLayout.ownerPictureImageButton)
    }

    override fun showRepoLanguages(languagesPercentileList: List<LanguagePercentile>) {
        mBinding.showLanguagesButton.isClickable = true
        mLanguagesAdapter.setData(languagesPercentileList)
    }

    override fun hideRepoLanguages() {
        mLanguagesAdapter.setData(listOf())
        mBinding.languagesRecyclerView.isClickable = true
        mBinding.languagesRecyclerView.isActivated = false
        mPresenter.hideRepoLanguages()
    }

    override fun showContributors(contributorsList: List<GitHubContributor>) {
        mBinding.showContributorsButton.isClickable = true
        mContributorsAdapter.setData(contributorsList)

    }

    override fun hideContributors() {
        mBinding.showContributorsButton.isClickable = true
        mBinding.showContributorsButton.isActivated = false
        mPresenter.hideContributors()
    }

    override fun loadingContributorsError() {
        mBinding.showContributorsButton.isClickable = true
        mBinding.showContributorsButton.isActivated = false
        mSnackbar = Snackbar
            .make(mBinding.root, R.string.error_loading_contributors_snackbar_text,Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again) {
                mPresenter.presentContributors()
            }
    }

    override fun loadingLanguagesError() {
        mBinding.showLanguagesButton.isClickable = true
        mBinding.showLanguagesButton.isActivated = false
        mSnackbar =  Snackbar.make(
            mBinding.root,R.string.error_loading_languages_snackbar_text,Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again){mPresenter.presentRepoLanguages()}
    }

    override fun fatalError() {
        navigateBack()
    }

    //endregion presenter_view

    private fun loadRepositoriesLanguages(){
        mBinding.showLanguagesButton.isClickable = false
        mBinding.showLanguagesButton.isActivated = true
        mPresenter.presentRepoLanguages()
    }

    private fun loadRepositoriesContributors(){
        mBinding.showContributorsButton.isClickable = false
        mBinding.showContributorsButton.isActivated = true
        mPresenter.presentContributors()
    }

    private fun openBrowserWithLink(uri : Uri){
        val browserIntent = Intent(Intent.ACTION_VIEW,uri)
        ContextCompat.startActivity(context!!,browserIntent,null)
    }

    private fun navigateBack(){
        (activity!!.application as BaseApplication)
            .releaseRepositoryDetailSobcomponent()
        findNavController().popBackStack()
    }

}
