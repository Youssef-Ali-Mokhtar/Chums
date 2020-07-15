package com.example.chums.ui.main_activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chums.model.data.NoteDatabase
import com.example.chums.model.pojo.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel(){
    val getNotesMutableLiveData = MutableLiveData<List<Note>>()
    val deleteNotesMutableLiveData = MutableLiveData<String>()

    public fun getNotesFromViewModel(context : Context){
        viewModelScope.launch(Dispatchers.IO) {
            getNotesMutableLiveData.postValue(getNotesFromDatabase(context))
        }
    }
    public fun deleteNotesFromViewModel(context : Context){
        viewModelScope.launch(Dispatchers.IO) {
            deleteNotesFromDatabase(context)
            deleteNotesMutableLiveData.postValue("Notes deleted")
        }
    }

    public fun deleteOneNoteFromViewModel(context : Context, id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            deleteOneNoteFromDatabase(context, id)
            deleteNotesMutableLiveData.postValue("Note deleted")
        }
    }

    suspend private fun getNotesFromDatabase(context : Context): List<Note>{
        return NoteDatabase.getInstance(context).noteDao().getAllNotes()
    }

    suspend private fun deleteNotesFromDatabase(context : Context){
        NoteDatabase.getInstance(context).noteDao().deleteAllNotes()
    }

    suspend private fun deleteOneNoteFromDatabase(context : Context, id : Int){
        NoteDatabase.getInstance(context).noteDao().deleteNoteById(id)
    }

}