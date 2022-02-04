package com.example.attendancetracker


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetracker.adapter.AttendanceAdapter
import com.example.attendancetracker.databaseHelper.DatabaseHelper
import com.example.attendancetracker.databinding.ActivityMainBinding
import com.example.attendancetracker.models.Attendance
import com.example.attendancetracker.models.Lecture
import com.example.attendancetracker.preference.PreferenceHelper
import java.util.*

class MainActivity : AppCompatActivity() {
    private val daysOfWeek = listOf<String>("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")

    private  var lectures:List<Lecture> = listOf()
    private  var attendanceList:MutableList<Attendance> = mutableListOf()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val showSetupScreen = PreferenceHelper.getInstance(this).isSetup
        if (!showSetupScreen) {
            val intent = Intent(this,WelcomeScreen::class.java)
            startActivity(intent)
            finish()
        } else {
            // show home screen
            val db = DatabaseHelper(this)
            Log.i("msg",db.getLecture(1).toString())
            val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            Log.i("msg",daysOfWeek[dayOfWeek-1])
            lectures = db.getLectures(daysOfWeek[dayOfWeek-1])
            if (lectures.isEmpty()){
                binding.noLectures.text = getString(R.string.no_lectures_today)
            }
            Log.i("msg",lectures.toString())
            for (lecture in lectures){
                attendanceList.add(Attendance(null,lecture,null))
            }

            val recyclerView = binding.recyclerView
            recyclerView.adapter = AttendanceAdapter(this,attendanceList)
            recyclerView.setHasFixedSize(true)

        }
    }
}