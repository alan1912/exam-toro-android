package com.example.toro.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.toro.android.add.AddActivity
import com.example.toro.android.room.Member
import com.example.toro.android.room.MemberDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { Room.databaseBuilder(this, MemberDatabase::class.java, "member.db").build() }
    lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupList()
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
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_add -> {
                System.out.println("click add")

                val add = Intent(this, AddActivity::class.java)
                startActivity(add)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        getSupportActionBar()?.setTitle(R.string.menu_list_title)
    }

    private fun setupList() {
//        arrayListOf(
//            Member("Wayne", 20, 1),
//            Member("David", 20, 0),
//            Member("Alan", 20, 1),
//        )
        memberAdapter = MemberAdapter(
            arrayListOf(),
            object: MemberAdapter.OnAdapterListener {
                override fun onClick(member: Member) {
                }
                override fun onUpdate(member: Member) {
                }
                override fun onDelete(member: Member) {
                    deleteAlert(member)
                }
            }
        )

        val list: RecyclerView = findViewById(R.id.member_list)
        list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = memberAdapter
        }
    }

    private fun deleteAlert(member: Member){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("刪除")
            setMessage("確定刪除 ${member.name}?")
            setNegativeButton("取消") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("確定") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.memberDao().delete(member)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }

        dialog.show()
    }
}