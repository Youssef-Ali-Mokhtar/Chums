package com.example.chums.model.data

import androidx.room.*
import com.example.chums.model.pojo.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note : Note)

    @Query("update Note set title = :title, content = :content where id = :id")
    suspend fun updateNote(id: Int, title : String, content : String)

    @Query("delete from Note")
    suspend fun deleteAllNotes()

    @Query("delete from Note where id = :id")
    suspend fun deleteNoteById(id : Int)

    @Query("select * from Note")
    suspend fun getAllNotes() : List<Note>
}