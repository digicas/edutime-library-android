package com.edukids.sdk.app

import android.os.Bundle
import androidx.activity.viewModels
import com.edukids.sdk.app.databinding.ActivityMainBinding
import com.skoumal.teanity.view.TeanityActivity
import timber.log.Timber

class MainActivity : TeanityActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutRes = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            viewModel.start(intent)
        } catch (e: IllegalStateException) {
            Timber.d(">! Not launched via edu launcher")
            finish()
            return
        }
    }

}