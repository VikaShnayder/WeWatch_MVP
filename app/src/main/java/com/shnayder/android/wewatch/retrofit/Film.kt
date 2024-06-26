package com.shnayder.android.wewatch.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Film(
    //var id: String,
    var imdbID: String,
    @Json(name = "Title")
    var title: String,
    @Json(name = "Poster")
    var posterPath: String,
    @Json(name = "Type")
    var overview: String,
    @Json(name = "Year")
    var releaseDate: String? = null,
) {
    fun getReleaseYearFromDate(): String? {
        return releaseDate?.split("-")?.get(0)
    }
}

/*
@Entity(tableName = "my_list_film")
class Film (
    @PrimaryKey(autoGenerate = true)
    val ID: Int?,
    @ColumnInfo
    val Title:String,
    @ColumnInfo
    val Year:Int,
    @ColumnInfo
    val Poster:String
): java.io.Serializable
*/
/* {"Title":"Star Wars: Episode IV - A New Hope",
    "Year":"1977",
    "Rated":"PG",
    "Released":"25 May 1977",
    "Runtime":"121 min",
    "Genre":"Action, Adventure, Fantasy",
    "Director":"George Lucas",
    "Writer":"George Lucas",
    "Actors":"Mark Hamill, Harrison Ford, Carrie Fisher",
    "Plot":"Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth ...",
    "Language":"English",
    "Country":"United States",
    "Awards":"Won 6 Oscars. 66 wins & 31 nominations total",
    "Poster":"https://m.media-amazon.com/images/M/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg",
    "Ratings":[{"Source":"Internet Movie Database","Value":"8.6/10"},
    {"Source":"Rotten Tomatoes","Value":"93%"},
    {"Source":"Metacritic","Value":"90/100"}],
    "Metascore":"90",
    "imdbRating":"8.6",
    "imdbVotes":"1,435,882",
    "imdbID":"tt0076759",
    "Type":"movie",
    "DVD":"10 Oct 2016",
    "BoxOffice":"$460,998,507",
    "Production":"N/A",
    "Website":"N/A",
    "Response":"True"}*/