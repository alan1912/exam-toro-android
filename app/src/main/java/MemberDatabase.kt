package com.example.toro.android

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Member::class), version = 1)
abstract class MemberDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
}