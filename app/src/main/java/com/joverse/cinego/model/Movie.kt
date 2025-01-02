package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    var title: String? = null,
    var description: String? = null,
    var poster: String? = null,
    var duration: Int = 0,
    var trailer: String? = null,
    var imdb: Int = 0,
    var premiere: String? = null,
    var ageRating: String? = null,
    var price: Int = 0,
    var genres: ArrayList<String> = ArrayList(),
    var casts: ArrayList<Cast> = ArrayList(),
    var showDates: ArrayList<ShowDate> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: ArrayList(),
        parcel.createTypedArrayList(Cast.CREATOR) ?: ArrayList(),
        parcel.createTypedArrayList(ShowDate.CREATOR) ?: ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(poster)
        parcel.writeInt(duration)
        parcel.writeString(trailer)
        parcel.writeInt(imdb)
        parcel.writeString(premiere)
        parcel.writeString(ageRating)
        parcel.writeInt(price)
        parcel.writeStringList(genres)
        parcel.writeTypedList(casts)
        parcel.writeTypedList(showDates)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
