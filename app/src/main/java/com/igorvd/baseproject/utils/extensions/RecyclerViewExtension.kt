package com.igorvd.baseproject.utils.extensions

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 *
 * @author Igor Vilela
 * @since 11/01/2018
 */

fun<T: RecyclerView.ViewHolder> RecyclerView.setup(
        context: Context,
        adapter: RecyclerView.Adapter<T>,
        layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
        itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator()) : RecyclerView {

    this.layoutManager = layoutManager
    this.itemAnimator = itemAnimator
    this.adapter = adapter

    return this
}