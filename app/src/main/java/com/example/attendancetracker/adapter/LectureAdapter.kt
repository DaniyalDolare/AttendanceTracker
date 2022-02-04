package com.example.attendancetracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancetracker.R
import com.example.attendancetracker.extensions.getTimeFromMillis
import com.example.attendancetracker.models.Attendance
import com.example.attendancetracker.models.Lecture

class AttendanceAdapter(private val context: Context, private val dataset: List<Attendance>) : RecyclerView.Adapter<AttendanceAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val attendanceCard: View = view.findViewById(R.id.attendanceCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.attendance_card, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.attendanceCard.findViewById<TextView>(R.id.subjectName).text = item.lecture.subjectName
        holder.attendanceCard.findViewById<TextView>(R.id.time).text = item.lecture.startTime.getTimeFromMillis()+" to "+item.lecture.endTime.getTimeFromMillis()
    }

    override fun getItemCount(): Int =dataset.size


}