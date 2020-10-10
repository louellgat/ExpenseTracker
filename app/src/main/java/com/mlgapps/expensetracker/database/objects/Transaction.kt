package com.mlgapps.expensetracker.database.objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Transaction(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
) : RealmObject() {
    var account : Account? = null
    var category : Category? = null
    var time : String? = null
    var date : String? = null
}