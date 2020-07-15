package com.example.chums.ui.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.chums.*
import com.example.chums.model.data.NoteDatabase
import com.example.chums.model.pojo.Note
import com.example.chums.ui.add_note.AddNoteActivity
import com.example.chums.ui.note_details.NoteDetailsActivity

class MainActivity : AppCompatActivity(),
    NoteAdapter.OnItemClickListener,
    Dialog.OnDialogClickListner {

    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var noteAdapter : NoteAdapter

    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var dividerItemDecoration : DividerItemDecoration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_id)

        //Transfers from MainActivity to AddNoteActivity
        fab_id.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        //Adapter and recycler view integration and setup
        noteAdapter = NoteAdapter(this)
        recycler_view_id.adapter = noteAdapter
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_id.layoutManager = layoutManager

        //Divides recycler view items
        dividerItemDecoration = DividerItemDecoration(
            recycler_view_id.getContext(),
            RecyclerView.VERTICAL
        )
        recycler_view_id.addItemDecoration(dividerItemDecoration)
        //Integrates swiping left and right with recycler view
        ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recycler_view_id)
        //MVVM setup
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setObservers()
        //Get all notes using MVVM
        mainActivityViewModel.getNotesFromViewModel(applicationContext)

    }

    //MVVM observers (show notes, delete all notes, delete one note)
    private fun setObservers() {
        mainActivityViewModel.getNotesMutableLiveData.observe(this, Observer {
            recycler_view_id.adapter = noteAdapter
            noteAdapter.addData(it as ArrayList<Note>)
        })
        mainActivityViewModel.deleteNotesMutableLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    //Create/inflate menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    //Implement menu item (dialog)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        if(id == R.id.delete_icon_id){
            openDialog()
        }
        return true
    }
    //Implements note swiping left or right
    val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            mainActivityViewModel.deleteOneNoteFromViewModel(this@MainActivity, noteAdapter.notes[viewHolder.adapterPosition].id)
            noteAdapter.notes.removeAt(viewHolder.adapterPosition)
            noteAdapter.notifyDataSetChanged()
        }

    }
    //Trasnfers from MainActivity to DetailsActivity
    override fun onItemClick(note: Note) {
        var intent = Intent(this, NoteDetailsActivity::class.java)
        intent.putExtra("title", note.title)
        intent.putExtra("content", note.content)
        intent.putExtra("date", note.date)
        intent.putExtra("time", note.time)
        intent.putExtra("note_id", note.id)
        startActivity(intent)
    }
    //Opens dialog
    fun openDialog(){
        var dialog = Dialog()
        dialog.show(supportFragmentManager, "Dialog")
    }
    //Make sure you wanna delete all notes then delete them all
    override fun onDialogClick() {
        mainActivityViewModel.deleteNotesFromViewModel(this)
        noteAdapter.notes.clear()
        noteAdapter.notifyDataSetChanged()
    }


}
