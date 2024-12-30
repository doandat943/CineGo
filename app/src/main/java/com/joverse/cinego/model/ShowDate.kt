package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class ShowDate(
    val date: String? = null,
    val showTimes: ArrayList<ShowTime> = ArrayList()
) : Parcelable {

    companion object CREATOR : Parcelable.Creator<ShowDate> {
        override fun createFromParcel(parcel: Parcel): ShowDate {
            return ShowDate(parcel)
        }

        override fun newArray(size: Int): Array<ShowDate?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(ShowTime.CREATOR) ?: ArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeTypedList(showTimes)
    }

    override fun describeContents(): Int {
        return 0
    }
}
