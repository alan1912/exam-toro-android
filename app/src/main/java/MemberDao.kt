package com.example.toro.android

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {
    @Insert
    fun add(member: Member)

    @Query("select * from Member")
    fun getAll() : List<Member>
}