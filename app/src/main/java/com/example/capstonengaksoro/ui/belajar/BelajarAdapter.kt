package com.example.capstonengaksoro.ui.belajar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstonengaksoro.R
import com.example.capstonengaksoro.data.response.ImagesItem

class BelajarAdapter(private val data: List<ImagesItem>) :
    RecyclerView.Adapter<BelajarAdapter.ListViewHolder>() {

//    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_belajar, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.haImageView)
        private val tvName: TextView = itemView.findViewById(R.id.haTextView)
        fun bind(dataList: ImagesItem) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataList.image)
                    .apply(RequestOptions().override(100, 100))
                    .into(image)
                tvName.text = dataList.text

//                setOnClickListener {
//                    onItemClickCallback?.onItemClick(user)
//                }
            }
        }
    }

//    interface OnItemClickCallback {
//        fun onItemClick(data: ImagesItem)
//    }
}