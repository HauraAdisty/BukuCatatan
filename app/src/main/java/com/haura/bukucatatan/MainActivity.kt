package com.haura.bukucatatan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.haura.bukucatatan.Adapter.NoteAdapter
import com.haura.bukucatatan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var db : NoteDatabaseHelper
    private lateinit var noteAdapter: NoteAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener{
            val intent = Intent( this, AddNoteActivity::class.java)
            startActivity(intent)
        }
        db = NoteDatabaseHelper(this)
        noteAdapter= NoteAdapter(db.getAllNotes(), this)

        binding.notesRecycle.layoutManager = LinearLayoutManager(this)
        binding.notesRecycle.adapter = noteAdapter
    }
    override fun onResume() {
        super.onResume()
        val  notes = db.getAllNotes()
        Log.d("MainActivity","Notes count ${notes.size}")
        noteAdapter.refreshData(notes)
    }
}