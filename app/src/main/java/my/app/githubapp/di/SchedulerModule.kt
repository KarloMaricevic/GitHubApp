package my.app.githubapp.di

import dagger.Module
import dagger.Provides
import my.app.githubapp.utils.schedulers.SchedulersProvider
import my.app.githubapp.utils.schedulers.SchedulersProviderInterface
import javax.inject.Singleton

@Module
interface SchedulerModule {

    companion object{
        @Provides
        @Singleton
        fun providesSchedulers() : SchedulersProviderInterface = SchedulersProvider

    }
}