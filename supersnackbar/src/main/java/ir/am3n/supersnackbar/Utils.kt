package ir.am3n.supersnackbar

import android.content.Context
import android.util.DisplayMetrics
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


fun calculateDuration(duration: Int): Long {
    return if (duration > 0) duration.toLong()
    else when (duration) {
        Snackbar.LENGTH_INDEFINITE -> 24 * 60 * 60 * 1000 // 1 day
        Snackbar.LENGTH_LONG -> 5000
        else -> 3000
    }
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun Context.dpToPixel(dp: Float): Int {
    val metrics = resources.displayMetrics
    return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Context.dpToPixel(dp: Int): Int {
    return dpToPixel(dp.toFloat())
}



/**
 * This method converts device specific pixels to density independent pixels.
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @return A float value to represent dp equivalent to px value
 */
fun Context.pixelsToDp(px: Int): Float {
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}