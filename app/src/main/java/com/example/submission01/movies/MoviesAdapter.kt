package com.example.submission01.movies

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


class MoviesAdapter(val listener : MoviesClickListener) : PagedListAdapter<DataEntity, MoviesAdapter.MoviesBinder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataEntity>() {
            override fun areItemsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataEntity, newItem: DataEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MoviesBinder {
        val view = ItemDataBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MoviesBinder(view)
    }

    override fun onBindViewHolder(holder: MoviesBinder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MoviesBinder(private val binder: ItemDataBinding) : RecyclerView.ViewHolder(binder.root) {
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

    interface MoviesClickListener{
        fun onClick(data : DataEntity)
    }

}