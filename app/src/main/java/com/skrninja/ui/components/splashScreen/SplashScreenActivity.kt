package com.skrninja.ui.components.splashScreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textview.MaterialTextView
import com.scottyab.rootbeer.RootBeer
import com.skrninja.mvvm.R
import com.skrninja.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var appVersion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // To make sure that the app runs in light mode only
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_splash_screen)

        // textview where app version will be displayed
        val appVersionText = findViewById<MaterialTextView>(R.id.appVersion)
        // appVersion is a variable holding the value of appversion and this value is set to 
        // textview accessed using appVersionText
        appVersion = getAppVersion()
        appVersionText.text = appVersion

        // to check whether the phone on which the app will run is rooted or not
        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted) {
            //we found indication of root
            indicationUser()

        } else {
//            viewModel.verifyVersion()
//            observeViewModel()
              launchApp()

        }

    }

    private fun launchApp() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun indicationUser() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.oops))
        builder.setMessage(getString(R.string.rooted_device))
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                finish()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun getAppVersion(): String {
        try {
            val pInfo: PackageInfo =
                packageManager.getPackageInfo(packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}