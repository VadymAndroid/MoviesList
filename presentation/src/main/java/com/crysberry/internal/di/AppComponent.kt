package com.crysberry.internal.di

import android.content.Context
import com.crysberry.data.di.NetworkModule
import com.crysberry.Application
import com.crysberry.internal.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    ViewModelModule::class,
    ImplModule::class,
    RouterModule::class,
    SchedulersModule::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: Application)
}