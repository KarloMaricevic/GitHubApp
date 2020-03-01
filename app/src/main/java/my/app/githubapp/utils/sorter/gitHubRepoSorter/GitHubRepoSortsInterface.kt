package my.app.githubapp.utils.sorter.gitHubRepoSorter

import my.app.githubapp.domain.GitHubRepo

interface GitHubRepoSortsInterface {
    fun sortByRepoName(iterable: Iterable<GitHubRepo>): Iterable<GitHubRepo>
    fun sortByStar(iterable: Iterable<GitHubRepo>): Iterable<GitHubRepo>
    fun sortByDate(iterable: Iterable<GitHubRepo>): Iterable<GitHubRepo>
    fun sortByForked(iterable: Iterable<GitHubRepo>): Iterable<GitHubRepo>
}
