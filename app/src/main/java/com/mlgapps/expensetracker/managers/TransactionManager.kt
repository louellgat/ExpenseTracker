package com.mlgapps.expensetracker.managers

import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.objects.CategoryType
import com.mlgapps.expensetracker.database.objects.Transaction

object TransactionManager {
    fun transact(transaction : Transaction){
        if(transaction.category!!.type == CategoryType.EXPENSE.ordinal){
            transaction.addExpense()
        }else if(transaction.category!!.type == CategoryType.INCOME.ordinal){

        }
    }

    private fun Transaction.addExpense(){

    }
}