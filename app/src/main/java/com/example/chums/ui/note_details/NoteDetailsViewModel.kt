package com.example.chums.ui.note_details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chums.model.data.NoteDatabase
import com.example.chums.model.pojo.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteDetailsViewModel : ViewModel() {
    val updateNoteMutableLiveData = MutableLiveData<String>()

    public fun updateNoteFromViewModel(context : Context, id : Int, title : String, content : String){
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteToDatabase(context, id, title, content)
            updateNoteMutableLiveData.postValue("Note updated")
        }
    }

    suspend private fun updateNoteToDatabase(context : Context, id : Int, title : String, content : String){
        NoteDatabase.getInstance(context).noteDao().updateNote(id, title, content)
    }
}