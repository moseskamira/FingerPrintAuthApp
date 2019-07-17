package com.example.fingureprintauthapp

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.widget.ImageView
import android.widget.TextView
import android.support.v4.os.CancellationSignal

class FingerPrintHandler(private val context: Context) : FingerprintManagerCompat.AuthenticationCallback() {

    private val cancellationSignal = CancellationSignal()

    fun startAuth(fingerprintManager: FingerprintManagerCompat, cryptoObject: FingerprintManagerCompat.CryptoObject?) {
        fingerprintManager.authenticate(cryptoObject,0, cancellationSignal , this, null)
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
        super.onAuthenticationError(errMsgId, errString)
        this.update("There was an Authentication Error$errString", false)
    }

    override fun onAuthenticationFailed() {
        super.onAuthenticationFailed()
        this.update("Authentication Failed", false)
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
        super.onAuthenticationHelp(helpMsgId, helpString)
        this.update("Error$helpString", false)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        this.update("You Can Now Access The App", true)
    }

    private fun update(string: String, boolean: Boolean) {
        val paraLabel: TextView = (context as Activity).findViewById(R.id.prompt_message)
        val newImage: ImageView = context.findViewById(R.id.fingure_scanner)
        paraLabel.text = string
        if(!boolean) {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            newImage.setImageResource(R.mipmap.ic_done)
        }
    }
}
