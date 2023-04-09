package com.domonz.androidcustomloader

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.cardview.widget.CardView


public class CustomLoader(var context: Context, var parentView: View) {

    var popup:PopupWindow? = null
    var width: Int = WindowManager.LayoutParams.WRAP_CONTENT
    var height: Int = WindowManager.LayoutParams.WRAP_CONTENT
    var dimOpacity: Float = 0.3f
    var outsideTouchEnabled = true
    var backgroundColor = Color.WHITE
    var titleText = ""
    var descriptionText = ""
    var elevation: Float = 10f
    var gravity: Int = Gravity.BOTTOM
    var xOffset = 0
    var yOffset = 0
    var marginLeft: Int = 0
    var marginRight: Int = 0
    var marginTop: Int = 0
    var marginBottom: Int = 0
    var backgroundCornerRadius = 0f
    var loaderColor = Color.BLACK
    var loaderWidth = 35
    var loaderHeight = 35
    var titleTextSize = 15f
    var descriptionTextSize = 10f
    var titleTextColor = Color.BLACK
    var descriptionTextColor = Color.BLACK
    var paddingLeft = 15
    var paddingRight = 15
    var paddingTop = 15
    var paddingBottom = 15
    var animationStyle: Int = 0
    var titleFont: Typeface? = null
    var descriptionFont: Typeface? = null
    var loaderSpeed:Long = 500

    @SuppressLint("MissingInflatedId")
    fun show() {
        popup = PopupWindow(context)
        val layout: View = LayoutInflater.from(context).inflate(R.layout.custom_loader_layout, null)
        popup?.contentView = layout
        popup?.height = height
        popup?.width = width
        if (animationStyle != 0) {
            popup?.animationStyle = animationStyle
        }

        val card: CardView = layout.findViewById(R.id.backCard)
        var container: LinearLayout = layout.findViewById(R.id.container)
        var icon: ImageView = layout.findViewById(R.id.loader_ico)
        val title: TextView = layout.findViewById(R.id.title_loader)
        val description: TextView = layout.findViewById(R.id.description_loader)

        card.cardElevation = elevation
        card.maxCardElevation = 15f

        val layoutParams = LinearLayout.LayoutParams(
            width, // Width
            height // Height
        )

        layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom)

        card.layoutParams = layoutParams
        card.setCardBackgroundColor(backgroundColor)
        card.radius = backgroundCornerRadius
        card.requestLayout()



        container.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)

        val layoutParamsIco = android.widget.LinearLayout.LayoutParams(
            loaderWidth, // Width
            loaderHeight // Height
        )
        icon.layoutParams = layoutParamsIco

        if (titleText.isEmpty()) {
            title.visibility = View.GONE
        } else {
            title.visibility = View.VISIBLE
            title.text = titleText
            title.setTextColor(titleTextColor)
            title.typeface = titleFont
            title.textSize = titleTextSize
        }

        if (descriptionText.isEmpty()) {
            description.visibility = View.GONE
        } else {
            description.visibility = View.VISIBLE
            description.text = descriptionText
            description.setTextColor(descriptionTextColor)
            description.typeface = descriptionFont
            description.textSize = descriptionTextSize
        }

        val animator = ValueAnimator.ofFloat(0f, 360f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = loaderSpeed // 1 second per rotation

        animator.addUpdateListener { animation ->
            val rotation = animation.animatedValue as Float
            icon.setRotation(rotation)
        }
        animator.start()

        parentView.post {
            if (parentView.windowToken != null) {
                popup?.isOutsideTouchable = outsideTouchEnabled
                popup?.setBackgroundDrawable(BitmapDrawable())
                popup?.showAtLocation(
                    parentView, gravity, xOffset,
                    yOffset
                )
                popup?.dimBehind()
            } else {
                // The view is not attached to the window hierarchy yet, try again later
                parentView.postDelayed({ show() }, 100)
            }
        }
    }

    fun hide(){
        popup?.dismiss()
    }

    private fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = dimOpacity
        wm.updateViewLayout(container, p)
    }





}