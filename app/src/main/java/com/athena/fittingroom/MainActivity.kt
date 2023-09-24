package com.athena.fittingroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.athena.fittingroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var videoAdapter : VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        videoAdapter = VideoAdapter(this){videoItem ->
            binding.motionLayout.transitionToEnd() //스타트에서 엔드로 가는 트렌지션이 생긴다.
        }

        binding.motionLayout.jumpToState(R.id.collapse)
        //뷰가 처음에 실행되면 강제로 collapse상태로 변하게 만듬

        binding.videoListRecyclerView.apply {
            //apply 스코프 함수 : 자기 자신을 내뱉는다.
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }

        val videoList = readData("videos.json",VideoList::class.java) ?: VideoList(emptyList())
        videoAdapter.submitList(videoList.videos)




    }//onCreate method

}//MainActivity