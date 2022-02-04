package com.example.attendancetracker.models

data class Lecture(
var id:Int?,
    val day:String, val subjectName:String, val subjectCode:String, val startTime:Long, val endTime:Long
)
