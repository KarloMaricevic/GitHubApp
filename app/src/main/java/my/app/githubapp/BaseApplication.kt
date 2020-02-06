package my.app.githubapp

import android.app.Application
import my.app.githubapp.di.AppComponent
import my.app.githubapp.di.DaggerAppComponent
import my.app.githubapp.di.repositoryDetailsSubcomponent.RepositoryDetailSubcomponent
import my.app.githubapp.di.repositorySearchSubcomponent.RepositorySearchSubcomponent
import my.app.githubapp.di.userDetailsSubcompnent.UserDetailsSubcomponent
import java.lang.NullPointerException

class BaseApplication : Application() {


    private lateinit var mAppComponent : AppComponent
    private var mRepositorySearchSubcomponent : RepositorySearchSubcomponent? = null
    private var mRepositoryDetailSubcomponent : RepositoryDetailSubcomponent? = null
    private var mUserDetailSubcomponent : UserDetailsSubcomponent? = null


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

    fun getRepositoryDetailSubcomponent() : RepositoryDetailSubcomponent{
        if(mRepositoryDetailSubcomponent == null) {
            mRepositoryDetailSubcomponent =
                mAppComponent.getRepositoryDetailsSubcmponentFactory().create()
        }
        return mRepositoryDetailSubcomponent ?: throw NullPointerException()
    }

    fun releaseRepositoryDetailSobcomponent(){
        mRepositoryDetailSubcomponent = null
    }

    fun getUserDetailSubcomponent() : UserDetailsSubcomponent{
        if(mUserDetailSubcomponent == null){
            mUserDetailSubcomponent = mAppComponent.getUserDetailsSubcomponentFactory().create()
        }
        return mUserDetailSubcomponent ?: throw NullPointerException()
    }

    fun releaseUserDetailSubcomponent(){
        mUserDetailSubcomponent = null
    }





}