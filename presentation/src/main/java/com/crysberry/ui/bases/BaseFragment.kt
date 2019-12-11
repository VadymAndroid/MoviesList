package com.crysberry.ui.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.crysberry.AppEvents
import com.crysberry.ui.baseActivity
import com.crysberry.ui.bases.action.WithRefresh
import com.crysberry.ui.bindViewModel
import com.cryssberry.core.internal.LocalStorage
import dagger.android.support.DaggerFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment(), AppEvents.Listener {

    @get:LayoutRes
    abstract val layoutId: Int

    @StringRes
    open val toolbarTitle: Int = 0

    @Inject lateinit var events: AppEvents

    @Inject lateinit var storage: LocalStorage
    @Inject lateinit var router: Router

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected open val scrollEnabled get() = arguments?.getBoolean(ARG_SCROLL_ENABLED) ?: false

    protected open val exitConfirmationMessage = 0

    override fun onResume() {
        super.onResume()

        if (toolbarTitle != 0) {
            activity!!.setTitle(toolbarTitle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        onCreateContentView(inflater, container, savedInstanceState)
            .let {
                when {
                    scrollEnabled -> NestedScrollView(context!!).apply { addView(it) }
                    else -> it
                }
            }

    protected open fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    companion object {
        const val ARG_SCROLL_ENABLED = "ARG_SCROLL_ENABLED"
    }
}

sealed class BaseBindingFragment<VDB : ViewDataBinding> : BaseFragment() {

    abstract val viewModel: Any

    protected lateinit var binding: VDB

    private val withUi get() = ::binding.isInitialized

    @CallSuper
    override fun onCreateContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false)
        .also { binding = it }
        .bindViewModel(viewModel)

    override fun onDestroyView() {
       // binding.unbind()
        super.onDestroyView()
    }

    protected fun invalidateUi() {
        if (withUi) {
            binding.invalidateAll()
        }
    }
}

abstract class ViewModelFragment<VM : BaseViewModel, VDB : ViewDataBinding>
    : BaseBindingFragment<VDB>() {

    abstract val viewModelClass: Class<VM>

    final override val viewModel: VM by lazy(::makeViewModel)

    private val useActivityScope get() = arguments?.getBoolean(ARG_USE_ACTIVITY_SCOPE) ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: (viewModel as? WithRefresh)?.asRefreshable?.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =  DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false)
    .also { binding = it }
    .bindViewModel(viewModel)


    private fun makeViewModel() =
        when {
            useActivityScope -> ViewModelProviders.of(baseActivity, viewModelFactory)
            else -> ViewModelProviders.of(this, viewModelFactory)
        }.get(viewModelClass)

    companion object {
        const val ARG_USE_ACTIVITY_SCOPE = "ARG_USE_ACTIVITY_SCOPE"
    }
}
