package com.igorvd.baseproject.utils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.igorvd.baseproject.R

class SpinnerDropdownAdapter<T>(
    private val mContext: Context,
    resource: Int = R.layout.spinner_item,
    val items: MutableList<T> = mutableListOf(),
    private val getText: (T) -> String) : ArrayAdapter<T>(mContext, resource) {

    override fun getItem(position: Int): T {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    fun clearList() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addAllToList(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    private fun getCustomView(position: Int, parent: ViewGroup): View {

        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rootView = inflater.inflate(R.layout.spinner_item,
                parent, false)

        val tv = rootView.findViewById<TextView>(R.id.text)

        tv.text = getText(items[position])

        return rootView

    }
}