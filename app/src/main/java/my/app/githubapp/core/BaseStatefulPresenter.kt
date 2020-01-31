package my.app.githubapp.core

interface BaseStatefulPresenter<in V : BaseView,S> : BasePresenter<V> {
    fun subscribe(view : V,state : S?)
    fun getState() : S
}