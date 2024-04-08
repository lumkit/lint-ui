package io.github.lumkit.desktop.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WindowSize(
    @SerializedName("width") val width : Float,
    @SerializedName("height") val height : Float,
): Serializable
