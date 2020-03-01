package my.app.githubapp.utils.sorter

import my.app.githubapp.domain.GitHubRepo

interface SorterInterface<T> {
    fun sortIterable(iterableToSort: Iterable<T>, sortType: Int): Iterable<GitHubRepo>
}
