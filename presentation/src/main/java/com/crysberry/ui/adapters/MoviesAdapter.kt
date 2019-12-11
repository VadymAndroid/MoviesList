package com.crysberry.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crysberry.databinding.MoviesBinding
import com.cryssberry.core.Results


class MoviesAdapter (private val moviesModel: ArrayList<Results>
): RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val newsBinding = MoviesBinding.inflate(layoutInflater, parent, false)

        return MoviesViewHolder(newsBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {

        val model = moviesModel[position]
        holder.bind(model)

    }

    override fun getItemCount(): Int {
        return moviesModel.size
    }

    inner class MoviesViewHolder(private val newsBinding: MoviesBinding) : RecyclerView.ViewHolder(newsBinding.root) {

        fun bind(viewModel: Results) {
            this.newsBinding.viewModel = viewModel
        }
    }
}
