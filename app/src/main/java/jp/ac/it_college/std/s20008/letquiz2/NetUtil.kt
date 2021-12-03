package jp.ac.it_college.std.s20008.letquiz2

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetUtil {

    /**
     * 保存每个请求对应的cookie数据
     */
    val cookieStore = hashMapOf<String, MutableList<Cookie>>()

    const val AUTHENTICATION_COOKIE = "cookie"

    fun <T : Request> getService(cls: Class<T>): T {

        val client = OkHttpClient.Builder().cookieJar(object : CookieJar {

            override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                Log.e("debug", "url: $url saveFromResponse: $cookies")
                if (url.toString().matches(Regex(".*/login"))) {
                    cookieStore[AUTHENTICATION_COOKIE] = cookies
                } else {
                    cookieStore[url.toString()] = cookies
                }
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                Log.e("debug", "url: $url loadForRequest: ${cookieStore[AUTHENTICATION_COOKIE]}")
                return cookieStore.getOrDefault(
                    url.toString(),
                    cookieStore.getOrDefault(AUTHENTICATION_COOKIE, mutableListOf())
                )
            }

        }).build()

        return Retrofit.Builder()
            .baseUrl("https://script.googleusercontent.com/macros/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(cls) as T
    }

    fun getAuthenticationCookie(): String {
        val cookieList = cookieStore[AUTHENTICATION_COOKIE].toString()
        return cookieList.substring(1, cookieList.length - 1)
    }

}