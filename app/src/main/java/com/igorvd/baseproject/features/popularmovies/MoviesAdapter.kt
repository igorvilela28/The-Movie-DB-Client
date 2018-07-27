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

import kotlinx.android.synthetic.main.row_movie.view.*


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private val set = ConstraintSet()
    private val requestOptions = RequestOptions().placeholder(R.drawable.placeholder)

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


        holder.itemView.tvTitle.content = movie.title


        Glide.with(holder.itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(movie.posterUrl)
                .into(holder.itemView.ivPoster)

       /* with(set) {
            @SuppressLint("DefaultLocale")
            val posterRatio = String.format("%d:%d", width, height)
            clone(holder.itemView.parentConstraint)
            setDimensionRatio(holder.itemView.ivPoster.id, posterRatio)
            applyTo(holder.itemView.parentConstraint)
        }
*/

    }

}
