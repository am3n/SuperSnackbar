package ir.am3n.supersnackbar

import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.snackbar.Snackbar


object SuperSnackbar {

    private const val TAG = "TheRootLayoutOfSuperSnackbar"

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE)
    annotation class Duration

    fun show(view: ViewGroup, text: String, @Duration duration: Int, @ColorInt backgroundColor: Int) {

        // ############## Prepare ##############

        val ctx = view.context
        var layout: LinearLayout? = view.children.firstOrNull { it.tag == TAG } as LinearLayout?
        if (layout == null) {
            layout = LinearLayout(ctx).apply {
                this.tag = TAG
                this.orientation = LinearLayout.VERTICAL
                this.setVerticalGravity(Gravity.BOTTOM)
            }
            view.addView(layout)
            layout.updateLayoutParams<ViewGroup.LayoutParams> {
                this.width = MATCH_PARENT
                this.height = MATCH_PARENT
            }
        }


        // ############## Initialize ##############

        val textView = AppCompatTextView(ctx).apply {
            this.id = ViewCompat.generateViewId()
            this.text = text
            this.setPadding(ctx.dpToPixel(16), ctx.dpToPixel(24), ctx.dpToPixel(16), ctx.dpToPixel(24))
        }

        val progressBar = ProgressBar(ctx, null, android.R.attr.progressBarStyleHorizontal).apply {
            this.max = 2000
        }

        val relativeLayout = RelativeLayout(ctx).apply {
            this.setBackgroundColor(backgroundColor)
            this.alpha = 0f
            this.translationY = ctx.dpToPixel(80).toFloat()
        }


        // ############## Inflate ##############

        layout.addView(relativeLayout)
        relativeLayout.addView(progressBar)
        relativeLayout.addView(textView)
        relativeLayout.updateLayoutParams<LinearLayout.LayoutParams> {
            updateMargins(top = ctx.dpToPixel(4))
        }


        // ############## Setup ##############

        textView.updateLayoutParams<RelativeLayout.LayoutParams> {
            this.width = MATCH_PARENT
        }
        progressBar.updateLayoutParams<RelativeLayout.LayoutParams> {
            this.width = MATCH_PARENT
            this.height = ctx.dpToPixel(2)
            addRule(RelativeLayout.ALIGN_BOTTOM, textView.id)
        }


        // ############## Start ##############

        ObjectAnimator.ofInt(progressBar, "progress", 0, progressBar.max).apply {
            setDuration(calculateDuration(duration))
            start()
        }
        relativeLayout.animate().translationY(0f).alpha(1f).setDuration(300).start()
        view.postDelayed({
            relativeLayout.removeAllViews()
            layout.removeView(relativeLayout)
        }, calculateDuration(duration))

    }

}