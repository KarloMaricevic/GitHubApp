package my.app.githubapp.core

interface BasePresenter<in V : BaseView> {
    fun subscribe(view : V)
    fun unsubscribe()
}