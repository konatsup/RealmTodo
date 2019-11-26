package com.konatsup.realmtodo

import android.app.Application
import io.realm.Realm

class RealmTodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}