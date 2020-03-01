package my.app.githubapp.core

abstract class BaseStatefulPresenter<V : BaseView, S : State> : BasePresenter<V>() {

    abstract fun subscribe(view: V, state: S?)
    override fun subscribe(view: V) = subscribe(view, null)
    abstract fun getState(): S
}
