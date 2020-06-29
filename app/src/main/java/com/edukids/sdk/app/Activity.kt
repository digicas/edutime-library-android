package com.edukids.sdk.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edukids.sdk.EduSdk
import com.edukids.sdk.EduSdkInstance
import timber.log.Timber

class Activity : AppCompatActivity(R.layout.activity_main) {

    private var sdk: EduSdkInstance? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sdk = EduSdk().getNewInstance(intent)
        } catch (e: IllegalStateException) {
            Timber.d(">! Not launched via edu launcher")
        }

    }

}