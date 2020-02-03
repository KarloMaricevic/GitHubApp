package my.app.githubapp

import android.app.Application
import android.telephony.mbms.MbmsErrors
import my.app.githubapp.di.AppComponent
import my.app.githubapp.di.DaggerAppComponent
import my.app.githubapp.di.RepositoryDetailsSubcomponent.RepositoryDetailSubcomponent
import my.app.githubapp.di.RepositorySearchSubcomponent.RepositorySearchSubcomponent
import java.lang.NullPointerException

class BaseApplication : Application() {


    private lateinit var mAppComponent : AppComponent
    private var mRepositorySearchSubcomponent : RepositorySearchSubcomponent? = null
    private var mRepositoryDetailSubcomponent : RepositoryDetailSubcomponent? = null


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

    fun releaseRepositorySearchSobcomponent() {
        mRepositorySearchSubcomponent = null
    }

    fun getRepositoryDetailSubcomponent(ownerLogin : String,repoName : String) : RepositoryDetailSubcomponent{
        if(mRepositoryDetailSubcomponent == null) {
            mRepositoryDetailSubcomponent =
                mAppComponent.getRepositoryDetailsSubcmponentFactory().create(ownerLogin,repoName)
        }
        return mRepositoryDetailSubcomponent ?: throw NullPointerException()
    }

    fun releaseRepositoryDetailSobcomponent(){
        mRepositoryDetailSubcomponent = null
    }





}