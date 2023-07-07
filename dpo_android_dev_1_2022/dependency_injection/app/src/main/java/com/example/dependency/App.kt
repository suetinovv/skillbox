package com.example.dependency

import android.app.Application
import com.example.dependency.di.DaggerComponent
import com.example.dependency.di.DaggerDaggerComponent
import com.example.dependency.factory.BicycleFactory
import com.example.dependency.factory.FrameFactory
import com.example.dependency.factory.FrameFactoryImpl
import com.example.dependency.model.WheelDealer
import com.example.dependency.model.WheelDealerImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    companion object {
        lateinit var component: DaggerComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDaggerComponent.builder().build()

        startKoin {
            modules(
                module {
                    single<FrameFactory> { FrameFactoryImpl() }
                    single<WheelDealer> { WheelDealerImpl }
                    single<BicycleFactory> { BicycleFactory(get(), get()) }
                }
            )
        }
    }
}