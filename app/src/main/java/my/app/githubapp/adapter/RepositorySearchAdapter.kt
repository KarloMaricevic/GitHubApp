package my.app.githubapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.githubapp.databinding.ItemBasicRepositoryInfoBinding
import my.app.githubapp.domain.GitHubRepo
import my.app.githubapp.ui.RepositorySearchView.RepositorySearchViewAdapterCallback

class RepositorySearchAdapter(mContext : Context,val mCallback : RepositorySearchViewAdapterCallback) : RecyclerView.Adapter<BasicRepositoryInfoViewHolder>(),RepositorySearchAdapterInterface {


    private var mData : List<GitHubRepo> = listOf()
    private val mGlide = Glide.with(mContext)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicRepositoryInfoViewHolder {
        return BasicRepositoryInfoViewHolder(ItemBasicRepositoryInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false),this)
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


    fun setData(newData : List<GitHubRepo>){
        mData = newData
        notifyDataSetChanged()
    }

    override fun clickedOnUser(userId: Int) {
        mCallback.navigateToUserDetailView(userId)
    }

    override fun clickedOnRepo(repoId: Int) {
        mCallback.navigateToRepoDetailView(repoId)
    }

}


class BasicRepositoryInfoViewHolder(private val mBinding : ItemBasicRepositoryInfoBinding,private val mRepositorySearchAdapterInterface: RepositorySearchAdapterInterface) : RecyclerView.ViewHolder(mBinding.root)
{


    fun bind(item : GitHubRepo){
        mBinding.gitHubRepo = item
        mBinding.repoOnClick = View.OnClickListener { mRepositorySearchAdapterInterface.clickedOnRepo(item.id) }
        mBinding.userOnClick = View.OnClickListener { mRepositorySearchAdapterInterface.clickedOnUser(item.owner.id) }
        mBinding.executePendingBindings()
    }


    fun getImageView() : ImageView
    {
        return mBinding.ownerPictureImageButton
    }


}