package my.app.githubapp.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulersProvider : SchedulersProviderInterface {
    override fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()

    override fun getNetworkThread(): Scheduler = Schedulers.io()
}
