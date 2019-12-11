package com.crysberry.internal.di.modules

import androidx.lifecycle.ViewModelProvider
import com.cryssberry.core.internal.LocalStorage
import com.cryssberry.core.internal.NetworkFacade
import com.crysberry.data.impl.NetworkFacadeImpl
import com.crysberry.internal.impls.*
import dagger.Binds
import dagger.Module

@Module
interface ImplModule {

    @Binds fun provideLocalStorage(localStorage: LocalStorageImpl): LocalStorage
    @Binds fun provideNetworkFacade(networkFacade: NetworkFacadeImpl): NetworkFacade
    @Binds fun bindViewModelFactory(factory: ViewModelFactoryImpl): ViewModelProvider.Factory

}