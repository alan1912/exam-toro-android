package com.example.toro.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Member(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var age: Int,
    var gender: Int) {
}