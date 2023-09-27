package notes.coding.meet.notes_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Define an Entity class with Room annotations
@Entity(tableName = "Task") // Specifies that this class represents a database table named "Task"
data class Task(
    @PrimaryKey(autoGenerate = false) // Indicates this field as the primary key, auto-generating is set to false
    @ColumnInfo(name = "taskId") // Specifies the column name in the database
    val id: String, // Unique identifier for the task

    @ColumnInfo(name = "taskTitle") // Specifies the column name in the database
    val title: String, // Title of the note

    val description: String, // Description of the note
    val date: Date // Date associated with the note
)
