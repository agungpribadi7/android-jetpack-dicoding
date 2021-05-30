package com.example.submission01.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.source.local.entity.DataEntity
import com.example.submission01.databinding.ItemDataLinearBinding

class FavoriteMoviesAdapter(val listener : MoviesClickListener) : RecyclerView.Adapter<FavoriteMoviesAdapter.MoviesBinder>() {
    private var list = mutableListOf<DataEntity>()
    fun setData(listData: MutableList<DataEntity>){
        list.clear()
        list = listData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MoviesBinder {
        val view = ItemDataLinearBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MoviesBinder(view)
    }

    override fun onBindViewHolder(holder: MoviesBinder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size

    inner class MoviesBinder(private val binder: ItemDataLinearBinding) : RecyclerView.ViewHolder(binder.root) {
        fun bind(data: DataEntity){
            with(binder){
                print(data.image)
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

    interface MoviesClickListener{
        fun onClick(data : DataEntity)
    }

}