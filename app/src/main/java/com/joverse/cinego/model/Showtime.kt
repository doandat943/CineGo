package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class Showtime(
    val date: String? = null,
    val times: ArrayList<TimeSeat> = ArrayList()
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Showtime> {
        override fun createFromParcel(parcel: Parcel): Showtime {
            return Showtime(parcel)
        }

        override fun newArray(size: Int): Array<Showtime?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(TimeSeat.CREATOR) ?: ArrayList() // Sửa lỗi ở đây
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeTypedList(times)
    }

    override fun describeContents(): Int {
        return 0
    }
}
