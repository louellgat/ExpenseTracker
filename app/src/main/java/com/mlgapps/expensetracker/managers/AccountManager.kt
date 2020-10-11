package com.mlgapps.expensetracker.managers

import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.objects.Category
import com.mlgapps.expensetracker.database.objects.CategoryType
import io.realm.Realm
import io.realm.kotlin.where

object AccountManager {
    private val DEFAULT_ACCOUNTS: Array<Account> by lazy {
        arrayOf(
            Account(0, "Cash"),
            Account(1, "Card"),
        )
    }

    fun addDefaults() {
        val realm = Realm.getDefaultInstance()
        val categories = realm.where<Account>().findAll()
        if (categories.size <= 0) {
            realm.beginTransaction()
            DEFAULT_ACCOUNTS.forEach { c ->
                realm.copyToRealm(c)
            }
            realm.commitTransaction()
        }
    }
}