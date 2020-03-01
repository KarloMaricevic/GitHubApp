package my.app.githubapp.utils.schedulers

import io.reactivex.Scheduler

interface SchedulersProviderInterface {
    fun getMainThread(): Scheduler
    fun getNetworkThread(): Scheduler
}
