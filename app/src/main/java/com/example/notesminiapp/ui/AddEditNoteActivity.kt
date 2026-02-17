package com.example.notesminiapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesminiapp.R
import com.example.notesminiapp.data.Note
import com.example.notesminiapp.databinding.ActivityAddEditNoteBinding
import com.example.notesminiapp.ui.viewmodel.NoteViewModel

/**
 * Activity for adding a new note or editing an existing one.
 * It uses ViewBinding for UI access and ViewModel for database operations.
 */
class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding and set the content view
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup the toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize the ViewModel
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Retrieve existing note data from intent if we are in "Edit" mode
        noteId = intent.getIntExtra("NOTE_ID", -1)
        val incomingTitle = intent.getStringExtra("NOTE_TITLE") ?: ""
        val incomingContent = intent.getStringExtra("NOTE_CONTENT") ?: ""

        // Set the Activity title based on whether we are adding or editing
        supportActionBar?.title = if (noteId == -1) "Add Note" else "Edit Note"

        // If editing an existing note, pre-populate the input fields
        if (noteId != -1) {
            binding.etTitle.setText(incomingTitle)
            binding.etContent.setText(incomingContent)
        }

        // Set up the click listener for the Save button
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            // Validate that the fields are not empty before saving
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepare the Note object; include ID if updating
            val note = if (noteId == -1) {
                Note(
                    title = title,
                    content = content
                )
            } else {
                Note(
                    id = noteId,
                    title = title,
                    content = content
                )
            }

            // Perform the database operation via the ViewModel
            if (noteId == -1) {
                noteViewModel.insert(note)
            } else {
                noteViewModel.update(note)
            }

            // Notify the user and return to the previous screen
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
