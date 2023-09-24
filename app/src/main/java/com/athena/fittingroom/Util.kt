package com.athena.fittingroom

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException

//파일은 string으로 받아오고, gson에 넘겨주는 클래스파일
fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = this.resources.assets.open(fileName)
        val byteArray = ByteArray(inputStream.available()) //이걸 저정할 Array, 생성자에 어벨러블 크기로 만들기
        inputStream.read(byteArray) //  inputStream.read() 이 함수를 통해 읽어들인다.
        inputStream.close()

        val gson = Gson()
        gson.fromJson(String(byteArray),classT)

    } catch (e: IOException) {
        //try에서 나온 에러가 캐치의 영역에 빠진다. e로 받는다.
        null //null 리턴한다.
    } catch (e: JsonSyntaxException){
        e.printStackTrace()
        null //JsonSyntaxException 경우에도 추가하기
    }
}