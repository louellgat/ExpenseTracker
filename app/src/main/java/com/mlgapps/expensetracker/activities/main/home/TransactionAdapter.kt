package com.mlgapps.expensetracker.activities.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mlgapps.expensetracker.R
import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.objects.Category
import java.util.ArrayList

typealias ItemClickListener = ((Int) -> Unit)?
class TransactionAdapter(private val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var listener: ItemClickListener = null
    private val itemList: MutableList<Any> = mutableListOf()

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }
    class AccountViewHolder(itemView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.name_text)
        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener(position)
                    }
                }
            }
        }
    }

    class CategoryViewHolder(itemView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.name_text)
        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener(position)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v : View = LayoutInflater.from(context).inflate(R.layout.cell_layout,parent,false)
        return when(viewType){
            ACCOUNT_VIEW_TYPE -> AccountViewHolder(v,listener)
            else -> CategoryViewHolder(v,listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: loading")
        when(holder.itemViewType){
            ACCOUNT_VIEW_TYPE -> {
                val item = itemList[position] as Account
                (holder as AccountViewHolder).nameText.text = item.name
            }
            else -> {
                val item = itemList[position] as Category
                (holder as CategoryViewHolder).nameText.text = item.name
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if(itemList is Account) ACCOUNT_VIEW_TYPE else CATEGORY_VIEW_TYPE
    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    fun clearList(){
        itemList.clear()
    }

    fun add(item : Any){
        itemList.add(item)
    }
    companion object{
        private val TAG = TransactionAdapter::class.java.simpleName
        const val ACCOUNT_VIEW_TYPE = 0
        const val CATEGORY_VIEW_TYPE = 1
    }
}