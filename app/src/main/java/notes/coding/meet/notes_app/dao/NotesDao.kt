package notes.coding.meet.notes_app.dao

import androidx.room.*
import notes.coding.meet.notes_app.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    // Query to get a list of notes sorted by note title in either ascending or descending order

    @Query("""SELECT * FROM Task ORDER BY
        CASE WHEN :isAsc = 1 THEN taskTitle END ASC, 
        CASE WHEN :isAsc = 0 THEN taskTitle END DESC""")
    fun getTaskListSortByTaskTitle(isAsc: Boolean) : Flow<List<Task>>

    // Query to get a list of notes sorted by task date in either ascending or descending order

    @Query("""SELECT * FROM Task ORDER BY
        CASE WHEN :isAsc = 1 THEN date END ASC, 
        CASE WHEN :isAsc = 0 THEN date END DESC""")
    fun getTaskListSortByTaskDate(isAsc: Boolean) : Flow<List<Task>>
    // Insert a note  into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    // First way to delete a note from the database
    @Delete
    suspend fun deleteTask(task: Task) : Int


    // Second way to delete a note from the database using its ID
    @Query("DELETE FROM Task WHERE taskId == :taskId")
    suspend fun deleteTaskUsingId(taskId: String) : Int

    // Update an existing note in the database
    @Update
    suspend fun updateTask(task: Task): Int

    // Update specific fields (note title and description) of a note in the database using its ID
    @Query("UPDATE Task SET taskTitle=:title, description = :description WHERE taskId = :taskId")
    suspend fun updateTaskPaticularField(taskId:String,title:String,description:String): Int

    // Search for notes with titles that match the provided query, ordered by date in descending order
    @Query("SELECT * FROM Task WHERE taskTitle LIKE :query ORDER BY date DESC")
    fun searchTaskList(query: String) : Flow<List<Task>>
}