package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class Seat(
    var name: String? = null,
    var status: SeatStatus? = null
) : Parcelable {

    enum class SeatStatus {
        AVAILABLE, SELECTED, UNAVAILABLE
    }

    // Constructor để khôi phục đối tượng từ Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as? SeatStatus // Đọc SeatStatus từ Parcel
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name) // Ghi name vào Parcel
        parcel.writeSerializable(status) // Ghi status vào Parcel
    }

    // Định nghĩa CREATOR để tạo đối tượng từ Parcel
    companion object CREATOR : Parcelable.Creator<Seat> {
        override fun createFromParcel(parcel: Parcel): Seat {
            return Seat(parcel)
        }

        override fun newArray(size: Int): Array<Seat?> {
            return arrayOfNulls(size)
        }
    }
}
