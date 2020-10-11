package com.mlgapps.expensetracker.database

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

class DatabaseMigration : RealmMigration{
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion
        val schema = realm.schema

        if(oldVersion == 0L){
            schema.create("Category")
                .addField("id",Int::class.javaPrimitiveType,FieldAttribute.PRIMARY_KEY)
                .addField("name",String::class.java)
                .addField("type",Int::class.javaPrimitiveType)
                .addField("desc",String::class.java)
            schema.create("Account")
                .addField("id",Int::class.javaPrimitiveType,FieldAttribute.PRIMARY_KEY)
                .addField("name",String::class.java)
                .addField("value",Float::class.java)
                .addField("desc",String::class.java)
            schema.create("Transaction")
                .addField("id",Int::class.javaPrimitiveType,FieldAttribute.PRIMARY_KEY)
                .addField("name",String::class.java)
                .addRealmObjectField("account",schema.get("Account"))
                .addRealmObjectField("category",schema.get("Category"))
                .addField("time",String::class.java)
                .addField("date",String::class.java)
            oldVersion++
        }
    }

}