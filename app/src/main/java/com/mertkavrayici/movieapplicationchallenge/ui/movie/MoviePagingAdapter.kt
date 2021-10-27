package com.mertkavrayici.movieapplicationchallenge.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mertkavrayici.movieapplicationchallenge.BR
import com.mertkavrayici.movieapplicationchallenge.data.Movie
import com.mertkavrayici.movieapplicationchallenge.databinding.MovieBinding

class MoviePagingAdapter : PagingDataAdapter<Movie,MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {


    var onClick : ((String)->Unit)? =null

    companion object{

        val DIFF_UTIL=object :DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {

                return oldItem.imdbID==newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {

                return oldItem==newItem
            }

        }
    }

    fun onMovieClick(listener:(String)->Unit){
        onClick=listener
    }

    inner class MyViewHolder(val viewDataBinding:MovieBinding):RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data=getItem(position)
        holder.viewDataBinding.setVariable(BR.movie,data)

        holder.viewDataBinding.root.setOnClickListener{
            onClick?.let{

                it(data?.imdbID!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding=MovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MyViewHolder(binding)

    }
}