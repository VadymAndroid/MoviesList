package com.crysberry.ui.bases.action

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean

interface WithRefresh {

    val asRefreshable: Refreshable
}

abstract class Refreshable {

    val refreshing = ObservableBoolean()

    init {
        refreshing.addOnPropertyChangedCallback(OnRefreshedCallback())
    }

    protected abstract fun onRefresh()

    fun refresh() {
        refreshing.set(true)
    }

    private inner class OnRefreshedCallback : Observable.OnPropertyChangedCallback() {

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (refreshing.get()) {
                onRefresh()
            }
        }
    }
}
