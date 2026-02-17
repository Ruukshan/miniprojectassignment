package com.example.notesminiapp.data

import androidx.lifecycle.LiveData

/**
 * Repository class that abstracts access to multiple data sources.
 * In this app, it provides a clean API for the rest of the application to interact with the [NoteDao].
 */
class NoteRepository(private val noteDao: NoteDao) {

    /**
     * LiveData list of all notes, observed by the UI to automatically update when data changes.
     */
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    /**
     * Inserts a note into the database.
     * @param note The note to insert.
     */
    suspend fun insert(note: Note) = noteDao.insert(note)

    /**
     * Updates an existing note in the database.
     * @param note The note with updated information.
     */
    suspend fun update(note: Note) = noteDao.update(note)

    /**
     * Deletes a note from the database.
     * @param note The note to delete.
     */
    suspend fun delete(note: Note) = noteDao.delete(note)

    /**
     * Toggles the completion state of a specific note.
     * @param id The ID of the note.
     * @param isCompleted The new completion status.
     */
    suspend fun toggleComplete(id: Int, isCompleted: Boolean) = noteDao.toggleComplete(id, isCompleted)
}
