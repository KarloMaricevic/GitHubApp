package my.app.githubapp.cacher

import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class DataSource<Key : Any,ResourceType : Any> private constructor(maxEntry : Int, networkDataSource : NetworkDataSourceInterface<Key, ResourceType>?) {


    private val mMemoryDataSource = MemoryDataSource<Key,ResourceType>(maxEntry)
    private val mNetworkDataSource = networkDataSource
    private val mCompositeDisposable = CompositeDisposable()

    fun getFromMemory(key : Key) : Single<ResourceType> {
        if(mMemoryDataSource.doesContainsKey(key)){
            return Single.just(mMemoryDataSource.getData(key)).subscribeOn(AndroidSchedulers.mainThread())
        }
        else{
            if(mNetworkDataSource != null) {
                val publisher =
                    mNetworkDataSource.getData(key).toObservable().publish().autoConnect(2).subscribeOn(Schedulers.io())
                val cachingDisposable = publisher.observeOn(AndroidSchedulers.mainThread()).subscribe(
                    { mMemoryDataSource.cacheInMemory(key, it) },
                    {
                        Log.e("Network Error: ", it.message ?: "Caching disposable")
                    }
                )
                mCompositeDisposable.add(cachingDisposable)
                return publisher.firstOrError()
            }
            else
            {
                return Single.error(Exception("No network Data Source,try calling is cached first"))
            }
        }
    }

    fun dispose(){
        mCompositeDisposable.clear()
    }

    fun clearCache(){
        val clearCache = Maybe.fromAction<Boolean>({mMemoryDataSource.clearCache()}).subscribeOn(AndroidSchedulers.mainThread()).subscribe({},{},{})
        mCompositeDisposable.add(clearCache)
    }


    class Builder<Key : Any,ResourceType : Any> {
        private var mMaxEntry : Int  = 5
        private var mNetworkDataSourceInterface : NetworkDataSourceInterface<Key,ResourceType>? = null


        fun setMaxEntry(maxEntry : Int) : Builder<Key,ResourceType>{
            mMaxEntry = maxEntry
            return this
        }

        fun setNetworkDataSource(networkDataSourceInterface: NetworkDataSourceInterface<Key,ResourceType>) : Builder<Key,ResourceType>{
            mNetworkDataSourceInterface = networkDataSourceInterface
            return this
        }

        fun build() :DataSource<Key,ResourceType> {
            return DataSource(mMaxEntry,mNetworkDataSourceInterface)
        }
    }
}