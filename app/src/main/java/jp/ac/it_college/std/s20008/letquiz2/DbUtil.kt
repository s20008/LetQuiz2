package jp.ac.it_college.std.s20008.letquiz2

import android.content.Context
import androidx.room.Room

object DbUtil {

    fun getInstance(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "exam_db"
        ).build()
    }

}