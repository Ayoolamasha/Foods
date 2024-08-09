package com.ayoolamasha.gopaddi.featureTrips.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.common.forEach
import com.ayoolamasha.gopaddi.databinding.FragmentDatePickerBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DatePickerFragment : DialogFragment() {

    private var _binding: FragmentDatePickerBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var selectedStartDate: Date? = null
    private var selectedEndDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Calendar_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateCalendar()
        setupListeners()
    }

    private fun populateCalendar() {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        binding.calendarContainer.removeAllViews()

        val currentYear = calendar.get(Calendar.YEAR)

        for (month in 0..11) {
            calendar.set(currentYear, month, 1)
            val monthView = LayoutInflater.from(context).inflate(R.layout.view_month, binding.calendarContainer, false)
           binding.tvMonthYear.text = monthYearFormat.format(calendar.time)

            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK)

            val calendarGrid = monthView.findViewById<GridLayout>(R.id.calendarGrid)
            for (i in 1..daysInMonth) {
                val dayButton = TextView(context).apply {
                    text = i.toString()
                    textSize = 16f
                    setPadding(16, 16, 16, 16)
                    setOnClickListener {
                        onDaySelected(calendar.get(Calendar.YEAR), month, i)
                    }
                }

                val params = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec((i + firstDayOfMonth - 2) / 7)
                    columnSpec = GridLayout.spec((i + firstDayOfMonth - 2) % 7)
                }

                calendarGrid.addView(dayButton, params)
            }

            binding.calendarContainer.addView(monthView)
        }
    }

    private fun onDaySelected(year: Int, month: Int, day: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month, day)
        }.time

        if (selectedStartDate == null || (selectedStartDate != null && selectedEndDate != null)) {
            selectedStartDate = selectedDate
            selectedEndDate = null
            binding.startDateText.text = SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(selectedStartDate)
            binding.endDateText.text = "End Date"
        } else {
            selectedEndDate = selectedDate
            binding.startDateText.text = SimpleDateFormat("EEE, MMM d", Locale.getDefault()).format(selectedEndDate)
        }

        updateCalendarSelection()
    }

    private fun updateCalendarSelection() {
        binding.calendarContainer.forEach { monthView ->
            val calendarGrid = monthView.findViewById<GridLayout>(R.id.calendarGrid)
            calendarGrid.forEach { dayView ->
                val dayText = (dayView as TextView).text.toString().toInt()
                val date = Calendar.getInstance().apply {
                    set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dayText)
                }.time

                val isSelected = (selectedStartDate != null && date == selectedStartDate) ||
                        (selectedEndDate != null && date == selectedEndDate)

                dayView.setBackgroundResource(if (isSelected) R.drawable.selected_date_background else 0)
            }
        }
    }

    private fun setupListeners() {
        binding.btnChooseDate.setOnClickListener {
            // Handle date selection confirmation
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
