package my.app.githubapp.bindingConversions

import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*

object BindingConversions {

        @BindingConversion
        @JvmStatic
        fun convertDateToString(date: Date?) : String{
                if(date == null) {
                        return "Not specified"
                }
                else{
                        val format = SimpleDateFormat("dd/MM/yyyy")
                        return format.format(date)
                }
        }

}