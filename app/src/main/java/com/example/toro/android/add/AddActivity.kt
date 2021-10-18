package com.example.toro.android.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toro.android.R
import com.example.toro.android.room.Member
import com.example.toro.android.room.MemberDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private val db by lazy { MemberDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setupView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setupView() {
        getSupportActionBar()?.setTitle(R.string.menu_add_title)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val addBtn: Button = findViewById(R.id.add_btn)
        addBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val name: TextView = findViewById(R.id.addMemberName)
                val age: TextView = findViewById(R.id.addMemberAge)
                db.memberDao().create(
                    Member(
                        name.text.toString(),
                        20,
                        0
                    )
                )
                finish()
            }
        }
    }
}