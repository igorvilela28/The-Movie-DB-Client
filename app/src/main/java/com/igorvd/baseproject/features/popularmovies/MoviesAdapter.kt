package com.igorvd.baseproject.features.popularmovies

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.igorvd.baseproject.R
import com.igorvd.baseproject.domain.movies.entities.Movie
import com.igorvd.baseproject.utils.extensions.content
import com.igorvd.baseproject.utils.extensions.loadImageFromUrl
import kotlinx.android.synthetic.main.footer_progress.view.*

import kotlinx.android.synthetic.main.row_movie.view.*


class MoviesAdapter(
        val onItemClicked: (Movie) -> Unit,
        val onRetryClick: () -> Unit) : ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    //ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK)
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
                    .inflate(R.layout.footer_progress, parent, false)

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

    private var movies : List<Movie> = emptyList()
    override fun submitList(list: List<Movie>) {
        movies = list
        super.submitList(list)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
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
            ivPoster.loadImageFromUrl(context, movie.posterUrl, R.drawable.placeholder)
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

    private object DIFF_CALLBACK : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}
