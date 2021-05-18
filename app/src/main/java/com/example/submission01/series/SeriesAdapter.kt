package com.example.submission01.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.DataClass
import com.example.submission01.databinding.ItemDataBinding


class SeriesAdapter(val listener : SeriesClickListener) : RecyclerView.Adapter<SeriesAdapter.SeriesBinder>() {
    private var list = mutableListOf<DataClass>()
    fun setData(listData: MutableList<DataClass>){
        if(listData.isEmpty()) return
        list.clear()
        list = listData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SeriesBinder {
        val view = ItemDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return SeriesBinder(view)
    }

    override fun onBindViewHolder(holder: SeriesBinder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size

    inner class SeriesBinder(private val binder: ItemDataBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind(data: DataClass){
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
        fun onClick(data : DataClass)
    }

}