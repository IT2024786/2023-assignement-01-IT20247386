package notes.coding.meet.notes_app.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

// Enum to represent different status values
enum class Status{
    SUCCESS,
    ERROR,
    LOADING
}
// Enum to represent different results of status operations
enum class StatusResult{
    Added,
    Updated,
    Deleted
}

// Function to hide the software keyboard associated with a specific View
fun Context.hideKeyBoard(view : View){
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }catch (e: Exception){
        e.printStackTrace()
    }
}

// Function to display a long-duration toast message
fun Context.longToastShow(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

// Function to configure a custom dialog
fun Dialog.setupDialog(layoutResId: Int){
    setContentView(layoutResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    setCancelable(false)
}

// Function to validate an EditText and its associated TextInputLayout
fun validateEditText(editText: EditText, textTextInputLayout: TextInputLayout): Boolean {
    return when {
        editText.text.toString().trim().isEmpty() -> {
            textTextInputLayout.error = "Required"
            false
        }
        else -> {
            textTextInputLayout.error = null
            true
        }
    }
}
// Function to clear the text of an EditText and reset its associated TextInputLayout
fun clearEditText(editText: EditText, textTextInputLayout: TextInputLayout) {
    editText.text = null
    textTextInputLayout.error = null
}