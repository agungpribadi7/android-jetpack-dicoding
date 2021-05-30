package com.example.submission01.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.ItemDataLinearBinding

class FavoriteSeriesAdapter(val listener : SeriesClickListener) : RecyclerView.Adapter<FavoriteSeriesAdapter.SeriesBinder>() {
    private var list = mutableListOf<DataEntity>()
    fun setData(listData: MutableList<DataEntity>){
        list.clear()
        list = listData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SeriesBinder {
        val view = ItemDataLinearBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return SeriesBinder(view)
    }

    override fun onBindViewHolder(holder: SeriesBinder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size

    inner class SeriesBinder(private val binder: ItemDataLinearBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind(data: DataEntity){
            with(binder){
                Glide.with(itemView.context)
                    .load(data.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_animated_loading)
                            .error(R.drawable.ic_broken)
                    )
                    .into(imageViewFav)
                titleFav.text = data.title
                val desc = data.description
                DescFav.text = desc
            }
            itemView.setOnClickListener { listener.onClick(data) }
        }
    }

    interface SeriesClickListener{
        fun onClick(data : DataEntity)
    }

}