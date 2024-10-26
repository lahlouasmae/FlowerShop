package ma.ensa.projet.beans

import android.os.Parcel
import android.os.Parcelable

data class Flower(
    val name: String,
    val price: Double,
    val animationRes: Int,
    val description: String,
    val imageRes: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(price)
        parcel.writeInt(animationRes)
        parcel.writeString(description)
        parcel.writeInt(imageRes)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Flower> {
        override fun createFromParcel(parcel: Parcel): Flower {
            return Flower(parcel)
        }

        override fun newArray(size: Int): Array<Flower?> {
            return arrayOfNulls(size)
        }
    }

    // Implémentation de equals et hashCode comme indiqué précédemment
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Flower) return false
        return name == other.name && description == other.description
    }

    override fun hashCode(): Int {
        return name.hashCode() * 31 + description.hashCode()
    }
}
