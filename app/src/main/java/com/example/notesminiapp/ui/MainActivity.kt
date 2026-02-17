package com.example.notesminiapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesminiapp.databinding.ActivityMainBinding
import com.example.notesminiapp.ui.viewmodel.NoteViewModel

/**
 * Main Activity of the application. Displays a list of notes.
 * Users can add, edit, delete, or mark notes as completed.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Setup Toolbar
        setSupportActionBar(binding.toolbar)

        // Initialize ViewModel to handle data operations
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Setup RecyclerView Adapter with item click callbacks
        adapter = NoteAdapter(
            onClick = { note ->
                // Start AddEditNoteActivity for editing an existing note
                val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                    putExtra("NOTE_TITLE", note.title)
                    putExtra("NOTE_CONTENT", note.content)
                }
                startActivity(intent)
            },
            onLongClick = { note ->
                // Delete note on long press
                noteViewModel.delete(note)
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            },
            onToggleComplete = { note ->
                // Update note status when checkbox is toggled
                noteViewModel.toggleComplete(note)
            }
        )

        // Configure RecyclerView with a LayoutManager and the adapter
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        // Observe the notes LiveData from ViewModel and update the adapter
        noteViewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }

        // Handle Floating Action Button click to add a new note
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditNoteActivity::class.java))
        }
    }
}
