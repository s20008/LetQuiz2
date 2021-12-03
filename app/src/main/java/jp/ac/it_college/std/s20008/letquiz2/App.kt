package jp.ac.it_college.std.s20008.letquiz2

import android.app.Application
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.alibaba.fastjson.JSON
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.util.logging.Handler

class App: Application() {


    override fun onCreate() {
        super.onCreate()
        getDataFromLocal()?.let {
            // save to database
            val list = mutableListOf<Item>()
            getDataFromLocal()?.forEach {
                val item = Item()
                item.id = it.id
                item.answers = it.answers
                item.question = it.question
                item.choices = it.choices.joinToString(",")
                list.add(item)
            }

            val examDao = DbUtil.getInstance(this).examDao()

            val handler = object :android.os.Handler(Looper.myLooper()!!){
                override fun handleMessage(msg: Message) {
                    Toast.makeText(baseContext, "insert success!", Toast.LENGTH_SHORT).show()
                }
            }

            Thread{
                examDao.insertAll(list)
                handler.sendEmptyMessage(0x11)
            }.start()
        }
    }

    private fun getDataFromLocal(): MutableList<TopicItem>? {
        val data = resources.openRawResource(R.raw.data)
        val br = BufferedReader(InputStreamReader(data))
        val result = StringBuilder()
        var temp: String?
        temp = br.readLine()
        while (temp != null){
            result.append(temp)
            temp = br.readLine()
        }

        return JSON.parseArray(result.toString(),TopicItem::class.java)
    }


}