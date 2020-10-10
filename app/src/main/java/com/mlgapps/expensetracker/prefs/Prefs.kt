package com.mlgapps.expensetracker.prefs

import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.AbstractPreferences

object Prefs {
    private lateinit var preferences: SharedPreferences

    fun initialize(context : Context){
        preferences = context.getSharedPreferences("mprefs", Context.MODE_PRIVATE)
    }
}