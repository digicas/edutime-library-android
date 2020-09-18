package cz.edukids.sdk.app

import android.os.Bundle
import androidx.activity.viewModels
import com.skoumal.teanity.view.TeanityActivity
import cz.edukids.sdk.app.databinding.ActivityMainBinding
import cz.edukids.sdk.model.permission.hasEduPermission
import cz.edukids.sdk.model.permission.requestEduPermission
import timber.log.Timber

class MainActivity : TeanityActivity<ActivityMainBinding>() {

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

        if (!hasEduPermission()) {
            Timber.d("Requesting permission...")
            requestEduPermission {
                Timber.d("Permission granted? [success=$it]")
            }
        }
    }

}