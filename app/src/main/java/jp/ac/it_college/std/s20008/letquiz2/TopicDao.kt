package jp.ac.it_college.std.s20008.letquiz2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: MutableList<Item>)

    @Query("select * from item")
    fun getAll():List<Item>

}