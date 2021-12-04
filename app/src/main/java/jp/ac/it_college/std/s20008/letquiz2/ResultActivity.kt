package jp.ac.it_college.std.s20008.letquiz2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.ac.it_college.std.s20008.letquiz2.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intExtra = intent.getIntExtra("correct", -1)
        val timer = intent.getStringExtra("timer_count")
        val count = intent.getIntExtra("count",-1)




        binding.correcttext.text = "${intExtra}/10"
        binding.timetext.text = "${timer}秒"

        if (timer != null) {
            if (timer.toLong() >= count * 10) {
                binding.timetext.text = "time over"
            }
        }


//        continute.setOnClickListener {
//            finish()
//            setResult(Activity.RESULT_OK)
//        }
        binding.finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}