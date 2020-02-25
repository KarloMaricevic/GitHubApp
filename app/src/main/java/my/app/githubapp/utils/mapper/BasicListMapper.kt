package my.app.githubapp.utils.mapper

abstract class BasicListMapper<From, To> : MapperInterface<From, To> {

    fun convertList(objectToConvert: List<From>): List<To> {
        val convertedList = arrayListOf<To>()

        for (item in objectToConvert) {
            convertedList.add(convert(item))
        }
        return convertedList
    }
}