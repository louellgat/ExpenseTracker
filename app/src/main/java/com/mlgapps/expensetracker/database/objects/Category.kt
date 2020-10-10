package com.mlgapps.expensetracker.database.objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Category(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var type: Int = CategoryType.EXPENSE.ordinal
) : RealmObject() {
    var desc: String = ""

}