package my.app.githubapp.ui.repositoryDetailsView

import kotlinx.android.parcel.Parcelize
import my.app.githubapp.mvp.contract.RepositoryDetailsContract

@Parcelize
data class RepositoryDetailsViewState(
    private val isRepoLanguagesShown: Boolean,
    private val isContributorsShown: Boolean
) : RepositoryDetailsContract.RepositoryDetailsViewStateInterface {
    override fun isRepoLanguagesVisible(): Boolean = isRepoLanguagesShown
    override fun isContributorsShown(): Boolean = isContributorsShown
}
