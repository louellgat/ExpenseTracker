package com.mlgapps.expensetracker

import android.app.Application
import com.louellgat.ecereviewmaster.database.EncryptedRealm
import com.mlgapps.expensetracker.managers.CategoriesManager
import com.mlgapps.expensetracker.prefs.Prefs

class ExpenseTrackerApp : Application(){

    override fun onCreate() {
        super.onCreate()
        EncryptedRealm.realmWithEncryptionInit(this)
        Prefs.initialize(applicationContext)
        CategoriesManager.addDefaultCategories()

    }

}