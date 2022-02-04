package com.example.attendancetracker.databaseHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.attendancetracker.models.Attendance
import com.example.attendancetracker.models.Lecture

class DatabaseHelper constructor(context: Context) {
    private val database: Database

    init {
        database = getInstance(context)
    }

    companion object {
        private const val SETUP = "Setup"
        private const val PREF_NAME = "Prefs"
        private val instance: Database? = null
        fun getInstance(context: Context): Database {
            return instance ?: Database(context)
        }
    }

    fun insertLecture(lecture: Lecture) {
        val db = database.writableDatabase
        val values = ContentValues()
        values.put("Day", lecture.day)
        values.put("SubjectName", lecture.subjectName)
        values.put("SubjectCode", lecture.subjectCode)
        values.put("StartTime", lecture.startTime)
        values.put("EndTime", lecture.endTime)
        db.insert("Lecture", null, values)
    }

    fun getLectures(dayOfWeek: String):List<Lecture>{
        val db = database.readableDatabase
        val cursor = db.query("Lecture",null,"day = ?", arrayOf(dayOfWeek),null,null,null,null)
        val lectures= mutableListOf<Lecture>()
        with(cursor) {
            while (moveToNext()) {
                val id =  getInt(getColumnIndexOrThrow("Id"))
                val day = getString(getColumnIndexOrThrow("Day"))
                val subjectName = getString(getColumnIndexOrThrow("SubjectName"))
                val subjectCode = getString(getColumnIndexOrThrow("SubjectCode"))
                val startTime = getLong(getColumnIndexOrThrow("StartTime"))
                val endTime = getLong(getColumnIndexOrThrow("EndTime"))
                lectures.add(Lecture(id,day,subjectName,subjectCode,startTime,endTime))
            }
        }
        cursor.close()
        return lectures
    }

    fun getLecture(id: Int): Lecture?{
        val db = database.readableDatabase
        val cursor = db.query("Lecture",null,"id = ?", arrayOf(id.toString()),null,null,null,null)
        var lecture: Lecture? = null
        with(cursor) {
            while (moveToNext()) {
                val id =  getInt(getColumnIndexOrThrow("Id"))
                val day = getString(getColumnIndexOrThrow("Day"))
                val subjectName = getString(getColumnIndexOrThrow("SubjectName"))
                val subjectCode = getString(getColumnIndexOrThrow("SubjectCode"))
                val startTime = getLong(getColumnIndexOrThrow("StartTime"))
                val endTime = getLong(getColumnIndexOrThrow("EndTime"))
                lecture = Lecture(id,day,subjectName,subjectCode,startTime,endTime)
            }
        }
        cursor.close()
        return lecture
    }

    fun insertAttendance(attendance: Attendance) {
        val db = database.writableDatabase
        val values = ContentValues()
        values.put("LectureId", attendance.lecture.id)
        values.put("HasAttended", attendance.hasAttended)
        db.insert("Lecture", null, values)
    }

//    fun getAttendance(dayOfWeek: String):List<Attendance>{
//        val db = database.readableDatabase
//        val cursor = db.query("Lecture",null,"day = ?", arrayOf(dayOfWeek),null,null,null,null)
//        val lectures= mutableListOf<Lecture>()
//        with(cursor) {
//            while (moveToNext()) {
//                val id =  getInt(getColumnIndexOrThrow("Id"))
//                val day = getString(getColumnIndexOrThrow("Day"))
//                val subjectName = getString(getColumnIndexOrThrow("SubjectName"))
//                val subjectCode = getString(getColumnIndexOrThrow("SubjectCode"))
//                val startTime = getLong(getColumnIndexOrThrow("StartTime"))
//                val endTime = getLong(getColumnIndexOrThrow("EndTime"))
//                lectures.add(Lecture(id,day,subjectName,subjectCode,startTime,endTime))
//            }
//        }
//        cursor.close()
//        return lectures
//    }

}


class Database(context: Context) : SQLiteOpenHelper(context,"mydb",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE LECTURE(Id INTEGER PRIMARY KEY AUTOINCREMENT, Day TEXT, SubjectName TEXT, SubjectCode TEXT, StartTime INTEGER, EndTime INTEGER )")
        db?.execSQL("CREATE TABLE ATTENDANCE(Id INTEGER PRIMARY KEY AUTOINCREMENT, LectureId INTEGER, HasAttended INTEGER, FOREIGN KEY (LectureId) REFERENCES LECTURE(Id))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // pass
    }
}