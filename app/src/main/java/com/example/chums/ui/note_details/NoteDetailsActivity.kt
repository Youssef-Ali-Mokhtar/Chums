package com.example.chums.ui.note_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chums.R
import com.example.chums.model.data.NoteDatabase
import com.example.chums.ui.main_activity.MainActivity
import kotlinx.android.synthetic.main.activity_note_details.*

class NoteDetailsActivity : AppCompatActivity() {
    lateinit var noteDetailsViewModel: NoteDetailsViewModel
    lateinit var title : String
    lateinit var content : String
    lateinit var date : String
    lateinit var time : String
    var id : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //MVVM setup
        noteDetailsViewModel = ViewModelProvider(this).get(NoteDetailsViewModel::class.java)
        //Observes a "note updated" massage to confirm the update
        setObservers()
        //Gets note details from MainActivity through an intent to show it on NoteDetailsActivity
        getDetails()
        note_details_title_id.setText(title)
        note_details_content_id.setText(content)
        //Updates database with the new values from the title and content text fields then transfers the user from NoteDetailsActivity to MainActivity
        note_detail_buttons_id.setOnClickListener {
            var getTitle = note_details_title_id.text.toString()
            var getContent = note_details_content_id.text.toString()
            noteDetailsViewModel.updateNoteFromViewModel(this, id, getTitle, getContent)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setObservers() {
        noteDetailsViewModel.updateNoteMutableLiveData.observe(this, Observer {

            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    public fun getDetails(){
        var intent = getIntent()
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        date = intent.getStringExtra("date")
        time = intent.getStringExtra("time")
        id = intent.getIntExtra("note_id",0)
    }

}
