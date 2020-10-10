package com.mlgapps.expensetracker.database.objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Account(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var value: Float = 0f
) : RealmObject() {

    var desc: String = ""
}