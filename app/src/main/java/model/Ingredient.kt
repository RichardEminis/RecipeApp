package model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
) : Parcelable {
    companion object : Parceler<Ingredient> {
        override fun Ingredient.write(parcel: Parcel, flags: Int) {
            parcel.writeString(quantity)
            parcel.writeString(unitOfMeasure)
            parcel.writeString(description)
        }

        override fun create(parcel: Parcel): Ingredient {
            return Ingredient(
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            )
        }
    }
}