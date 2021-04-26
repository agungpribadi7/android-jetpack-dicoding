package com.example.submission01.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission01.R
import com.example.submission01.data.DataClass
import com.example.submission01.databinding.ItemDataBinding


class MoviesAdapter(val listener : MoviesClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesBinder>() {
    private var list = mutableListOf<DataClass>()
    fun setData(listData: MutableList<DataClass>){
        if(listData.isEmpty()) return
        list.clear()
        list = listData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MoviesBinder {
        val view = ItemDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MoviesBinder(view)
    }

    override fun onBindViewHolder(holder: MoviesBinder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size

    inner class MoviesBinder(private val binder: ItemDataBinding) : RecyclerView.ViewHolder(binder.root) {
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

    interface MoviesClickListener{
        fun onClick(data : DataClass)
    }

}