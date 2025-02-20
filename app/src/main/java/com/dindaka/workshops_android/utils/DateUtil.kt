package com.dindaka.workshops_android.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_UTC = "UTC"

    fun getCurrentDateUtc(): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(DATE_FORMAT)
        }
        return sdf.format(Date())
    }

    fun convertUTCDateToLocal(dateString: String): String {
        val utcFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone(DATE_UTC)
        }
        val localFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        val date = utcFormat.parse(dateString)
        return localFormat.format(date)
    }
}