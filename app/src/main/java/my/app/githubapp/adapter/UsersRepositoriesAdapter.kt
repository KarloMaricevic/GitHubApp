package my.app.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.app.githubapp.databinding.ItemUsersRepositoryBinding
import my.app.githubapp.domain.GitHubRepo

class UsersRepositoriesAdapter : RecyclerView.Adapter<UserRepositoryViewHolder>() {

    private var mGitHubRepoList = listOf<GitHubRepo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepositoryViewHolder {
        return UserRepositoryViewHolder(
            ItemUsersRepositoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount() = mGitHubRepoList.count()

    override fun onBindViewHolder(holder: UserRepositoryViewHolder, position: Int) {
        val gitHubRepo = mGitHubRepoList[position]
        holder.bind(gitHubRepo)
    }

    fun setData(gitHubRepoList: List<GitHubRepo>) {
        mGitHubRepoList = gitHubRepoList
        notifyDataSetChanged()
    }

}


class UserRepositoryViewHolder(private val mBinding: ItemUsersRepositoryBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(gitHubRepo: GitHubRepo) {
        mBinding.gitHubRepo = gitHubRepo
        mBinding.executePendingBindings()
    }

}