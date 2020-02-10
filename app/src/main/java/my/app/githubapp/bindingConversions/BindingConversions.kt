package my.app.githubapp.bindingConversions

import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

object BindingConversions {

        @BindingConversion
        @JvmStatic
        fun convertDateToString(date: Date?) : String{
                return if(date == null) {
                        "Not specified"
                } else{
                        val format = SimpleDateFormat("dd/MM/yyyy")
                        format.format(date)
                }
        }

}