package com.example.notesminiapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object (DAO) for the notes table.
 * Contains methods to perform SQL operations on the database.
 */
@Dao
interface NoteDao {

    /**
     * Retrieve all notes from the table, ordered by timestamp in descending order.
     * @return LiveData list of Note objects to observe changes in the UI.
     */
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    /**
     * Insert a new note into the database.
     * If a note with the same ID already exists, it will be replaced.
     * @param note The Note entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    /**
     * Update an existing note in the database.
     * @param note The Note entity with updated data.
     */
    @Update
    suspend fun update(note: Note)

    /**
     * Delete a specific note from the database.
     * @param note The Note entity to be removed.
     */
    @Delete
    suspend fun delete(note: Note)

    /**
     * Toggle the completion status of a note.
     * @param id The ID of the note to update.
     * @param isCompleted The new completion status.
     */
    @Query("UPDATE notes SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun toggleComplete(id: Int, isCompleted: Boolean)
}
