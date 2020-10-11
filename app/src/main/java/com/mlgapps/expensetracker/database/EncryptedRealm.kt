package com.louellgat.ecereviewmaster.database

import android.content.Context
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mlgapps.expensetracker.R
import com.mlgapps.expensetracker.database.DatabaseMigration
import com.mlgapps.expensetracker.database.objects.Account
import com.mlgapps.expensetracker.database.EncryptionHelper
import com.mlgapps.expensetracker.database.objects.Category
import com.mlgapps.expensetracker.database.objects.Transaction
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.annotations.RealmModule

object EncryptedRealm {
    @RealmModule(classes = [Account::class, Category::class, Transaction::class])
    class MainModule

    private const val REMINDER_NAME = "dbm.realm"
    private lateinit var mainInstance: Realm
    val MAIN_CONFIG: RealmConfiguration.Builder by lazy {
        RealmConfiguration.Builder().name(REMINDER_NAME)
    }

    @JvmStatic
    fun realmWithEncryptionInit(context: Context) {
        val mainEncryptHelper = EncryptionHelper.initHelper(
            context, context.resources.getString(
                R.string.app_name
            )
        )
        Realm.init(context)
        val mainConfig = MAIN_CONFIG
            .schemaVersion(0)
            .encryptionKey(mainEncryptHelper.encryptKey)
            .modules(MainModule())
            .migration(DatabaseMigration())
            .compactOnLaunch()
            .build()
        Realm.setDefaultConfiguration(mainConfig)
        try {
            mainInstance = Realm.getDefaultInstance()
            mainInstance.close()
        } catch (e: Exception) {
            Toast.makeText(context, "Error detected. Restarting..", Toast.LENGTH_SHORT).show();
            Realm.deleteRealm(Realm.getDefaultConfiguration());
            ProcessPhoenix.triggerRebirth(context);
        }
    }

}