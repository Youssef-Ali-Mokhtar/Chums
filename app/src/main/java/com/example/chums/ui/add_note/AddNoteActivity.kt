package com.example.chums.ui.add_note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chums.R
import com.example.chums.model.data.NoteDatabase
import com.example.chums.model.pojo.Note
import com.example.chums.ui.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_add_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    lateinit var addNoteViewModel : AddNoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //MVVM setup
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        setObservers()
        //Adds a note to the database then transfers the user from NoteDetailsActivity to MainActivity
        add_note_button_id.setOnClickListener {
            val note : Note =
                Note(
                    add_note_title_id.text.toString(),
                    add_note_content_id.text.toString(),
                    getDate(),
                    getTime()
                )
            addNoteViewModel.addNoteFromViewModel(this, note)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    //Observes a "note added" massage to confirm the update
    private fun setObservers() {
        addNoteViewModel.addNoteMutableLiveData.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
    //Gets time of note creation
    fun getTime() : String{
        val time = SimpleDateFormat("hh:mm:ss")
        val currentTime = time.format(Date())
        return currentTime.toString()
    }
    //Gets date of note creation
    fun getDate() : String {
        val date = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val currentDate = date.format(Date())
        return currentDate.toString()
    }


}
