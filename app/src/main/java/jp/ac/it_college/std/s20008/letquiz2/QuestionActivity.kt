package jp.ac.it_college.std.s20008.letquiz2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.ac.it_college.std.s20008.letquiz2.databinding.ActivityMainBinding
import jp.ac.it_college.std.s20008.letquiz2.databinding.ActivityQuestionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding

    private val topicService = NetUtil.getService(TopicRequest::class.java)
    private val topics = Topic() //150道题
    private lateinit var adaper: TopicAdapter
    private var curPos = 0
    private val item = mutableListOf<TopicItem>()
    private val ans = mutableSetOf<String>()
    private var count = 0
    private var correct = 0

    private var selectCount = 0
    private var flag = true

    private var timerCount:String? = null

    inner class MyCountDownTimer(millisInFuture:Long, countDownInteral:Long)
        : CountDownTimer(millisInFuture, countDownInteral) {

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            binding.timerText.text = "%1d:%2$02d".format(minute, second)

        }

        override fun onFinish() {
            binding.timerText.text = "0:00"
        }
    }

    val onSelcect = object:OnSelect{

        override fun select(cor: Boolean, ansCount:Int) {
            if (selectCount == 0){
                count++
                selectCount++
            }

            if (!cor){
                flag = false
            }

            if (flag && cor && selectCount == ansCount){
                correct++
                flag = false
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x11){
            curPos++
            correct = 0
            handler.sendEmptyMessage(0x11)
        }
    }

    val handler = object: Handler(Looper.myLooper()!!){
        @SuppressLint("NotifyDataSetChanged")
        override fun handleMessage(msg: Message) {
            if (curPos == topics.size - 1){
                Toast.makeText(this@QuestionActivity, "all question end!", Toast.LENGTH_SHORT).show()
                return
            }
            item.clear()
            reshufle(topics[curPos])
            item.add(topics[curPos])
            binding.question.text = item[0].question
            adaper.notifyDataSetChanged()
        }
    }

    private fun reshufle(topicItem: TopicItem) {
        ans.clear()
        repeat(topicItem.answers){
            ans.add(topicItem.choices[it])
        }
        topicItem.choices.shuffle()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adaper = TopicAdapter(item,ans)
        adaper.selectCallBack = onSelcect
        binding.topic.adapter = adaper
        binding.topic.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.timerText.text = "{10}:{0}"
        val timer = MyCountDownTimer((10 * 10 * 1000).toLong(),100)
        timer.start()

        binding.next.setOnClickListener {
            flag = true
            selectCount = 0
//            Toast.makeText(this, "$count", Toast.LENGTH_SHORT).show()
            binding.appProgressBar.progress = count
            binding.appProgressBar.max = 10
            binding.appProgress.text = "$count/10"
            if (count >= 10){
//                with(Intent(this@QuestionActivity,ResultActivity::class.java)){
//                    putExtra("correct", correct)
//                    startActivityForResult(this,0x11)
//
//                }
                setQuestion()
                count = 0
                return@setOnClickListener
            }

            this.curPos++
            item.clear()
            binding.question.text = topics[curPos].question
            item.add(topics[curPos])
            handler.sendEmptyMessage(0x11)
        }

        val examDao = DbUtil.getInstance(this).examDao()

        Thread{
            val all = examDao.getAll()
            topics.clear()
            all.forEach {
                val topicItem = TopicItem()
                topicItem.answers = it.answers
                topicItem.id = it.id
                topicItem.choices = it.choices!!.split(",").filter { item -> item.isNotEmpty() }.toList()
                topicItem.question = it.question

                topics.add(topicItem)
            }

            topics.shuffle()
            handler.sendEmptyMessage(0x11)
        }.start()

        topicService.getTopic().enqueue(object : Callback<Topic> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Topic>, response: Response<Topic>) {
                response.body()?.let {
                    topics.clear()
                    topics.addAll(it)
                    handler.sendEmptyMessage(0x11)
                }

            }

            override fun onFailure(call: Call<Topic>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@QuestionActivity, "get topic error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val times = binding.timerText.text.toString().split(":")
        val min = times[0].toLong()
        val sec = times[1].toLong()
        val total = (item.size * 10) - (min * 60 + sec)
        timerCount = total.toString()

        val intent = Intent(this@QuestionActivity, ResultActivity::class.java)
        intent.putExtra("TIMER_COUNT",timerCount)
        intent.putExtra("correct", correct)
        startActivity(intent)
        finish()
        return



    }
}