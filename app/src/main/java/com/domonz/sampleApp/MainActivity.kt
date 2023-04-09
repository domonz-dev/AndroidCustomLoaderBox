package com.domonz.sampleApp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import com.domonz.androidcustomloader.CustomLoader
import com.domonz.sampleApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //createExcel()
        updateData()
    }

    fun updateData(){

        CustomLoader(this@MainActivity, binding.justALongDescription).apply {
            gravity = Gravity.BOTTOM
            backgroundCornerRadius = 30f
            backgroundColor = Color.WHITE
            outsideTouchEnabled = true
            dimOpacity = 0.6f
            titleFont = ResourcesCompat.getFont(context, R.font.poppins_semi_bold)
            descriptionFont = ResourcesCompat.getFont(context, R.font.poppins_medium)
            titleText = "Loading, please wait..."
            descriptionText = "This may take a while"
            descriptionTextColor = Color.parseColor("#AAAAAA")
            loaderWidth = 45
            loaderHeight = 45
            marginLeft = 20
            marginRight = 20
            marginBottom = 60
            paddingLeft = 60
            paddingRight = 60
            paddingTop = 50
            paddingBottom = 50
            loaderSpeed = 1000

        }.show()

    }




}