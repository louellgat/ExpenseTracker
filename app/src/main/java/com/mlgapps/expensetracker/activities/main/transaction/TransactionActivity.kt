package com.mlgapps.expensetracker.activities.main.transaction

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mlgapps.expensetracker.R
import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.objects.Category
import io.realm.Realm
import io.realm.kotlin.where
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class TransactionActivity : AppCompatActivity() {
    private enum class Tabs{
        TRANSFER,
        INCOME,
        EXPENSE
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var selectedTab : Tabs
    private lateinit var timeText : TextView
    private lateinit var dateText : TextView
    private lateinit var categoryText : TextView
    private lateinit var accountText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        val realm = Realm.getDefaultInstance()
        selectedTab = Tabs.values()[intent.extras?.getInt(EXTRAS_SELECTEDTAB)?:1]
        accountText = findViewById<TextView>(R.id.accountText).apply {
            setOnClickListener {
                UIUtil.hideKeyboard(this@TransactionActivity)
                loadItems(true)
            }
        }
        categoryText = findViewById<TextView>(R.id.categoryText).apply {
            setOnClickListener {
                UIUtil.hideKeyboard(this@TransactionActivity)
                loadItems(false)
            }
        }
        timeText = findViewById<TextView>(R.id.timeText).apply {
            setOnClickListener { UIUtil.hideKeyboard(this@TransactionActivity) }
        }
        dateText = findViewById<TextView>(R.id.dateText).apply {
            setOnClickListener { UIUtil.hideKeyboard(this@TransactionActivity) }
        }
        val tabLayout = findViewById<TabLayout>(R.id.typeTab).apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val color = when(selectedTabPosition){
                        0 -> Color.YELLOW
                        1 -> Color.GREEN
                        else -> Color.RED
                    }
                    setSelectedTabIndicatorColor(color)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        adapter = TransactionAdapter(this).apply {
            accounts = realm.copyFromRealm(realm.where<Account>().findAll())
            categories = realm.copyFromRealm(realm.where<Category>().findAll())

        }
        loadItems(false)
        realm.close()
    }

    private fun loadItems(isAccount: Boolean = true) {
        recyclerView.adapter = adapter.apply {
            viewType = if(isAccount){
                TransactionAdapter.ACCOUNT_VIEW_TYPE
            } else{
                TransactionAdapter.CATEGORY_VIEW_TYPE
            }
            adapter.setItemClickListener { i->
                if(viewType == TransactionAdapter.ACCOUNT_VIEW_TYPE){
                    accountText.text = accounts[i].name
                } else{
                    categoryText.text = categories[i].name
                }
            }
        }
    }

    companion object{
        val EXTRAS_SELECTEDTAB = "selectedTab"
        private val TAG = TransactionActivity::class.java.simpleName
    }
}