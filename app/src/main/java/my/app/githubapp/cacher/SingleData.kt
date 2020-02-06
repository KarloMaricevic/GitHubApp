package my.app.githubapp.cacher

internal class SingleData<Key : Any,ResourceType : Any> (
    val key : Key,
    val resource : ResourceType
)