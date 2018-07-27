package com.igorvd.baseproject.utils.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.igorvd.baseproject.R
import kotlinx.android.synthetic.main.row_movie.view.*

/**
 * @author Igor Vilela
 * @since 26/07/18
 */



private val requestOptions = RequestOptions().placeholder(R.drawable.placeholder)


fun ImageView.loadImageFromUrl(context: Context, url: String) {

    Glide.with(context)
            .setDefaultRequestOptions(requestOptions)
            .load(url)
            .into(this)

}