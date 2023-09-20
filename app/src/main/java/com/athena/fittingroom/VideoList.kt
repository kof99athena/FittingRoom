package com.athena.fittingroom

import com.google.gson.annotations.SerializedName

data class VideoList(
    //api형식을 봐라
    //비디오라는 array안에 데이터들을 넣어둠. {id, description 등등}
    val videos: List<VideoItem>
)

data class VideoItem(
    val id: String,
    val title: String,

    @SerializedName("sources") //sources는 videoUrl이니까 gson이 읽을수있게 만듬. gson은 sources필드값을 videoUrl 프로퍼티에 할당함.
    val videoUrl: String,

    val channelName : String,
    val viewCount : String,
    val dateText : String,
    val channelThumb : String,

    @SerializedName("thumb")
    val videoThumb : String
)


/**
 * {
"id": "a",
"description": "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttps://www.bigbuckbunny.org",
"sources": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
"channelName": "Blender Foundation",
"viewCount": "24만회",
"dateText": "6개월 전",
"channelThumb": "https://picsum.photos/seed/Blender/40",
"thumb": "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
"title": "Big Buck Bunny"
},
 */
