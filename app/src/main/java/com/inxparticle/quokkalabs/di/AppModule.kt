package com.inxparticle.quokkalabs.di

import com.inxparticle.quokkalabs.main.DefaultMainRepository
import com.inxparticle.quokkalabs.main.MainRepository
import com.inxparticle.quokkalabs.utils.DispatcherProvider
import com.inxparticle.quokkalabs.data.QuokkaLabsApi
import com.inxparticle.quokkalabs.utils.ApplicationConstant.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideCurrencyApi() : QuokkaLabsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuokkaLabsApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: QuokkaLabsApi): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() =  Dispatchers.IO
        override val default: CoroutineDispatcher
            get() =  Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }



}