package com.example.toro.android.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemberDao {
    @Insert
    fun create(member: Member)

    @Delete
    fun delete(member: Member)

    @Query("select * from Member")
    fun all() : List<Member>
}