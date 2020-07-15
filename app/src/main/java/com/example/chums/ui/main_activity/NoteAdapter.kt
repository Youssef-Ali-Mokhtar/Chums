package com.example.chums.ui.main_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chums.R
import com.example.chums.model.pojo.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var notes = ArrayList<Note>()

    fun addData(notes : ArrayList<Note>){
        this.notes = notes
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position], onItemClickListener)

    }

    override fun getItemCount(): Int {
        return notes.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note, onItemClickListener: OnItemClickListener) = with(itemView){
            title_id.text = note.title
            content_id.text = showMaximum20Characters(note.content)
            date_id.text = note.date
            time_id.text = note.time
            setOnClickListener {
                onItemClickListener.onItemClick(note)
            }
        }

        fun showMaximum20Characters(s : String):String {
            if(s.length>20){
                return s.substring(0, 20)+"..."
            }
            return s
        }

    }

    interface OnItemClickListener{
        fun onItemClick(note: Note)
    }




}