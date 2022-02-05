package com.jcdecaux.velolib.dagger

/**
 * Created by txhien on 11/16/2017.
 */

import android.app.Application
import com.jcdecaux.velolib.view.activities.MainActivity
import com.jcdecaux.velolib.view.activities.StationActivityDetail
import dagger.Component
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

class AppDelegate : Application() {

    var injector: AppInjector? = null

    @Singleton
    @Component(modules = arrayOf(StationModule::class))
    interface AppInjector {

        fun inject(activity: MainActivity)
        fun inject(stationDetailActivity: StationActivityDetail)

    }

    override fun onCreate() {
        super.onCreate()
        injector = DaggerAppDelegate_AppInjector.builder().build()

        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}