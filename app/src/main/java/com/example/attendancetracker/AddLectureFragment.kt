package com.example.attendancetracker

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.attendancetracker.databinding.FragmentAddLectureBinding
import java.util.*


class AddLectureFragment : Fragment() {

    private var _binding: FragmentAddLectureBinding? = null
    private val binding get() = _binding!!
    private lateinit var day:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getString("day").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddLectureBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val startTimeField = view.findViewById<EditText>(R.id.startTime)
        val endTimeField = view.findViewById<EditText>(R.id.endTime)

        startTimeField.setOnClickListener {
            selectTime(startTimeField)
        }

        endTimeField.setOnClickListener {
            selectTime(endTimeField)
        }

        view.findViewById<Button>(R.id.addLectureBtn).setOnClickListener {
            val subjectName = view.findViewById<EditText>(R.id.subjectName).text
            val subjectCode = view.findViewById<EditText>(R.id.subjectCode).text
            val startTime = startTimeField.text
            val endTime = endTimeField.text
            if (subjectName.isEmpty() || subjectCode.isEmpty() || startTime.isEmpty() ||endTime.isEmpty()){
              Toast.makeText(context,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }else{
                parentFragmentManager.setFragmentResult("day",bundleOf("day" to day))
                parentFragmentManager.setFragmentResult("subjectName",bundleOf("subjectName" to subjectName))
                parentFragmentManager.setFragmentResult("subjectCode",bundleOf("subjectCode" to subjectCode))
                parentFragmentManager.setFragmentResult("startTime",bundleOf("startTime" to startTime))
                parentFragmentManager.setFragmentResult("endTime",bundleOf("endTime" to endTime))
                findNavController().previousBackStackEntry?.savedStateHandle?.set("subjectName", subjectName)
                findNavController().popBackStack()
            }
        }
    }

    private fun selectTime(field: EditText){
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        var time: String = ""

        mTimePicker = TimePickerDialog(context,
            { _, hourOfDay, selectedMinute ->
                var selectedHour = hourOfDay
                val dayTime:String

                if (hourOfDay<12){
                    dayTime = "AM"
                } else if (hourOfDay == 12){
                    dayTime = "PM"
                } else {
                    selectedHour = hourOfDay - 12
                    dayTime = "PM"
                }
                val selectedHourStr = selectedHour.toString().padStart(2,'0')
                val selectedMinuteStr = selectedMinute.toString().padStart(2,'0')

                time ="$selectedHourStr : $selectedMinuteStr $dayTime"
                field.setText(time)
            }, hour, minute, false)
        mTimePicker.show()
    }


}