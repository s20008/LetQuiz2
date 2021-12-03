package jp.ac.it_college.std.s20008.letquiz2

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicAdapter(val data: List<TopicItem>,val ans:Set<String>) :
    RecyclerView.Adapter<TopicAdapter.MyHolder>() {

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    var selectCallBack: OnSelect? = null



    override fun getItemCount(): Int {
        if (data.isEmpty()) {
            return 0
        }
        return data[0].choices.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_topic,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        with(holder.itemView){
            container.setBackgroundColor(Color.WHITE)
            topic.text = "${('A' + position)}.${data[0].choices[position]}"
            setOnClickListener {
                if (ans.contains(data[0].choices[position])){
                    container.setBackgroundColor(Color.GREEN)
                    selectCallBack!!.select(true,data[0].answers)
                } else{
                    container.setBackgroundColor(Color.RED)
                    selectCallBack!!.select(false,data[0].answers)
                }
            }
        }
    }

}