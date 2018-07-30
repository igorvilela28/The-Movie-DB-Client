package com.igorvd.baseproject.features.popularmovies.detail

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.MovieVideo
import com.igorvd.baseproject.utils.extensions.content

import kotlinx.android.synthetic.main.row_trailer.view.*


class MovieVideosAdapter(
    val onItemClicked: (MovieVideo) -> Unit) : ListAdapter<MovieVideo, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.row_trailer, parent, false)

        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movieVideo = getItem(position)
        with(holder.itemView) {
            tvTrailerName.content = movieVideo.name
            setOnClickListener {
                onItemClicked(movieVideo)
            }
        }
    }

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<MovieVideo>() {
        override fun areItemsTheSame(oldItem: MovieVideo, newItem: MovieVideo): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: MovieVideo, newItem: MovieVideo): Boolean {
            return oldItem == newItem
        }
    }

}
