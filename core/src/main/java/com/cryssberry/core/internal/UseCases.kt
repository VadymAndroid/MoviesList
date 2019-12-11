package com.cryssberry.core.internal

import io.reactivex.*
import io.reactivex.disposables.Disposables
import io.reactivex.observers.*
import javax.inject.Inject
import kotlin.reflect.KMutableProperty1

sealed class BaseUseCase {

    @Inject
    lateinit var networkFacade: NetworkFacade
    @Inject
    lateinit var localStorage: LocalStorage

    @Inject
    lateinit var schedulers: Map<SchedulerType, Scheduler>

    protected var disposable = Disposables.empty()

    fun cancel() {
        disposable.dispose()
    }


    enum class SchedulerType {
        WORK,
        WORK_RESULT
    }

    enum class ReloadCriteria {
        IF_EXPIRED, FORCED
    }

    protected fun <T> lazyRepo(
        cache: KMutableProperty1<LocalStorage, LocalStorage.ExpirableValue<T>>,
        update: (NetworkFacade) -> Single<T>
    ) = LazyRepo(
        localStorage,
        networkFacade,
        cache,
        update
    )
}

sealed class SingleUseCase<in P, R> : BaseUseCase() {

    protected abstract fun buildSingle(params: P): Single<R>

    protected fun doExecute(params: P, o: DisposableSingleObserver<R>) {
        cancel()
        disposable = o
        buildSingle(params)
            .subscribeOn(schedulers[SchedulerType.WORK])
            .observeOn(schedulers[SchedulerType.WORK_RESULT])
            .subscribe(o)
    }

    abstract class ParametrizedUseCase<in P : Any, R> : SingleUseCase<P, R>() {

        fun execute(params: P, o: DisposableSingleObserver<R>) {
            doExecute(params, o)
        }
    }

    abstract class SimpleUseCase<R> : SingleUseCase<Nothing?, R>() {

        fun execute(o: DisposableSingleObserver<R>) {
            doExecute(null, o)
        }
    }
}

