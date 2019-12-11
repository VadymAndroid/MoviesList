package com.crysberry.internal.di.modules

import com.crysberry.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsModule {


    @ContributesAndroidInjector
    fun mainFragmentInjector(): MainFragment

}