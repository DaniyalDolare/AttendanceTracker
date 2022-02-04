package com.example.attendancetracker.extensions

import java.util.*

/// Converts string time of format `HH : MM AM/PM` to milliseconds in Long
fun String.getTimeInMillis():Long {
    val c = Calendar.getInstance()
    val hour = this.split(" ")[0]
    val minute = this.split(" ")[2]
    c.set(Calendar.HOUR_OF_DAY,hour.toInt())
    c.set(Calendar.MINUTE,minute.toInt())
    return c.timeInMillis
}

fun Long.getTimeFromMillis():String{
    val c = Calendar.getInstance()
    c.timeInMillis = this

    var hour = c.get(Calendar.HOUR_OF_DAY)
    val dayTime:String
    val minute = c.get(Calendar.MINUTE)
    if (hour<12){
        dayTime = "AM"
    } else if (hour == 12){
        dayTime = "PM"
    } else {
        hour = hour - 12
        dayTime = "PM"
    }
    val selectedHourStr = hour.toString().padStart(2,'0')
    val selectedMinuteStr = minute.toString().padStart(2,'0')

    return "$selectedHourStr : $selectedMinuteStr $dayTime"
}