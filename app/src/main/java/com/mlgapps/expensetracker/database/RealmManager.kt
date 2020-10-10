package com.louellgat.ecereviewmaster.database

import io.realm.Realm
import io.realm.RealmConfiguration


class RealmManager {
    private val realms: ThreadLocal<Realm> = ThreadLocal()
    fun openRealm(realmConfiguration: RealmConfiguration): Realm {
        var realm = realms.get()
        check(!(realm != null && !realm.isClosed)) { "Realm is already open" }
        realm = Realm.getInstance(realmConfiguration)
        realms.set(realm)
        return realm
    }

    val realm: Realm
        get() = realms.get() ?: throw IllegalStateException("There is no open Realm on this thread")

    fun closeRealm() {
        val realm = realms.get() ?: throw IllegalStateException("No Realm found to close")
        if (!realm.isClosed) {
            realm.close()
        }
        realms.set(null)
    }

    fun startRealm(realmConfiguration: RealmConfiguration,openedRealm : Realm.()->Unit){
        openRealm(realmConfiguration).openedRealm()
        closeRealm()
    }

}