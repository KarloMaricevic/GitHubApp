package my.app.githubapp.cacher

import io.reactivex.Maybe
import io.reactivex.Single

interface NetworkDataSourceInterface<Key,ResourceType> {
    fun getData(key: Key) : Single<ResourceType>
}