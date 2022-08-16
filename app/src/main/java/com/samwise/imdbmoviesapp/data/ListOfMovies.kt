package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.samwise.imdbmoviesapp.api.ImdbResponse
import com.samwise.imdbmoviesapp.api.Query
import kotlinx.parcelize.Parcelize

@Entity(tableName = "list_of_movies")
@Parcelize
@TypeConverters(Converters::class)
data class ListOfMovies(
    @PrimaryKey var typeOfList: Query,
    @Embedded
    @TypeConverters(Converters::class)
    var listOfMovies: ImdbResponse
) : Parcelable{
    }