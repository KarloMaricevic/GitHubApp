package my.app.githubapp.bindingConversions

import androidx.databinding.BindingConversion
import java.text.DateFormat
import java.util.*

object BindingConversions {

    @BindingConversion
    @JvmStatic
    fun convertDateToString(date: Date?): String {
        return if (date == null) {
            "Not specified"
        } else {
            return DateFormat.getDateInstance().format(date)
        }
    }

}