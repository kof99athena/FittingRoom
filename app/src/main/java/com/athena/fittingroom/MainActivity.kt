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

        videoAdapter = VideoAdapter()

        binding.videoListRecyclerView.apply {
            //apply 스코프 함수 : 자기 자신을 내뱉는다.
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }
    }
}