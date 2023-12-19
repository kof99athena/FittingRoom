package com.athena.fittingroom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.athena.fittingroom.databinding.ItemVideoBinding
import com.athena.fittingroom.databinding.ItemVideoHeaderBinding
import com.bumptech.glide.Glide

class PlayerVideoAdapter(private val context : Context, private val onClick : (VideoItem) -> Unit ) : ListAdapter<VideoItem, RecyclerView.ViewHolder>(diffUtil) {

    //이너 클래스가 2개임, 그래서 타입이 PlayerVideoAdapter.ViewHolder가 아니라 RecyclerView.ViewHolder로 사용한다.
    //PlayerVideoAdapter.ViewHolder 였다면 어떤 홀더를 써야할지 애매했을것이다.
    inner class HeaderViewholeder(private val binding : ItemVideoHeaderBinding) : RecyclerView.ViewHolder(

    ) {
        fun bind(item : VideoItem){

        }
    }

    inner class VideoViewholeder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : VideoItem){
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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHoleder {
        //뷰타입 : companion object
        return if(viewType == VIEW_TYPE_HEADER){
            HeaderViewholeder(
                ItemVideoHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )
        }else{
            VideoViewholeder(
                ItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHoleder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER){
            (holder as HeaderViewholeder).bind(currentList[position])
        } else {
            (holder as VideoViewholeder).bind(currentList[position])
        }
    }

    //리스트아탑터에서 쓰는 메소드
    //멀티 뷰홀더를 바인딩해준다. 0번째타입이 헤더이다.
    override fun getItemViewType(position: Int): Int {
        return if(position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_VIDEO
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_VIDEO = 1

        val diffUtil = object : DiffUtil.ItemCallback<VideoItem> {
            override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}