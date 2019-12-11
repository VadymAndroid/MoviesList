package com.crysberry.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.crysberry.R
import com.crysberry.databinding.FragmentMainBinding
import com.crysberry.ui.adapters.MoviesAdapter
import com.crysberry.ui.bases.BaseViewModel
import com.crysberry.ui.bases.ViewModelFragment
import com.cryssberry.core.Results
import com.cryssberry.core.interactors.MoviesUseCase
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : ViewModelFragment<MainViewModel, FragmentMainBinding>() {

    override val layoutId: Int get() = R.layout.fragment_main
    override val viewModelClass  = MainViewModel::class.java
    var results: ArrayList<Results> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val rootView = inflater.inflate(layoutId, container, false)

        val rv = rootView?.findViewById<RecyclerView>(R.id.listMovies)

        viewModel.onRefresh()


        viewModel.getListMovies().observe(this, Observer { item ->

            when {
                item.isNotEmpty() -> {
                    results = ArrayList(item)
                    val adapter = MoviesAdapter(results)
                    rv?.adapter = adapter
                }
                else -> tvInternet.visibility = View.VISIBLE
            }
        })

        return rootView
    }

}



class MainViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : BaseViewModel(){

    private var moviesLive = MutableLiveData<List<Results>>()

    fun onRefresh() {
        moviesUseCase.execute(moviesUseCase, MarkAsMainObserver())
    }

    fun setListMovies(minutesLeft: List<Results>) {
        this.moviesLive.value = minutesLeft
    }

    fun getListMovies(): LiveData<List<Results>> = moviesLive


    private inner class MarkAsMainObserver : BaseSingleObserver<List<Results>>() {

        override fun onSuccess(t: List<Results>) {
            super.onSuccess(t)
            setListMovies(t)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            setListMovies(emptyList())
        }

    }
}



