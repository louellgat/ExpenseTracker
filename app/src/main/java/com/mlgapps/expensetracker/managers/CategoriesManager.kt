package com.mlgapps.expensetracker.managers

import com.mlgapps.expensetracker.database.objects.Category
import com.mlgapps.expensetracker.database.objects.CategoryType
import io.realm.Realm
import io.realm.kotlin.where

object CategoriesManager {
    private val DEFAULT_CATEGORIES: Array<Category> by lazy {
        arrayOf(
            Category(0, "Allowance", CategoryType.INCOME.ordinal),
            Category(1, "Salary", CategoryType.INCOME.ordinal),
            Category(2,"Bonus", CategoryType.INCOME.ordinal),
            Category(3, "Food", CategoryType.EXPENSE.ordinal),
            Category(4, "Health", CategoryType.EXPENSE.ordinal),
            Category(5, "Transportation", CategoryType.EXPENSE.ordinal),
            Category(6, "Treat", CategoryType.EXPENSE.ordinal),
            Category(7, "Gift", CategoryType.EXPENSE.ordinal),
            Category(8, "Bills", CategoryType.EXPENSE.ordinal),
            Category(9, "Car", CategoryType.EXPENSE.ordinal),
        )
    }

    fun addDefaults() {
        val realm = Realm.getDefaultInstance()
        val categories = realm.where<Category>().findAll()
        if (categories.size <= 0) {
            realm.beginTransaction()
            DEFAULT_CATEGORIES.forEach { c ->
                realm.copyToRealm(c)
            }
            realm.commitTransaction()
        }
    }
}