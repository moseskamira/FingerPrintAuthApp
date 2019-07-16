package com.example.fingureprintauthapp

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
// import android.security.keystore.KeyGenParameterSpec
// import android.security.keystore.KeyProperties
import android.support.v4.content.ContextCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.security.KeyStore
// import javax.crypto.KeyGenerator
import javax.crypto.Cipher
// import javax.crypto.SecretKey

/**
 * The commented imports will be needed in case one would like to use commented methods in this class
 * */
class MainActivity : AppCompatActivity() {
    lateinit var paraLabel: TextView
    lateinit var titleLabel: TextView
    lateinit var fingerImage: ImageView
    lateinit var fingerPrintManager: FingerprintManagerCompat
    lateinit var keyGuardManager: KeyguardManager
    lateinit var keyStore: KeyStore
    lateinit var cipher: Cipher
    val KEY_NAME = "AndroidKey"

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
                 paraLabel.text = getString(R.string.no_fingerPrint_scanner)
             } else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) !=
                 PackageManager.PERMISSION_GRANTED) {
                 paraLabel.text = getString(R.string.set_fingerPrint_permission)
             } else if (!keyGuardManager.isKeyguardSecure) {
                 paraLabel.text = getString(R.string.no_lock_message)

             } else if (!fingerPrintManager.hasEnrolledFingerprints()) {
                 paraLabel.text = getString(R.string.no_enrolled_finger)

             } else {
                 paraLabel.text = getString(R.string.place_finger_to_scanner)
             }
            val fingerPrintHandler = FingerPrintHandler(this)
            fingerPrintHandler.startAuth(fingerPrintManager, null)
            /**
             * Uncomment out this code to make calls to the generateKey() and cipherInit() methods
             * If you uncomment this, Do not forget to comment out the two lines of code just above this docString
             * */
//            generateKey()
//            if (cipherInit()) {
//                val cryptoObject:FingerprintManagerCompat.CryptoObject = FingerprintManagerCompat.CryptoObject(cipher)
//                val fingerPrintHandler = FingerPrintHandler(this)
//                fingerPrintHandler.startAuth(fingerPrintManager, cryptoObject)
//            }
        }
    }

    /**
     * If you need to implement the Crypto Object in the Application,
     * Uncomment methods below together with their usage in above code.
     * The Crypto Object helps to generate new key each time a new fingure print is generated in the device
     * And stored in the created keyStore
     *
     */


//    @TargetApi(Build.VERSION_CODES.M)
//    private fun generateKey(){
//        keyStore = KeyStore.getInstance("AndroidKeyStore")
//        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
//        keyStore.load(null)
//        keyGenerator.init(
//            KeyGenParameterSpec.Builder(
//                KEY_NAME,
//                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//            ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                .setUserAuthenticationRequired(true)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                .build())
//        keyGenerator.generateKey()
//    }

//    @TargetApi(Build.VERSION_CODES.M)
//    private fun cipherInit(): Boolean {
//        cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC)
//
//        keyStore.load(null)
//        val key: SecretKey = keyStore.getKey(KEY_NAME, null) as SecretKey
//        cipher.init(Cipher.ENCRYPT_MODE, key)
//        return true
//    }

}
