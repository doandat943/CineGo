package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class TimeSeat(
    val time: String? = null,
    val seats: ArrayList<Seat> = ArrayList()
) : Parcelable {
    // Constructor để khôi phục đối tượng từ Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Seat.CREATOR) ?: ArrayList() // Khôi phục danh sách seats
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(time) // Ghi time vào Parcel
        dest.writeTypedList(seats) // Ghi danh sách seats vào Parcel
    }

    // Định nghĩa CREATOR để tạo đối tượng từ Parcel
    companion object CREATOR : Parcelable.Creator<TimeSeat> {
        override fun createFromParcel(parcel: Parcel): TimeSeat {
            return TimeSeat(parcel)
        }

        override fun newArray(size: Int): Array<TimeSeat?> {
            return arrayOfNulls(size)
        }
    }
}