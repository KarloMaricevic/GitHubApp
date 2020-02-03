package my.app.githubapp.ui.RepositoryDetailsView

import kotlinx.android.parcel.Parcelize
import my.app.githubapp.mvp.contract.RepositoryScreenContract


@Parcelize
data class RepositoryDetailsViewState(private val isRepoLanguagesShown : Boolean,private val isContributorsShown : Boolean)
    : RepositoryScreenContract.RepositoryDetailsViewStateInterface {
    override fun isRepoLanguagesVisible(): Boolean = isRepoLanguagesShown
    override fun isContributorsShown(): Boolean = isContributorsShown
}
