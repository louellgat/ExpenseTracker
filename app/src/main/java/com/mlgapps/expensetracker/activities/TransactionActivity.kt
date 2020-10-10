package com.mlgapps.expensetracker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlgapps.expensetracker.R
import com.mlgapps.expensetracker.activities.main.home.TransactionAdapter
import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.objects.Category
import io.realm.Realm
import io.realm.kotlin.where

class TransactionActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = TransactionAdapter(this)
        recyclerView.adapter = adapter
        loadAdapter(false)
    }

    private fun loadAdapter(isAccount: Boolean = true) {
        adapter.clearList()
        val realm = Realm.getDefaultInstance()
        val list = realm.copyFromRealm(
            if (isAccount) {
                realm.where<Account>().findAll() as MutableList<Account>
            } else {
                realm.where<Category>().findAll() as MutableList<Category>
            }
        )
        Log.d(TAG, "loadAdapter: loaded ${list.size}")
        val iterator = list.iterator()
        while(iterator.hasNext()) {
            adapter.add(iterator.next())
        }
        adapter.notifyDataSetChanged()
        realm.close()
    }

    companion object{
        private val TAG = TransactionActivity::class.java.simpleName
    }
}