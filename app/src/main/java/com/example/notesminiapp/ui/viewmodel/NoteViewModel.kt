package com.example.notesminiapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesminiapp.data.AppDatabase
import com.example.notesminiapp.data.Note
import com.example.notesminiapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class that serves as a communication center between the Repository (data layer)
 * and the UI (Activity/Fragment).
 * 
 * It uses [AndroidViewModel] because it requires the Application context to initialize the database.
 */
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    // Repository instance to handle data operations
    private val repository: NoteRepository
    
    /**
     * LiveData that holds the list of all notes.
     * The UI observes this property to get automatic updates when data changes.
     */
    val allNotes: LiveData<List<Note>>

    init {
        // Initialize the DAO and the Repository
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        
        // Fetch all notes from the repository
        allNotes = repository.allNotes
    }

    /**
     * Inserts a new note into the database.
     * Operation is performed in a background thread using Coroutines.
     */
    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    /**
     * Updates an existing note in the database.
     * Operation is performed in a background thread using Coroutines.
     */
    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    /**
     * Deletes a note from the database.
     * Operation is performed in a background thread using Coroutines.
     */
    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    /**
     * Toggles the completion status of a note.
     * 
     * @param note The note to toggle. The ID is used for lookup and current status is inverted.
     */
    fun toggleComplete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.toggleComplete(note.id, !note.isCompleted)
    }
}
