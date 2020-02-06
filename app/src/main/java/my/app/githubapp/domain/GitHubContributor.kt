package my.app.githubapp.domain


class GitHubContributor(userLogin : String,userId : Int,userThumbnailUrl: String,val numberOfCommits : Int) : BasicGitHubUser(userLogin,userId,userThumbnailUrl)