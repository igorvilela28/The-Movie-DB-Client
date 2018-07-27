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

import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.utils.extensions.content
import com.igorvd.baseproject.utils.extensions.loadImageFromUrl
import kotlinx.android.synthetic.main.item_progress.view.*

import kotlinx.android.synthetic.main.row_movie.view.*


class MoviesAdapter(
        val movies: ArrayList<Movie> = arrayListOf(),
        val onItemClicked: (Movie) -> Unit,
        val onRetryClick: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class MyFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //constants for footer types
    private var currentFootType = FooterType.TYPE_LOADING

    //view types
    private val ITEMS = 0
    private val LOAD_MORE = 1

    private enum class FooterType {
        TYPE_LOADING,
        TYPE_ERROR,
    }

    //public variables
    var hasFooter = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return if(viewType == ITEMS) {
            val itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_movie, parent, false)

            MyViewHolder(itemView)
        } else {

            val itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_progress, parent, false)

            MyFooterViewHolder(itemView)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movies.size) {
            LOAD_MORE
        } else {
            ITEMS
        }
    }

    override fun getItemCount(): Int {
        return movies.size + 1
    }

    override fun getItemId(position: Int): Long {

        return if(position == movies.size) {
            Long.MAX_VALUE
        } else {
            movies[position].id.toLong()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is MyViewHolder) {
            bindItemViewHolder(holder, position)
        } else if (holder is MyFooterViewHolder) {
            bindFooterViewHolder(holder)
        }
    }

    private fun bindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val movie = movies[position]
        with(holder.itemView) {
            tvTitle.content = movie.title
            ivPoster.loadImageFromUrl(context, movie.posterUrl)
            setOnClickListener {
                onItemClicked(movie)
            }
        }
    }

    private fun bindFooterViewHolder(holder: MyFooterViewHolder) {

        val visibility = if(hasFooter) View.VISIBLE else View.GONE
        holder.itemView.progressRoot.visibility = visibility

        if(currentFootType == FooterType.TYPE_LOADING) {

            holder.itemView.itemProgressLLRetry.visibility = View.GONE
            holder.itemView.itemprogressIvRetryIcon.visibility = View.GONE
            holder.itemView.itemProgressBar.visibility = View.VISIBLE

            holder.itemView.progressRoot.setOnClickListener(null)

        } else if (currentFootType == FooterType.TYPE_ERROR) {

            holder.itemView.itemProgressBar.visibility = View.GONE
            holder.itemView.itemProgressLLRetry.visibility = View.VISIBLE
            holder.itemView.itemprogressIvRetryIcon.visibility = View.VISIBLE

            holder.itemView.progressRoot.setOnClickListener {
                onRetryClick()
            }

        }
    }

    /**
     * Shows a progress bar below the last item
     */
    fun showFooterProgress() {

        setFooterType(FooterType.TYPE_LOADING)
    }

    /**
     * shows a error message below the last item
     */
    fun showFooterError() {
        setFooterType(FooterType.TYPE_ERROR)
    }

    /**
     * Remove the progress or error message below the last item
     */
    fun removeFooter() {
        this.hasFooter = false
        notifyDataSetChanged()
    }

    /**
     * Change the footer type
     * @param[type] Must be one of [TYPE_ERROR] or [TYPE_LOADING]
     */
    private fun setFooterType(type: FooterType) {
        this.hasFooter = true
        this.currentFootType = type
        notifyDataSetChanged()
    }

}
