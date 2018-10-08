package noro.me.pixacloneandroid

import android.app.Application
import io.realm.Realm

class PixaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}