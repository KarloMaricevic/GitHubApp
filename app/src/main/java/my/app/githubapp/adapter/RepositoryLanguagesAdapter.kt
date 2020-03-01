package my.app.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.app.githubapp.databinding.ItemLanguageBinding
import my.app.githubapp.domain.LanguagePercentage

class RepositoryLanguagesAdapter : RecyclerView.Adapter<ItemLanguageViewHolder>() {

    private var mData: List<LanguagePercentage> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemLanguageViewHolder {
        return ItemLanguageViewHolder(
            ItemLanguageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    override fun onBindViewHolder(holder: ItemLanguageViewHolder, position: Int) {
        val gitHubRepoLanguage = mData[position]
        holder.bind(gitHubRepoLanguage)
    }

    fun setData(newData: List<LanguagePercentage>) {
        mData = newData
        notifyDataSetChanged()
    }
}

class ItemLanguageViewHolder(private val mBinding: ItemLanguageBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(data: LanguagePercentage) {
        mBinding.language = data
        mBinding.executePendingBindings()
    }
}
