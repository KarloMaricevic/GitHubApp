package my.app.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.githubapp.databinding.ItemContributorBinding
import my.app.githubapp.domain.GitHubContributor

class RepositoryContributorsAdapter(fragment: Fragment) :
    RecyclerView.Adapter<ContributorViewHolder>() {

    var mContributorList = listOf<GitHubContributor>()
    val mGlide = Glide.with(fragment)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(
            ItemContributorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mContributorList.count()
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val gitHubContributor = mContributorList[position]
        holder.bind(gitHubContributor)
        mGlide
            .load(gitHubContributor.thumbnailUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.getImageView())
    }

    fun setData(contributorsList: List<GitHubContributor>) {
        mContributorList = contributorsList
        notifyDataSetChanged()
    }
}

class ContributorViewHolder(private val mBinding: ItemContributorBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(item: GitHubContributor) {
        mBinding.contributor = item
        mBinding.executePendingBindings()
    }

    fun getImageView(): ImageView {
        return mBinding.contributorImageView
    }
}