package my.app.githubapp.ui.userDetailsView

import kotlinx.android.parcel.Parcelize
import my.app.githubapp.mvp.contract.UserDetailsContract.UserDetailsViewStateInterface

@Parcelize
data class UserDetailsViewState(val repositoryButtonActivated: Boolean) :
    UserDetailsViewStateInterface {
    override fun isUserRepositoriesShown() = repositoryButtonActivated
}
