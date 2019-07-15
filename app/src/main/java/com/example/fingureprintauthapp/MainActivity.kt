package com.example.fingureprintauthapp

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var paraLabel: TextView
    lateinit var titleLabel: TextView
    lateinit var fingerImage: ImageView
    lateinit var fingerPrintManager: FingerprintManagerCompat
    lateinit var keyGuardManager: KeyguardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fingerImage = fingure_scanner
        titleLabel = fingure_title
        paraLabel = prompt_message

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerPrintManager = FingerprintManagerCompat.from(this)
            keyGuardManager = applicationContext.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
             if(!fingerPrintManager.isHardwareDetected) {
                 paraLabel.text = "Finger Print Scanner Not Detected In Your Device"
             } else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
                 paraLabel.text = "Permission Not Set To Use Fingure Print"
             } else if (!keyGuardManager.isKeyguardSecure) {
                 paraLabel.text = "Add Lock To Your Phone In Settings"

             } else if (!fingerPrintManager.hasEnrolledFingerprints()) {
                 paraLabel.text = "You Should Add Atleat One Fingure Print To Use This Feature"

             } else
                 paraLabel.text = "Place You Fingure On Scanner To Access The App"

            val fingerPrintHandler = FingerPrintHandler(this)
            fingerPrintHandler.startAuth(fingerPrintManager, null)




        }
    }


}
