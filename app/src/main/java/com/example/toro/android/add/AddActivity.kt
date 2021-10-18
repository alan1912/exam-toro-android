package com.example.toro.android.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.toro.android.R

class AddActivity : AppCompatActivity() {
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
    }
}