package com.example.fics_compose

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "histories")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val netWorth: Double,
    val wallet: Double,
    val gains: Double,
    val trades: Int
//    val user: usrInfo,



//    @ColumnInfo("netWorthListId")
//    val netWorthListId : Long
)

//@Entity(
//    tableName = "net_worth_list",
//    foreignKeys = [ForeignKey(
//        entity = HistoryItem::class,
//        parentColumns = ["id"],
//        childColumns = ["netWorthListId"],
//        onDelete = ForeignKey.CASCADE
//    )])
//data class NetWorthList(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long = 0,
//
//    @TypeConverters(DoubleListConverter::class)
//    val netWorthList: List<Double>
//
//)
//
//class DoubleListConverter{
//    @TypeConverter
//    fun fromDoubleList(doubleList:List<Double>?) : String?{
//        return doubleList?.joinToString(",")
//    }
//
//    @TypeConverter
//    fun toDoubleList(value:String?): List<Double>?{
//        return value?.split(",")?.map { it.toDouble() }
//    }
//}
//
