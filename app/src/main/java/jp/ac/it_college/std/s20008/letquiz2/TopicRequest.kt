package jp.ac.it_college.std.s20008.letquiz2

import retrofit2.Call
import retrofit2.http.GET

interface TopicRequest:Request {

    @GET("https://script.google.com/macros/s/AKfycbznWpk2m8q6lbLWSS6qaz3uS6j3L4zPwv7CqDEiC433YOgAdaFekGJmjoAO60quMg6l/exec?f=data")
    fun getTopic(): Call<Topic>

}