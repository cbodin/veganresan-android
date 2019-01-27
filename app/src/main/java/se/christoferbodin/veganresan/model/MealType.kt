package se.christoferbodin.veganresan.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class MealType : Parcelable {
    RESTAURANT,
    HOMEMADE
}
