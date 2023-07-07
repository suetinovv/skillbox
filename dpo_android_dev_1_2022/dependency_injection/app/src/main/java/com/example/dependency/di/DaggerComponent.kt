package com.example.dependency.di

import com.example.dependency.factory.BicycleFactory
import com.example.dependency.factory.FrameFactory
import com.example.dependency.factory.FrameFactoryImpl
import com.example.dependency.model.WheelDealer
import com.example.dependency.model.WheelDealerImpl
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [BicycleModule::class])
interface DaggerComponent {
    fun getBicycleFactory(): BicycleFactory
}

@Module
class BicycleModule {
    @Provides
    fun provideFrameFactory(): FrameFactory {
        return FrameFactoryImpl()
    }

    @Provides
    @Singleton
    fun provideWheelDealer(): WheelDealer {
        return WheelDealerImpl
    }

    @Provides
    fun provideBicycleFactory(
        frameFactory: FrameFactory,
        wheelDealer: WheelDealer
    ): BicycleFactory {
        return BicycleFactory(frameFactory, wheelDealer)
    }
}