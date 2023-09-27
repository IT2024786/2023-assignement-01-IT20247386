package notes.coding.meet.notes_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import notes.coding.meet.notes_app.models.Task
import notes.coding.meet.notes_app.repository.NoteRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository(application)
    val taskStateFlow get() =  noteRepository.taskStateFlow
    val statusLiveData get() =  noteRepository.statusLiveData
    val sortByLiveData get() =  noteRepository.sortByLiveData

    fun setSortBy(sort:Pair<String,Boolean>){
        noteRepository.setSortBy(sort)
    }

    fun getTaskList(isAsc : Boolean, sortByName:String) {
        noteRepository.getTaskList(isAsc, sortByName)
    }

    fun insertTask(task: Task){
        noteRepository.insertTask(task)
    }

    fun deleteTask(task: Task) {
        noteRepository.deleteTask(task)
    }

    fun deleteTaskUsingId(taskId: String){
        noteRepository.deleteTaskUsingId(taskId)
    }

    fun updateTask(task: Task) {
        noteRepository.updateTask(task)
    }

    fun updateTaskPaticularField(taskId: String,title:String,description:String) {
        noteRepository.updateTaskPaticularField(taskId, title, description)
    }
    fun searchTaskList(query: String){
        noteRepository.searchTaskList(query)
    }
}