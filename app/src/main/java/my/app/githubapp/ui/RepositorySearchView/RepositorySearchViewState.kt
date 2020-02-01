package my.app.githubapp.ui.RepositorySearchView

import kotlinx.android.parcel.Parcelize
import my.app.githubapp.mvp.contract.RepositorySearchContract

const val SORT_BY_REPO_NAME = 1
const val SORT_BY_STAR = 2
const val SORT_BY_DATE = 3
const val SORT_BY_FORKED = 4

@Parcelize
data class RepositorySearchViewState(private val query : String,private val sortBy : Int = SORT_BY_REPO_NAME) : RepositorySearchContract.RepositorySearchViewStateInterface {
    override fun getQuery()  = query
    override fun getSortBy(): Int = sortBy

}


