package com.example.toro.android.add

import android.app.AlertDialog
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
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var genderSpinner: Spinner

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val memberId = intent.getIntExtra("member_id", 0)

        nameTextView = findViewById(R.id.addMemberName)
        ageTextView = findViewById(R.id.addMemberAge)
        genderSpinner = findViewById(R.id.addMemberGender)

        val adapter = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
        genderSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
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

        val sendBtn: Button = findViewById(R.id.send_btn)
        sendBtn.setOnClickListener {
            when {
                nameTextView.text.isBlank() -> {
                    AlertDialog.Builder(this@AddActivity)
                        .setTitle(R.string.error_title)
                        .setMessage(R.string.error_name_empty)
                        .setPositiveButton(R.string.btn_close, null)
                        .show()
                }
                ageTextView.text.isBlank() -> {
                    AlertDialog.Builder(this@AddActivity)
                        .setTitle(R.string.error_title)
                        .setMessage(R.string.error_age_empty)
                        .setPositiveButton(R.string.btn_close, null)
                        .show()
                }
                else -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (memberId == 0) {
                            db.memberDao().create(
                                Member(
                                    name = nameTextView.text.toString(),
                                    age = if (ageTextView.text.toString() != "") ageTextView.text.toString().toInt() else 0,
                                    gender = gender
                                )
                            )
                        } else {
                            db.memberDao().update(
                                Member(
                                    id = memberId,
                                    name = nameTextView.text.toString(),
                                    age = if (ageTextView.text.toString() != "") ageTextView.text.toString().toInt() else 0,
                                    gender = gender
                                )
                            )
                        }
                        finish()
                    }
                }
            }
        }

        if (memberId == 0) {
            supportActionBar?.setTitle(R.string.menu_add_title)
            sendBtn.text = getString(R.string.menu_add_title)
        } else {
            supportActionBar?.setTitle(R.string.menu_edit_title)
            sendBtn.text = getString(R.string.menu_edit_title)
            getMember(memberId)
        }
    }

    private fun getMember(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val member = db.memberDao().one(id)

            nameTextView.text = member.name
            ageTextView.text = member.age.toString()
            genderSpinner.setSelection(member.gender)
        }
    }
}