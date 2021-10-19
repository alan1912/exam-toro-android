package com.example.toro.android.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.toro.android.R
import com.example.toro.android.room.Member
import com.example.toro.android.room.MemberDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {
    private val db by lazy { MemberDatabase(this) }
    private var gender: Int = 0

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
        supportActionBar?.setTitle(R.string.menu_add_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spinner: Spinner = findViewById(R.id.addMemberGender)
        val adapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gender = id.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val addBtn: Button = findViewById(R.id.add_btn)
        addBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val name: TextView = findViewById(R.id.addMemberName)
                val age: TextView = findViewById(R.id.addMemberAge)
                db.memberDao().create(
                    Member(
                        name.text.toString(),
                        if (age.text.toString() != "") age.text.toString().toInt() else 0,
                        gender
                    )
                )
                finish()
            }
        }
    }
}