package notes.coding.meet.notes_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coding.meet.notes.databinding.ViewNoteGridLayoutBinding
import com.coding.meet.notes.databinding.ViewNoteListLayoutBinding
import notes.coding.meet.notes_app.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class NotesListAdapter(
    private val isList: MutableLiveData<Boolean>,
    private val deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
) :
    ListAdapter<Task, RecyclerView.ViewHolder>(DiffCallback()) {

    // ViewHolder for displaying notes in a list
    class ListTaskViewHolder(private val viewTaskListLayoutBinding: ViewNoteListLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskListLayoutBinding.root) {

        // Bind data to the list item view
        fun bind(
            task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {
            viewTaskListLayoutBinding.titleTxt.text = task.title
            viewTaskListLayoutBinding.descrTxt.text = task.description

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())

            viewTaskListLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            // Set up click listener for delete action
            viewTaskListLayoutBinding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }

            // Set up click listener for edit action
            viewTaskListLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }
        }
    }

    // ViewHolder for displaying notes in a grid
    class GridTaskViewHolder(private val viewTaskGridLayoutBinding: ViewNoteGridLayoutBinding) :
        RecyclerView.ViewHolder(viewTaskGridLayoutBinding.root) {

        // Bind data to the grid item view
        fun bind(
            task: Task,
            deleteUpdateCallback: (type: String, position: Int, task: Task) -> Unit,
        ) {
            viewTaskGridLayoutBinding.titleTxt.text = task.title
            viewTaskGridLayoutBinding.descrTxt.text = task.description

            val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())

            viewTaskGridLayoutBinding.dateTxt.text = dateFormat.format(task.date)

            // Set up click listener for delete action
            viewTaskGridLayoutBinding.deleteImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("delete", adapterPosition, task)
                }
            }

            // Set up click listener for edit action
            viewTaskGridLayoutBinding.editImg.setOnClickListener {
                if (adapterPosition != -1) {
                    deleteUpdateCallback("update", adapterPosition, task)
                }
            }
        }
    }

    // Create the appropriate ViewHolder based on the view type
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (viewType == 1) {  // Grid_Item
            GridTaskViewHolder(
                ViewNoteGridLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {  // List_Item
            ListTaskViewHolder(
                ViewNoteListLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = getItem(position)

        if (isList.value!!) {
            (holder as ListTaskViewHolder).bind(task, deleteUpdateCallback)
        } else {
            (holder as GridTaskViewHolder).bind(task, deleteUpdateCallback)
        }
    }

    // Determine the view type based on the layout
    override fun getItemViewType(position: Int): Int {
        return if (isList.value!!) {
            0 // List_Item
        } else {
            1 // Grid_Item
        }
    }

    // Callback for calculating the differences between items in the list
    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}
