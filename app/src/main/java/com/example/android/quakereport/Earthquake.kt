package com.example.android.quakereport

import android.os.Parcel
import android.os.Parcelable

class Earthquake(val magnitude: Double, val place: String, val date: Long, val url: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(magnitude)
        parcel.writeString(place)
        parcel.writeLong(date)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Earthquake> {
        override fun createFromParcel(parcel: Parcel): Earthquake {
            return Earthquake(parcel)
        }

        override fun newArray(size: Int): Array<Earthquake?> {
            return arrayOfNulls(size)
        }
    }
}