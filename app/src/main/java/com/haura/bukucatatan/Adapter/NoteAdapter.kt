package com.haura.bukucatatan.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.haura.bukucatatan.Note
import com.haura.bukucatatan.NoteDatabaseHelper
import com.haura.bukucatatan.R
import com.haura.bukucatatan.UpdateNoteActivity

class NoteAdapter(
    private var notes: List<Note>,
    context: Context
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class NoteViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val  judulNote : TextView = itemview.findViewById(R.id.tvNotesJudul)
        val  isiNote : TextView = itemview.findViewById(R.id.tvNoteIsi)
        val updateButton: ImageView = itemview.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemview.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val noteItem = notes[position]
        holder.judulNote.text = noteItem.title
        holder.isiNote.text = noteItem.content

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", noteItem.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            AlertDialog.Builder(holder.itemView.context).apply {
                setTitle("Konfirmasi")
                setMessage("Apakah anda ingin melanjutkan?")
                setIcon(R.drawable.baseline_delete_24)

                setPositiveButton("Yakin"){dialogInterface, i ->
                    db.deletenote(noteItem.id)
                    refreshData(db.getAllNotes())
                    dialogInterface.dismiss()
                }

                setNegativeButton("Batal"){dialogInterface, i->
                    dialogInterface.dismiss()
                }
                }.show()

            Toast.makeText(holder.itemView.context, "Catatan Dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun refreshData(newNotes : List<Note>){
        notes = newNotes
        notifyDataSetChanged()
        }

}