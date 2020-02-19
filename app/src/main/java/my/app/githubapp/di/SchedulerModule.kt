package my.app.githubapp.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
interface SchedulerModule {

    companion object{
        @Provides
        @Singleton
        @Named("MainThread")
        fun providesMainThread() : Scheduler {
            return AndroidSchedulers.mainThread()
        }

    }
}