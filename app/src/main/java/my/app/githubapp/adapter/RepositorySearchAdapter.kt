package my.app.githubapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.githubapp.databinding.ItemBasicRepositoryInfoBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.ui.repositorySearchView.RepositorySearchViewAdapterCallback

class RepositorySearchAdapter(
    fragment: Fragment,
    private val mCallback: RepositorySearchViewAdapterCallback
) : RecyclerView.Adapter<BasicRepositoryInfoViewHolder>(), RepositorySearchAdapterInterface {

    private var mData: List<GitHubRepo> = listOf()
    private var mGlide = Glide.with(fragment)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicRepositoryInfoViewHolder {
        return BasicRepositoryInfoViewHolder(
            ItemBasicRepositoryInfoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), this
        )
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    override fun onBindViewHolder(holder: BasicRepositoryInfoViewHolder, position: Int) {
        val gitHubRepo = mData[position]
        holder.bind(gitHubRepo)
        mGlide
            .load(gitHubRepo.owner.thumbnailUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.getImageView())
    }

    fun setData(newData: List<GitHubRepo>) {
        mData = newData
        notifyDataSetChanged()
    }

    fun changeContext(context: Context) {
        mGlide = Glide.with(context)
    }

    override fun clickedOnOwner(ownerLogin: String) {
        mCallback.navigateToUserDetailView(ownerLogin)
    }

    override fun clickedOnRepo(ownerLogin: String, repoName: String) {
        mCallback.navigateToRepoDetailView(ownerLogin, repoName)
    }
}

class BasicRepositoryInfoViewHolder(
    private val mBinding: ItemBasicRepositoryInfoBinding,
    private val mRepositorySearchAdapterInterface: RepositorySearchAdapterInterface
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: GitHubRepo) {
        mBinding.gitHubRepo = item
        mBinding.repoOnClick = View.OnClickListener {
            mRepositorySearchAdapterInterface.clickedOnRepo(
                item.owner.login,
                item.name
            )
        }
        mBinding.userOnClick =
            View.OnClickListener { mRepositorySearchAdapterInterface.clickedOnOwner(item.owner.login) }
        mBinding.executePendingBindings()
    }

    fun getImageView(): ImageView {
        return mBinding.ownerPictureImageButton
    }
}
