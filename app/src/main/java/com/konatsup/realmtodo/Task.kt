package com.konatsup.realmtodo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Task(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var imageId: Int = 0,
    open var content: String = "",
    open var createdAt: Date = Date(System.currentTimeMillis())
) : RealmObject()
