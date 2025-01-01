package com.joverse.cinego.model

import android.os.Parcel
import android.os.Parcelable

data class Ticket(
    var ticketCode: String? = null,
    var ticketStatus: TicketStatus? = null,
    var movieTitle: String? = null,
    var poster: String? = null,
    var theater: String? = null,
    var date: String? = null,
    var time: String? = null,
    var duration: Int = 0,
    var totalAmount: Int = 0,
    var seats: ArrayList<String> = ArrayList()
) : Parcelable {

    enum class TicketStatus {
        AVAILABLE, USED, UNAVAILABLE
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as? TicketStatus,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createStringArrayList() ?: ArrayList(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ticketCode)
        parcel.writeSerializable(ticketStatus)
        parcel.writeString(movieTitle)
        parcel.writeString(poster)
        parcel.writeString(theater)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeInt(duration)
        parcel.writeInt(totalAmount)
        parcel.writeStringList(seats)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }
}

