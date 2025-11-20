package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.uiStateData.CalendarTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId

class CalendarViewModel(taskViewModel: TaskListViewModel): ViewModel(){

    // untuk tahun
    private val _currentYear = MutableStateFlow(LocalDate.now().year)
    val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    // untuk bulan
    private val _currentMonth = MutableStateFlow(LocalDate.now().monthValue)
    val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

    // untuk tanggal yang dipilih
    private val _selectedDate = MutableStateFlow<Triple<Int, Int, Int>?>(null)
    val selectedDate: StateFlow<Triple<Int, Int, Int>?> = _selectedDate.asStateFlow()

    // semua task yang ada di kalender di grup berdasarkan tanggal bulan tahun
    private val _calendarTasks = MutableStateFlow<List<CalendarTask>>(emptyList())
    val calendarTasks: StateFlow<List<CalendarTask>> = _calendarTasks.asStateFlow()

    private val _taskUiState = MutableStateFlow<List<Task>>(emptyList())
    val taskUiState: StateFlow<List<Task>> = _taskUiState

    // Semua tugas di tanggal yang di pilih
    private val _selectedDateTasks = MutableStateFlow<List<Task>>(emptyList())
    val selectedDateTasks: StateFlow<List<Task>> = _selectedDateTasks.asStateFlow()

    init {
        viewModelScope.launch {
            taskViewModel.taskState.collect { state ->
                _taskUiState.value = state.tasks
                loadCalendarTasks() // update kalender berdasarkan perubahan
            }
        }
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

        val filteredTasks = _taskUiState.value.filter { task ->
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