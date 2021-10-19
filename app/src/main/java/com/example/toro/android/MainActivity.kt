package com.example.toro.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toro.android.add.AddActivity
import com.example.toro.android.room.Member
import com.example.toro.android.room.MemberDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val db by lazy { MemberDatabase(this) }
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            memberAdapter.setData(db.memberDao().all())
            withContext(Dispatchers.Main) {
                memberAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_add -> {
                val add = Intent(this, AddActivity::class.java)
                startActivity(add)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        supportActionBar?.setTitle(R.string.menu_list_title)
    }

    private fun setUpRecyclerView() {
        memberAdapter = MemberAdapter(
            arrayListOf(),
            object: MemberAdapter.OnAdapterListener {
                override fun onUpdate(member: Member) {
                    update(member)
                }
                override fun onDelete(member: Member) {
                    deleteAlert(member)
                }
            }
        )

        val recyclerView: RecyclerView = findViewById(R.id.member_list)
//        recyclerView.addItemDecoration(SimpleDividerItemDecoration(this))
        recyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = memberAdapter
        }
    }

    private fun update(member: Member) {
        println(member.id)
        startActivity(
            Intent(this, AddActivity::class.java)
                .putExtra("member_id", member.id)
        )
    }

    private fun deleteAlert(member: Member){
        AlertDialog.Builder(this)
            .setTitle(R.string.btn_delete)
            .setMessage("${getString(R.string.btn_delete)} ${member.name}?")
            .setNegativeButton(R.string.btn_cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton(R.string.btn_ok) { dialogInterface, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.memberDao().delete(member)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
            .show()
    }
}