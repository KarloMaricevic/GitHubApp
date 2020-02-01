package my.app.githubapp

import android.app.Application
import android.telephony.mbms.MbmsErrors
import my.app.githubapp.di.AppComponent
import my.app.githubapp.di.DaggerAppComponent
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubcomponent
import java.lang.NullPointerException

class BaseApplication : Application() {


    private lateinit var mAppComponent : AppComponent
    private var mRepositorySearchSubcomponent : RepositorySearchSubcomponent? = null


    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.create()
    }


    fun getRepositorySearchSubcomponent() : RepositorySearchSubcomponent{
        if(mRepositorySearchSubcomponent == null) {
            mRepositorySearchSubcomponent = mAppComponent.getRepositorySearchSubcomponentFactory().create()
        }
        return mRepositorySearchSubcomponent ?: throw NullPointerException()
    }

    fun releaseRepertorySearchSobcomponent() {
        mRepositorySearchSubcomponent = null
    }




}