package my.app.githubapp.domain


class GitHubContributor constructor(userLogin : String,userId : Int,userThumbnailUrl: String, val numberOfCommits : Int) : BasicGitHubUser(userLogin,userId,userThumbnailUrl){

    constructor(author : BasicGitHubUser,numberOfCommits: Int) : this(author.login,author.id,author.thumbnailUrl,numberOfCommits)
}