package com.athena.fittingroom

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.athena.fittingroom.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var videoAdapter : VideoAdapter

    //엑소플레이어를 사용하기 위한 변수
    private var player : ExoPlayer?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initMotionLayout()
        initVideoRecyclerView()

        initControlButton()

        binding.hideButton.setOnClickListener {
            binding.motionLayout.transitionToState(R.id.hide)
            player?.pause()
        }


    }//onCreate method

    private fun initControlButton() {
        binding.controlButton.setOnClickListener {
            //작아진 후 하단화면의 컨트롤러 버튼을 지우기
            player?.let {
                if (it.isPlaying) {
                    it.pause()
                } else {
                    it.play()
                }
            }

        }
    }

    private fun initVideoRecyclerView() {
        videoAdapter = VideoAdapter(this) { videoItem ->
            binding.motionLayout.setTransition(R.id.collapse, R.id.expand)
            binding.motionLayout.transitionToEnd() //스타트에서 엔드로 가는 트렌지션이 생긴다.

            play(videoItem)
        }

        binding.videoListRecyclerView.apply {
            //apply 스코프 함수 : 자기 자신을 내뱉는다.
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }

        val videoList = readData("videos.json", VideoList::class.java) ?: VideoList(emptyList())
        videoAdapter.submitList(videoList.videos)
    }

    private fun initMotionLayout() {
        binding.motionLayout.targetView = binding.videoPlayerContainer
        binding.motionLayout.jumpToState(R.id.hide)
        //뷰가 처음에 실행되면 강제로 hide로 변하게 만듬

        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
               //트랜지션이 바뀔때는 컨트롤러를 끄기
                binding.playerView.useController  = false

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.playerView.useController = (currentId == R.id.expand)
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })
    }


    private fun initExoPlayer(){
        // 빌드업패턴으로 넣어준다.
        player = ExoPlayer.Builder(this).build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer //동영상을 사용할수 있는 상태가 됨.
                binding.playerView.useController = false

                exoPlayer.addListener(object : Player.Listener{
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        super.onIsPlayingChanged(isPlaying)

                        if (isPlaying){
                            binding.controlButton.setImageResource(R.drawable.pause)
                        } else{
                            binding.controlButton.setImageResource(R.drawable.play)
                        }
                    }

                })
            }

        //플레이어는 릴리즈가 되고 난후는 사용불가하다

    }

    private fun play(videoItem: VideoItem){
        player?.setMediaItem(MediaItem.fromUri(Uri.parse(videoItem.videoUrl)))
        player?.prepare() //미디어를 준비시켜주거
        player?.play() //플레이한다.
        binding.videoTitleTextView.text = videoItem.title
    }

    //혹시나 플레이어가 만들어지는데 시간이 지연될수잇으니..onStart에 만들어주기
    override fun onStart() {
        super.onStart()

        if(player == null){
            initExoPlayer()
        }

    }

    override fun onResume() {
        super.onResume()

        if(player == null){
            initExoPlayer()
        }
    }

    //백그라운드로 갔을때는 영상을 멈추고 싶다
    override fun onStop() {
        super.onStop()
        player?.pause() //일시정지 되어있다.
    }

    override fun onDestroy() {
        super.onDestroy()

        player?.release() //플레이어를 초기화한다.
    }

}//MainActivity












