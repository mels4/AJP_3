package com.example.android.ajp_1.entity.remote.response

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movieentities")
@Parcelize
data class MovieResponse (

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    var movieId: String,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    var title:String,

    @ColumnInfo(name = "poster_path")
    @field:SerializedName("poster_path")
    var imgPath:String,

    @ColumnInfo(name = "backdrop_path")
    @field:SerializedName("backdrop_path")
    var imgPathBgr: String,

    @ColumnInfo(name = "overview")
    @field:SerializedName("overview")
    var description : String,

    @ColumnInfo(name = "release_date")
    @field:SerializedName("release_date")
    var rilis : String,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var userScore: Double,


    @ColumnInfo(name = "tagline")
    @SerializedName("popularity")
    var tageline:String,

    @ColumnInfo(name = "status")
    @SerializedName("original_title")
    var status:String,

    @ColumnInfo(name = "favorites")
    var favorite: Boolean = false

): Parcelable


data class MovieResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val total_result: Int,
    @SerializedName("total_pages")
    val total_page:Int,
    @SerializedName("results")
    val result: List<MovieResponse> = mutableListOf()
)
