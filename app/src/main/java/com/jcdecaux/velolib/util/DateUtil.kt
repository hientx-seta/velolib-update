package com.jcdecaux.velolib.util

/**
 * Created by txhien on 11/16/2017.
 */

import com.jcdecaux.velolib.config.Constants
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {

        fun formatDate(originalDate: String): String {

            try {
                val sdf = SimpleDateFormat(Constants.DATE_PATTERN, Locale.ENGLISH)
                val timeZone = TimeZone.getTimeZone(Constants.IST_TIME_ZONE)
                sdf.timeZone = timeZone
                val date = sdf.parse(originalDate)
                val calendar = Calendar.getInstance()
                calendar.time = date
                val month = calendar.getDisplayName(
                        Calendar.MONTH,
                        Calendar.SHORT,
                        Locale.getDefault())
                val dayOFMonth = calendar.get(Calendar.DAY_OF_MONTH)
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val minutes = calendar.get(Calendar.MINUTE)

                return StringBuilder()
                        .append(dayOFMonth.toString())
                        .append(" ")
                        .append(month)
                        .append(" ")
                        .append(String.format(Locale.getDefault(), Constants.LEADING_ZERO_TEMPLATE, hours))
                        .append(":")
                        .append(String.format(Locale.getDefault(), Constants.LEADING_ZERO_TEMPLATE, minutes))
                        .toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return originalDate
        }
    }

}