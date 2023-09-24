package com.athena.fittingroom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.athena.fittingroom.databinding.ItemVideoBinding
import com.bumptech.glide.Glide

class VideoAdapter(val context: Context,
                   private val onclick: (VideoItem)->Unit) :
    ListAdapter<VideoItem, VideoAdapter.Viewholder>(diffUtil) {

    inner class Viewholder(private val binding: ItemVideoBinding) : ViewHolder(binding.root) {
        //아이템뷰바인딩을 받아오고 아이템.root 생성자에 넣는다.
        fun bind(item: VideoItem) {
            //item받아오기. 바인딩에 있는 데이터를 연결한다.
            binding.titleTextView.text = item.title
            binding.subTextView.text = context.getString(
                R.string.sub_title_video_info,
                item.channelName,
                item.viewCount,
                item.dateText
            )
            Glide.with(binding.channelLogoImageView)
                .load(item.channelThumb)
                .circleCrop()
                .into(binding.channelLogoImageView)

            Glide.with(binding.videoThumbnailImageView)
                .load(item.videoThumb)
                .into(binding.videoThumbnailImageView)

            binding.root.setOnClickListener{
                onclick.invoke(item)
            }

        }

    }//inner class Viewholeder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(currentList[position])

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoItem>() {
            //object : DiffUtil.ItemCallback<VideoItem>() 익명클래스를 정의함
            //이 변수(diffUtil)의 타입은 DiffUtil.ItemCallback<VideoItem>

            //areItemsTheSame, areContentsTheSame method는 리사이클러뷰나 리스트뷰등
            //UI 컴포넌트에서 아이템 간 변경사항을 비교하고 감지한다.
            override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem == newItem
            }

        }
    }


}