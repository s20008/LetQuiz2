package jp.ac.it_college.std.s20008.letquiz2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Item {

    @PrimaryKey
    var id: Int? = null

    @ColumnInfo(name = "answers")
    var answers: Int? = null

    @ColumnInfo(name = "choices")
    var choices: String? = null

    @ColumnInfo(name = "question")
    var question: String? = null

}