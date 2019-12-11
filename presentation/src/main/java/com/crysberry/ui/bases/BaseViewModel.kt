package com.crysberry.ui.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.crysberry.AppEvents
import com.cryssberry.core.internal.LocalStorage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseViewModel : ViewModel(), AppEvents.Listener {

    @Inject lateinit var events: AppEvents

    @Inject lateinit var storage: LocalStorage
    @Inject lateinit var router: Router

    protected val disposables = CompositeDisposable()

    @Inject
    fun bindToAppEvents() {
        events.addListener(this)
    }

    @CallSuper
    override fun onCleared() {
        disposables.dispose()
        events.removeListener(this)
    }

    open class ProgressMode(
        val processStart: BaseViewModel.() -> Unit,
        val processTerminate: BaseViewModel.() -> Unit
    ) {
        object PRE_LOADER : ProgressMode(
            processStart = {},
            processTerminate = { }
        )
    }


    private interface ObserverBehavior : Disposable {

        val progressMode: ProgressMode get() = ProgressMode.PRE_LOADER

        val messageAfterCompletion get() = 0
        val exitAfterCompletion get() = false

        val owner: BaseViewModel

        fun onMapError(e: Throwable) = 0

        fun onHandleError(e: Throwable) = false

        fun ObserverBehavior.processStart() {
            owner.disposables.add(this)
            progressMode.processStart.invoke(owner)
        }
        fun ObserverBehavior.processError(e: Throwable) {
            processTerminate()
            showAlertIfNotHandled(e)
        }

        fun ObserverBehavior.processCompletion() {
            processTerminate()
        }

        fun ObserverBehavior.processTerminate() {
            progressMode.processTerminate(owner)
        }

        fun ObserverBehavior.showAlertIfNotHandled(e: Throwable) {
            when {
                !onHandleError(e) -> {
                }
            }
        }
    }

    protected open inner class BaseSingleObserver<T>(
        override val messageAfterCompletion: Int = 0,
        override val exitAfterCompletion: Boolean = false
    ) : DisposableSingleObserver<T>(),
        ObserverBehavior {

        final override val owner get() = this@BaseViewModel

        @CallSuper
        override fun onStart() {
            processStart()
        }

        @CallSuper
        override fun onSuccess(t: T) {
            processCompletion()
        }

        @CallSuper
        override fun onError(e: Throwable) {
            processError(e)
        }
    }
}
