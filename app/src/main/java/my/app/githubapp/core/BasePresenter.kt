package my.app.githubapp.core

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseView> {
    protected var mView: V? = null
    protected val mCompositeDisposable = CompositeDisposable()

    abstract fun subscribe(view: V)

    fun unsubscribe() {
        mView = null
        mCompositeDisposable.clear()
    }
}
