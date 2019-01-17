package se.christoferbodin.veganresan.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginValidate(val valid: Boolean)
