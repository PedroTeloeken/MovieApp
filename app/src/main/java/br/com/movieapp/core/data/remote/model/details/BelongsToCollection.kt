package br.com.movieapp.core.data.remote.model.details


import com.google.gson.annotations.SerializedName

data class BelongsToCollection(

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String

)