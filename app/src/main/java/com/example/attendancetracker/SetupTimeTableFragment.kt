package com.example.attendancetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.attendancetracker.databaseHelper.DatabaseHelper
import com.example.attendancetracker.databinding.FragmentSetupTimeTableBinding
import com.example.attendancetracker.extensions.getTimeFromMillis
import com.example.attendancetracker.extensions.getTimeInMillis
import com.example.attendancetracker.models.Lecture
import com.example.attendancetracker.preference.PreferenceHelper
import com.google.android.material.button.MaterialButton


class SetupTimeTableFragment : Fragment() {

    private var _binding:FragmentSetupTimeTableBinding? = null
    private val binding get() = _binding!!
    lateinit var cardList: LinearLayout
    var lectureList = mutableListOf<Lecture>()
    val daysOfWeek = listOf<String>("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday")
    val daysIds = listOf<Int>(R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday,R.id.sunday)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupTimeTableBinding.inflate(inflater,container,false)
        val  view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.doneBtn.setOnClickListener {
            for (lecture in lectureList){
                DatabaseHelper(context!!).insertLecture(lecture)
            }
            PreferenceHelper.getInstance(context!!).isSetup = true
            Toast.makeText(context,"Setup Successfull",Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        cardList = binding.cardList
        for ( day in daysOfWeek) {
            var card: View = layoutInflater.inflate(R.layout.weekday_lecture_card, null, false)
            card.findViewById<TextView>(R.id.day).text = day
            card.findViewById<MaterialButton>(R.id.addLectureBtn).setOnClickListener {
                val action =
                    SetupTimeTableFragmentDirections.actionSetupTimeTableFragmentToAddLectureFragment(day = day)
                findNavController().navigate(action)
            }
            cardList.addView(card)
            card.id = daysIds[daysOfWeek.indexOf(day)]
        }
        for (lecture in lectureList){
            var id = daysIds[daysOfWeek.indexOf(lecture.day)]
            var row: View =
                layoutInflater.inflate(R.layout.lecture_detail_row, null, false)
            row.findViewById<TextView>(R.id.lectureName).text = lecture.subjectName
            row.findViewById<TextView>(R.id.lectureTime).text = "${lecture.startTime.getTimeFromMillis()} to ${lecture.endTime.getTimeFromMillis()}"
            cardList.findViewById<LinearLayout>(id).findViewById<LinearLayout>(R.id.lectureList).addView(row)
        }

        var day: String = ""
        var subjectName: String = ""
        var subjectCode: String = ""
        var startTime: String = ""
        var endTime: String = ""
        parentFragmentManager.setFragmentResultListener(
            "day", this
        ) { requestKey, result ->
            day = result.get(requestKey).toString()
        }
        parentFragmentManager.setFragmentResultListener(
            "subjectName", this
        ) { requestKey, result ->
            subjectName = result.get(requestKey).toString()
        }
        parentFragmentManager.setFragmentResultListener(
            "subjectCode", this
        ) { requestKey, result ->
            subjectCode = result.get(requestKey).toString()
        }
        parentFragmentManager.setFragmentResultListener(
            "startTime", this
        ) { requestKey, result ->
            startTime = result.get(requestKey).toString()
        }
        parentFragmentManager.setFragmentResultListener(
            "endTime", this
        ) { requestKey, result ->
            endTime = result.get(requestKey).toString()
        }
        parentFragmentManager.addOnBackStackChangedListener {
            Log.i("Msg", "Back stack changed")
            if (day.isNotEmpty() || subjectName.isNotEmpty() || subjectCode.isNotEmpty() || startTime.isNotEmpty() || endTime.isNotEmpty()) {
                // inflate lecture row
                var row: View =
                    layoutInflater.inflate(R.layout.lecture_detail_row, null, false)
                row.findViewById<TextView>(R.id.lectureName).text = subjectName
                row.findViewById<TextView>(R.id.lectureTime).text = "$startTime to $endTime"
                view.findViewById<LinearLayout>(daysIds[daysOfWeek.indexOf(day)]).findViewById<LinearLayout>(R.id.lectureList).addView(row)
                lectureList.add(Lecture(null,day, subjectName, subjectCode, startTime.getTimeInMillis(), endTime.getTimeInMillis()))
            }
            parentFragmentManager.clearFragmentResultListener("day")
            parentFragmentManager.clearFragmentResultListener("subjectName")
            parentFragmentManager.clearFragmentResultListener("subjectCode")
            parentFragmentManager.clearFragmentResultListener("startTime")
            parentFragmentManager.clearFragmentResultListener("endTime")
            subjectName = ""
            subjectCode = ""
            startTime = ""
            endTime = ""
            day = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}