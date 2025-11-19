package com.example.assignmenttrack.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.Model.CalendarTask
import com.example.assignmenttrack.Model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import com.example.assignmenttrack.Model.TaskList
import java.time.Month

class CalendarViewModel: ViewModel(){

    // untuk tahun
    private val _currentYear = MutableStateFlow(java.time.LocalDate.now().year)
    val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    // untuk bulan
    private val _currentMonth = MutableStateFlow(java.time.LocalDate.now().monthValue)
    val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

    // untuk tanggal yang dipilih
    private val _selectedDate = MutableStateFlow<Triple<Int, Int, Int>?>(null)
    val selectedDate: StateFlow<Triple<Int, Int, Int>?> = _selectedDate.asStateFlow()

    // semua task yang ada di kalender di grup berdasarkan tanggal bulan tahun
    private val _calendarTasks = MutableStateFlow<List<CalendarTask>>(emptyList())
    val calendarTasks: StateFlow<List<CalendarTask>> = _calendarTasks.asStateFlow()

    // Semua tugas di tanggal yang di pilih
    private val _selectedDateTasks = MutableStateFlow<List<Task>>(emptyList())
    val selectedDateTasks: StateFlow<List<Task>> = _selectedDateTasks.asStateFlow()

    init {
        loadCalendarTasks()
    }

    fun onDayClick(day: Int, month: Int, year: Int) {
        viewModelScope.launch {
            _selectedDate.value = Triple(day, month, year)
            loadSelectedDateTasks(selectedDate = _selectedDate.value, currentCalendarTasks = _calendarTasks.value)

            if (month != _currentMonth.value || year != _currentYear.value) {
                changeMonth(month, year)
            }
        }
    }

    fun changeMonth(newMonth: Int, newYear: Int){
        viewModelScope.launch {
            _currentMonth.value = newMonth
            _currentYear.value = newYear
            loadCalendarTasks()
        }
    }

    fun loadSelectedDateTasks(
        selectedDate: Triple<Int, Int, Int>?,
        currentCalendarTasks: List<CalendarTask>
    ){
        if (selectedDate == null) {
            _selectedDateTasks.value = emptyList()
        }
        else{
            val (day, _, _) = selectedDate
            _selectedDateTasks.value = currentCalendarTasks
                .firstOrNull { it.day == day }?.tasks ?: emptyList()
        }
    }
    private fun loadCalendarTasks(){
        val currentMonth = Month.of(_currentMonth.value)
        val currentYear = _currentYear.value

        val filteredTasks = TaskList.filter { task -> // Masih Pakai TaskList
            val taskDate = task.deadline.atZone(ZoneId.systemDefault())
            taskDate.month == currentMonth && taskDate.year == currentYear
        }

        _calendarTasks.value = filteredTasks
            .groupBy { it.deadline.atZone(ZoneId.systemDefault()).dayOfMonth }
            .map { (day, tasks) ->
                CalendarTask(day = day, tasks = tasks)
            }
    }
}