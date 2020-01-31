package my.app.githubapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.githubapp.databinding.ItemBasicRepositoryInfoBinding
import my.app.githubapp.domain.GitHubRepo

class RepositorySearchAdapter(mContext : Context) : RecyclerView.Adapter<BasicRepositoryInfoViewHolder>() {


    private var mData : List<GitHubRepo> = listOf()
    private val mGlide = Glide.with(mContext)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicRepositoryInfoViewHolder {
        return BasicRepositoryInfoViewHolder(ItemBasicRepositoryInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
}


class BasicRepositoryInfoViewHolder(private val mBinding : ItemBasicRepositoryInfoBinding) : RecyclerView.ViewHolder(mBinding.root)
{

    fun bind(item : GitHubRepo){
        mBinding.gitHubRepo = item
        mBinding.executePendingBindings()
    }


    fun getImageView() : ImageView
    {
        return mBinding.ownerPictureImageButton
    }


}