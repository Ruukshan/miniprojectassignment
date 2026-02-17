package com.example.notesminiapp.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesminiapp.data.Note
import com.example.notesminiapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Adapter for the RecyclerView in MainActivity.
 * Displays a list of [Note] items using ViewBinding and DiffUtil for efficient updates.
 */
class NoteAdapter(
    private val onClick: (Note) -> Unit,
    private val onLongClick: (Note) -> Unit,
    private val onToggleComplete: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback) {

    /**
     * ViewHolder class that binds note data to the item layout.
     */
    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds a Note object to the UI elements in the item layout.
         */
        fun bind(note: Note, onClick: (Note) -> Unit, onLongClick: (Note) -> Unit, onToggleComplete: (Note) -> Unit) {
            binding.tvTitle.text = note.title
            binding.tvContent.text = note.content
            
            // Format and display the timestamp
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            binding.tvTimestamp.text = dateFormat.format(note.timestamp)

            // Setup Checkbox and apply strike-through effect if completed
            binding.cbCompleted.isChecked = note.isCompleted
            binding.tvTitle.paintFlags = if (note.isCompleted)
                binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                binding.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            // Handle checkbox changes
            binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                onToggleComplete(note.copy(isCompleted = isChecked))
            }

            // Handle item click and long click
            binding.root.setOnClickListener { onClick(note) }
            binding.root.setOnLongClickListener { 
                onLongClick(note)
                true 
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note, onClick, onLongClick, onToggleComplete)
    }

    /**
     * DiffUtil callback used by ListAdapter to calculate minimal changes between lists.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean = oldItem == newItem
    }
}
