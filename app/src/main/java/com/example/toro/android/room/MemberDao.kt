package com.example.toro.android.room

import androidx.room.*

@Dao
interface MemberDao {
    @Insert
    fun create(member: Member)

    @Update
    fun update(member: Member)

    @Delete
    fun delete(member: Member)

    @Query("select * from Member")
    fun all() : List<Member>

    @Query("select * from Member where id=:memberId")
    fun one(memberId: Int) : Member
}