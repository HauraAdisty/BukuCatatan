package com.haura.bukucatatan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haura.bukucatatan.databinding.ActivityAddNoteBinding
import com.haura.bukucatatan.databinding.ActivityUpdateBinding

class UpdateNoteActivity : AppCompatActivity(){
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

       noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1){
            finish()
            return
        }
        val note = db.getNoteById(noteId)
        binding.updatetitleEditText.setText(note.title)
        binding.updatecontentEditText.setText(note.content)

        binding.UpdatesaveButton.setOnClickListener{
            val newTitle = binding.updatetitleEditText.text.toString()
            val newContent = binding.updatecontentEditText.text.toString()

            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)

            Toast.makeText(this, "Catatan Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
            finish()

        }


    }
}