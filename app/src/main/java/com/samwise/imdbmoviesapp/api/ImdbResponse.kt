package com.samwise.imdbmoviesapp.api

import android.os.Parcelable
import androidx.room.TypeConverters
import com.samwise.imdbmoviesapp.data.Converters
import com.samwise.imdbmoviesapp.data.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
@TypeConverters(Converters::class)
data class ImdbResponse(
    @TypeConverters(Converters::class)
    val items: Array<Movie>,
    val errorMessage: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImdbResponse

        if (!items.contentEquals(other.items)) return false
        if (errorMessage != other.errorMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = items.contentHashCode()
        result = 31 * result + errorMessage.hashCode()
        return result
    }
}
