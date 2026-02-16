package com.example.notesminiapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesminiapp.R
import com.example.notesminiapp.data.Note
import com.example.notesminiapp.databinding.ActivityAddEditNoteBinding
import com.example.notesminiapp.ui.viewmodel.NoteViewModel

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        noteId = intent.getIntExtra("NOTE_ID", -1)
        if (noteId != -1) {
            binding.etTitle.setText(intent.getStringExtra("NOTE_TITLE"))
            binding.etContent.setText(intent.getStringExtra("NOTE_CONTENT"))
        }

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(id = noteId, title = title, content = content)
            if (noteId == -1) {
                noteViewModel.insert(note)
            } else {
                noteViewModel.update(note)
            }

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}