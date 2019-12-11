package com.crysberry.internal.impls

import com.cryssberry.core.Results
import com.cryssberry.core.internal.LocalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorageImpl @Inject constructor() : LocalStorage {
    override var movies: LocalStorage.ExpirableValue<List<Results>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var onBoardingShown by storageProperty(false)
}