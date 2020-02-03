package my.app.githubapp.ui.RepositoryDetailsView


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.githubapp.BaseApplication
import my.app.githubapp.adapter.RepositoryLanguagesAdapter
import my.app.githubapp.databinding.FragmentRepositoryDetailsScreenBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.domain.GitHubUser
import my.app.githubapp.domain.LanguagePercentile
import my.app.githubapp.mvp.contract.RepositoryScreenContract.RepositoryDetailsView
import my.app.githubapp.mvp.presenter.RepositoryDetailPresenter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class RepositoryDetailsFragment :   Fragment(), RepositoryDetailsView {

    @Inject
    lateinit var mPresenter : RepositoryDetailPresenter

    lateinit var mBinding : FragmentRepositoryDetailsScreenBinding

    private var mLanguagesAdapter = RepositoryLanguagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        val navigationArgs : RepositoryDetailsFragmentArgs by navArgs()

/*        (activity!!.application as BaseApplication)
            .getRepositoryDetailSubcomponent(navigationArgs.ownerLogin,navigationArgs.repoName)
            .inject(this)*/

        (activity!!.application as BaseApplication)
            .getRepositoryDetailSubcomponent("angular","angular")
            .inject(this)


        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRepositoryDetailsScreenBinding.inflate(inflater,container,false)
        mBinding.languagesRecyclerView.layoutManager  = LinearLayoutManager(context)
        mBinding.languagesRecyclerView.adapter = mLanguagesAdapter

        mBinding.showLanguagesButton.setOnClickListener {
            if(!it.isActivated) {
                it.isActivated = true
                mPresenter.displayRepoLanguages()
            }
            else{
                it.isActivated = false
                mPresenter.hideRepoLanguages()
            }
        }

        mBinding.toWebImageView.setOnClickListener {
            openBrowserWithLink(Uri.parse(mBinding.gitHubRepo.pageUrl))
        }

        return mBinding.root
    }

    override fun bindRepoDetails(gitHubRepo: GitHubRepo) {
        mBinding.gitHubRepo = gitHubRepo
        mBinding.executePendingBindings()

    }

    override fun bindUserInfo(owner: GitHubUser) {
        mBinding.owner = owner
        Glide.with(context!!)
            .load(owner.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(mBinding.ownerInfoLayout.ownerPictureImageButton)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe(this,null)
    }

    override fun onStop() {
        mPresenter.unsubscribe()
        super.onStop()
    }

    override fun showRepoLanguages(languagesPercentileList: List<LanguagePercentile>) {
        mLanguagesAdapter.setData(languagesPercentileList)
    }

    override fun hideRepoLanguages() {
        mLanguagesAdapter.setData(listOf())
    }

    fun openBrowserWithLink(uri : Uri){
        val browserIntent = Intent(Intent.ACTION_VIEW,uri)
        ContextCompat.startActivity(context!!,browserIntent,null)
    }
}
