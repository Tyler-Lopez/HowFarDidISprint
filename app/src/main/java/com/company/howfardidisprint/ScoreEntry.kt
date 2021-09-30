package com.company.howfardidisprint

import android.os.Parcel
import android.os.Parcelable

data class ScoreEntry(val meters: Int) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(meters)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScoreEntry> {
        override fun createFromParcel(parcel: Parcel): ScoreEntry {
            return ScoreEntry(parcel)
        }

        override fun newArray(size: Int): Array<ScoreEntry?> {
            return arrayOfNulls(size)
        }
    }
}
