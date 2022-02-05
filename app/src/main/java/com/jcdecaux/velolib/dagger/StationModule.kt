package com.jcdecaux.velolib.dagger

/**
 * Created by hientx on 11/16/2017.
 */

import com.jcdecaux.velolib.api.StationApiInterface
import com.jcdecaux.velolib.config.Constants
import com.jcdecaux.velolib.presenter.StationDetailPresenter
import com.jcdecaux.velolib.presenter.StationPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import javax.inject.Singleton

@Module
class StationModule {

    @Provides
    @Singleton
    fun provideStationPresenter(): StationPresenter {
        return StationPresenter()
    }

    @Provides
    @Singleton
    fun provideStationDetailPresenter(): StationDetailPresenter {
        return StationDetailPresenter()
    }

    @Provides
    @Singleton
    internal fun provideStationApiInterface(): StationApiInterface {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        return retrofit.create(StationApiInterface::class.java)
    }
}