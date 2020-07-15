package com.example.chums.ui.add_note

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chums.model.data.NoteDatabase
import com.example.chums.model.pojo.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel : ViewModel() {
    val addNoteMutableLiveData = MutableLiveData<String>()

    public fun addNoteFromViewModel(context : Context, note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            addNoteToDatabase(context, note)
            addNoteMutableLiveData.postValue("Note added")
        }
    }

    suspend private fun addNoteToDatabase(context : Context, note : Note){
        NoteDatabase.getInstance(context).noteDao().addNote(note)
    }
}