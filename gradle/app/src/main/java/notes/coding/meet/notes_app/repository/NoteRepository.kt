package notes.coding.meet.notes_app.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import notes.coding.meet.notes_app.database.NotesRoomDb
import notes.coding.meet.notes_app.models.Task
import notes.coding.meet.notes_app.utils.Resource
import notes.coding.meet.notes_app.utils.Resource.Error
import notes.coding.meet.notes_app.utils.Resource.Loading
import notes.coding.meet.notes_app.utils.Resource.Success
import notes.coding.meet.notes_app.utils.StatusResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteRepository(application: Application) {
    // Initialize the DAO (Data Access Object) for database operations
    private val taskDao = NotesRoomDb.getInstance(application).notesDao

    // Create a StateFlow to represent the loading status of the note list
    private val _taskStateFlow = MutableStateFlow<Resource<Flow<List<Task>>>>(Loading())
    val taskStateFlow: StateFlow<Resource<Flow<List<Task>>>>
        get() = _taskStateFlow
    // Create a MutableLiveData to store the status of different operations (e.g., insert, delete)
    private val _statusLiveData = MutableLiveData<Resource<StatusResult>>()
    val statusLiveData: LiveData<Resource<StatusResult>>
        get() = _statusLiveData

    // Create a MutableLiveData to store the sorting order (field and direction)
    private val _sortByLiveData = MutableLiveData<Pair<String,Boolean>>().apply {
        postValue(Pair("title",true))
    }
    val sortByLiveData: LiveData<Pair<String,Boolean>>
        get() = _sortByLiveData

    // Function to set the sorting order
    fun setSortBy(sort:Pair<String,Boolean>){
        _sortByLiveData.postValue(sort)
    }

    // Function to retrieve the note list sorted by specified criteria
    fun getTaskList(isAsc : Boolean, sortByName:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _taskStateFlow.emit(Loading())
                delay(500)
                val result = if (sortByName == "title"){
                    taskDao.getTaskListSortByTaskTitle(isAsc)
                }else{
                    taskDao.getTaskListSortByTaskDate(isAsc)
                }
                _taskStateFlow.emit(Success("loading", result))
            } catch (e: Exception) {
                _taskStateFlow.emit(Error(e.message.toString()))
            }
        }
    }

    // Function to insert a new note
    fun insertTask(task: Task) {
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                handleResult(result.toInt(), "Inserted Task Successfully", StatusResult.Added)
            }
        } catch (e: Exception) {
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    // Function to delete a note
    fun deleteTask(task: Task) {
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                handleResult(result, "Deleted Task Successfully", StatusResult.Deleted)

            }
        } catch (e: Exception) {
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    // Function to delete a note using its ID
    fun deleteTaskUsingId(taskId: String) {
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTaskUsingId(taskId)
                handleResult(result, "Deleted Task Successfully", StatusResult.Deleted)

            }
        } catch (e: Exception) {
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    // Function to update a note
    fun updateTask(task: Task) {
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)

            }
        } catch (e: Exception) {
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    // Function to update specific fields of a notes
    fun updateTaskPaticularField(taskId: String, title: String, description: String) {
        try {
            _statusLiveData.postValue(Loading())
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTaskPaticularField(taskId, title, description)
                handleResult(result, "Updated Task Successfully", StatusResult.Updated)

            }
        } catch (e: Exception) {
            _statusLiveData.postValue(Error(e.message.toString()))
        }
    }

    // Function to search for notes based on a query
    fun searchTaskList(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _taskStateFlow.emit(Loading())
                val result = taskDao.searchTaskList("%${query}%")
                _taskStateFlow.emit(Success("loading", result))
            } catch (e: Exception) {
                _taskStateFlow.emit(Error(e.message.toString()))
            }
        }
    }

    // Function to handle the result of database operations and update status
    private fun handleResult(result: Int, message: String, statusResult: StatusResult) {
        if (result != -1) {
            _statusLiveData.postValue(Success(message, statusResult))
        } else {
            _statusLiveData.postValue(Error("Something Went Wrong", statusResult))
        }
    }
}