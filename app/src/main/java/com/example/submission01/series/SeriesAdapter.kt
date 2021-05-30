package com.example.submission01.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.ItemDataBinding


class SeriesAdapter(val listener : SeriesClickListener) : PagedListAdapter<DataEntity, SeriesAdapter.SeriesBinder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SeriesBinder {
        val view = ItemDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return SeriesBinder(view)
    }

    override fun onBindViewHolder(holder: SeriesBinder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class SeriesBinder(private val binder: ItemDataBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind(data: DataEntity){
            with(binder){
                print(data.image)
                Glide.with(itemView.context)
                    .load(data.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                            .error(R.drawable.ic_broken)
                    )
                    .into(imageView)
                textView.text = data.title
            }
            itemView.setOnClickListener { listener.onClick(data) }
        }
    }

    interface SeriesClickListener{
        fun onClick(data : DataEntity)
    }

}