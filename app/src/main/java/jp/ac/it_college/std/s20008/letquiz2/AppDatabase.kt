package jp.ac.it_college.std.s20008.letquiz2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun examDao(): TopicDao

}