package my.app.githubapp.utils.mapper

interface MapperInterface<From, To> {
    fun convert(objectToConvert: From): To
}