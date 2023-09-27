package notes.coding.meet.notes_app.converters

import androidx.room.TypeConverter
import java.util.Date

class TypeConverter {
    // This function converts a timestamp (Long) to a Date object.
    @TypeConverter
    fun fromTimestamp(value:Long): Date {
        return Date(value)
    }
    // This function converts a Date object to a timestamp (Long).
    @TypeConverter
    fun dateToTimestamp(date:Date): Long {
        return date.time
    }
}