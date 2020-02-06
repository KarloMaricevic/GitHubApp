package my.app.githubapp.cacher

import io.reactivex.Maybe
import io.reactivex.Single
import my.app.githubapp.domain.GitHubRepo

interface NetworkDataSourceInterface<Key,ResourceType> {
    fun getData(key: Key) : Single<ResourceType>
}