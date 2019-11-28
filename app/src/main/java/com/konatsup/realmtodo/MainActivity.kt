package com.konatsup.realmtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import io.realm.Sort

class MainActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        deleteAll()

        val taskList = readAll()

        if (taskList.isEmpty()) {
            createDummyData()
        }

        val adapter =
            TaskAdapter(this, taskList, object : TaskAdapter.OnItemClickListener {
                override fun onItemClick(item: Task) {
                    // クリックした処理を書く
                    Toast.makeText(applicationContext, item.content + "を削除しました", Toast.LENGTH_SHORT)
                        .show()
                    delete(item)
                }
            }, true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {
        for (i in 0..10) {
            create(R.drawable.ic_launcher_background, "やること $i")
        }
    }

    fun create(imageId: Int, content: String) {
        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.imageId = imageId
            task.content = content
        }
    }

    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }

    fun update(id: String, content: String) {
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.content = content
        }
    }

    fun update(task: Task, content: String) {
        realm.executeTransaction {
            task.content = content
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun delete(task: Task) {
        realm.executeTransaction {
            task.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

}
