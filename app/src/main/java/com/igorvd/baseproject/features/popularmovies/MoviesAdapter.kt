package com.igorvd.baseproject.features.popularmovies

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.utils.extensions.content
import com.igorvd.baseproject.utils.extensions.loadImageFromUrl

import kotlinx.android.synthetic.main.row_movie.view.*


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {


    var movies: List<Movie> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = movies[position]

        with(holder.itemView) {
            tvTitle.content = movie.title
            ivPoster.loadImageFromUrl(context, movie.posterUrl)
        }

    }

}
