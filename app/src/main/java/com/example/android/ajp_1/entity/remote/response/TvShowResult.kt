package com.example.android.ajp_1.entity.remote.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tvshowentities")
@Parcelize
data class TvShowResult (

    @PrimaryKey
    @ColumnInfo(name= "id")
    @field:SerializedName("id")
    var tvShowId: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var title: String,

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var decription: String,

    @ColumnInfo(name="poster_path")
    @SerializedName("poster_path")
    var imagePath: String,

    @ColumnInfo(name= "vote_average")
    @SerializedName("vote_average")
    var user_score: Double,

    @ColumnInfo(name= "backdrop_path")
    @SerializedName("backdrop_path")
    var ImgPathBgr: String,

    @ColumnInfo(name="first_air_date")
    @SerializedName("first_air_date")
    var date: String,

    @ColumnInfo(name = "number_of_episodes")
    @SerializedName("popularity")
    var nEpisode: String,

    @ColumnInfo(name= "number_of_seasons")
    @SerializedName("original_language")
    var nSeason: String,

    @ColumnInfo(name= "tagline")
    @SerializedName("vote_count")
    var tagline: String,

    @ColumnInfo(name = "favorites")
    var favorites: Boolean = false

) : Parcelable

data class TvShowResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_results")
    val total_result: Int,
    @SerializedName("total_pages")
    val total_page:Int,
    @SerializedName("results")
    val results: List<TvShowResult> = mutableListOf()
)