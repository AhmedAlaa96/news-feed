package com.example.newsfeeds.data.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.newsfeeds.R
import com.example.newsfeeds.data.models.DrawerItem
import com.example.newsfeeds.databinding.DrawerItemBinding
import com.squareup.picasso.Picasso

class DrawerAdapter(val items: List<DrawerItem>): BaseAdapter() {
    override fun getCount() = items.size

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun changeSelected(position: Int){
        for (i in items.indices){
            items[i].isSelected = i == position
        }
        notifyDataSetChanged()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = DrawerItemBinding.inflate(LayoutInflater.from(parent?.context), parent,false)

        if(items[position].isSelected){
            Picasso.get().load(R.drawable.selected).into(binding.isSelectedIcon)
        }else{
            Picasso.get().load(R.color.transparent).into(binding.isSelectedIcon)
        }

        Picasso.get().load(items[position].itemIcon).into(binding.drawerIcon)

        binding.drawerTitle.text = items[position].itemTitle

        return binding.root

    }
}
