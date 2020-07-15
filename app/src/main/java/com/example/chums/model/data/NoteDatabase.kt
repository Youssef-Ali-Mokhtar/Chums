package com.example.chums.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chums.model.pojo.Note
import kotlinx.coroutines.Dispatchers

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun noteDao() : NoteDao

    companion object{
        var noteDatabase : NoteDatabase? = null
        val DB_NAME = "notes_database"

        fun getInstance(context : Context) : NoteDatabase {
            synchronized(Dispatchers.IO) {
                if (noteDatabase == null) {
                    noteDatabase = Room.databaseBuilder(
                        context,
                        NoteDatabase::class.java,
                        DB_NAME
                    ).build()
                }
                return noteDatabase as NoteDatabase
            }
        }
    }
}