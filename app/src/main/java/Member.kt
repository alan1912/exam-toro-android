package com.example.toro.android

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Member(
    var name: String,
    var age: Int,
    var sex: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0) {
}