package notes.coding.meet.notes_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import notes.coding.meet.notes_app.converters.TypeConverter
import notes.coding.meet.notes_app.dao.NotesDao
import notes.coding.meet.notes_app.models.Task
// Define a Room database with annotations
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class) // Use TypeConverter to handle data type conversions
abstract class NotesRoomDb : RoomDatabase() {

    abstract val notesDao : NotesDao // Abstract property representing the DAO for database operations

    // Companion object to provide database instance creation method
    companion object {
        @Volatile
        private var INSTANCE: NotesRoomDb? = null
        // Create and return a database instance
        fun getInstance(context: Context): NotesRoomDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDb::class.java,
                    "task_db" // Name of the database
                ).build().also {
                    INSTANCE = it // Assign the created instance to the volatile variable
                }
            }

        }
    }

}