package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class ShowTime(
    val theater: String? = null,
    val time: String? = null,
    val seats: ArrayList<Seat> = ArrayList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Seat.CREATOR) ?: ArrayList()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(theater)
        dest.writeString(time)
        dest.writeTypedList(seats)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShowTime> {
        override fun createFromParcel(parcel: Parcel): ShowTime {
            return ShowTime(parcel)
        }

        override fun newArray(size: Int): Array<ShowTime?> {
            return arrayOfNulls(size)
        }
    }
}