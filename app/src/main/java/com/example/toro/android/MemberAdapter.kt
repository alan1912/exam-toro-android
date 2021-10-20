package com.example.toro.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toro.android.constant.Genders
import com.example.toro.android.room.Member

class MemberAdapter(private val members: ArrayList<Member>, private var listener: OnAdapterListener) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        val memberNameTextView: TextView
        val memberAgeTextView: TextView
        val memberSexTextView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            memberNameTextView = view.findViewById(R.id.member_name)
            memberAgeTextView = view.findViewById(R.id.member_age)
            memberSexTextView = view.findViewById(R.id.member_sex)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_member, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.memberNameTextView.text = members[position].name
        holder.memberAgeTextView.text = members[position].age.toString()
        holder.memberSexTextView.text = getGender(members[position].gender)
        holder.view.setOnClickListener {
            listener.onUpdate(members[position])
        }
    }

    override fun getItemCount(): Int = members.size

    fun getItem(pos: Int): Member? {
        return members.getOrNull(pos)
    }

    fun setData(newList: List<Member>) {
        members.clear()
        members.addAll(newList)
    }

    private fun getGender(sex: Int) : String {
        when (sex) {
            Genders.SEX_MALE -> {
                return "MALE"
            }
            Genders.SEX_FEMALE -> {
                return "FEMALE"
            }
        }
        return "None"
    }

    interface OnAdapterListener {
        fun onUpdate(member: Member)
    }
}