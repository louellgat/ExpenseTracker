package com.mlgapps.expensetracker.activities.main.transaction

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
import java.lang.Exception

typealias ItemClickListener = ((Int) -> Unit)?
class TransactionAdapter(private val context : Context) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>(){
    private var itemClickListener: ItemClickListener = null
    var viewType : Int = ACCOUNT_VIEW_TYPE
    var accounts: MutableList<Account> = mutableListOf()
    var categories: MutableList<Category> = mutableListOf()


    fun setItemClickListener(listener: ItemClickListener) {
        this.itemClickListener = listener
    }


    class TransactionViewHolder(itemView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.name_text)
        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        Log.d(TAG, "Selected: $position")
                        listener(position)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val v : View = LayoutInflater.from(context).inflate(R.layout.cell_layout,parent,false)
        return TransactionViewHolder(v,itemClickListener)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        try{
            when(viewType){
                ACCOUNT_VIEW_TYPE -> {
                    val item = accounts[position]
                    Log.d(TAG, "onBindViewHolder: accounts pos $position name ${item.name}")
                    holder.nameText.text = item.name
                }
                else -> {
                    val item = categories[position]
                    Log.d(TAG, "onBindViewHolder: categories pos $position name ${item.name}")
                    holder.nameText.text = item.name
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return if(viewType == ACCOUNT_VIEW_TYPE) accounts.size else categories.size
    }


    companion object{
        private val TAG = TransactionAdapter::class.java.simpleName
        const val ACCOUNT_VIEW_TYPE = 0
        const val CATEGORY_VIEW_TYPE = 1
    }
}