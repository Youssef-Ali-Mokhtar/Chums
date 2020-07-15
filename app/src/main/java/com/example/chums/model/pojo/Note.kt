package com.example.chums.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    var title : String,
    var content : String,
    var date : String,
    var time : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}