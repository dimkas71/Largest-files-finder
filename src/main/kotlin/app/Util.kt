package app

import kotlin.math.round

const val BYTES_IN_KB = 1024

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }

    return round(this * multiplier) / multiplier
}