package cz.edukids.sdk.app

import android.content.Intent
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.google.android.material.button.MaterialButton
import com.skoumal.teanity.list.BindingAdapter
import com.skoumal.teanity.observable.observable
import com.skoumal.teanity.viewmodel.TeanityViewModel
import cz.edukids.sdk.EduTimeSdk
import cz.edukids.sdk.EduTimeSdkInstance
import cz.edukids.sdk.app.recycler.TextItem
import cz.edukids.sdk.model.EduMissionContract
import cz.edukids.sdk.model.EduMissionFinishParams
import cz.edukids.sdk.model.EduMissionStartParams
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.random.Random.Default.nextInt

class MainViewModel : TeanityViewModel() {

    var isSDKAvailable by observable(false, BR.sDKAvailable)
        @Bindable get
    val adapter = BindingAdapter<TextItem> {
        it.setVariable(BR.viewModel, this)
    }

    // ---

    private var pendingMission: EduMissionContract? = null
    private var sdk: EduTimeSdkInstance? = null

    @Throws(IllegalStateException::class)
    fun start(intent: Intent) {
        isSDKAvailable = EduTimeSdk().runCatching { getNewInstance(intent) }
            .onSuccess { sdk = it }
            .fold({ true }, { false })
    }

    // ---

    private inline fun <reified T> measureResult(body: () -> T): T {
        adapter.update(adapter.items + TextItem("Started new request…"))
        val start = System.currentTimeMillis()
        return body().also {
            val time = System.currentTimeMillis() - start
            val text = "%s\n[took %d ms]".format(
                it.toString(),
                time
            )
            adapter.update(adapter.items + TextItem(text))
        }
    }

    // ---

    fun onToggleMissionClicked(v: View) {
        val sdk = sdk ?: return
        val button = v as? MaterialButton ?: return

        button.isEnabled = false

        viewModelScope.launch {
            val mission = pendingMission
            pendingMission = if (mission == null) {
                measureResult { onStartMission(sdk) }.getOrNull()?.also {
                    button.text = "Finish Mission"
                }
            } else {
                measureResult { onFinishMission(sdk, mission) }.also {
                    button.text = "Start Mission"
                }
                null
            }
            button.isEnabled = true
        }
    }


    fun onTimeConstraintsClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            val result = measureResult {
                sdk.getTimeConstraints()
            }
            Timber.v(result.toString())
        }
    }

    fun onScreenTimeCategoriesClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            val result = measureResult {
                sdk.getScreenTimeCategoryInfo()
            }
            Timber.v(result.toString())
        }
    }

    fun onCurrencyStatsClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            val result = measureResult {
                sdk.getCurrencyStats()
            }
            Timber.v(result.toString())
        }
    }

    fun onSkillLevelClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            val result = measureResult {
                sdk.getSkillLevel()
            }
            Timber.v(result.toString())
        }
    }

    // ---

    private suspend fun onStartMission(sdk: EduTimeSdkInstance): Result<EduMissionContract> {
        val params = EduMissionStartParams(
            isRetry = false,
            skills = listOf("Test skill"),
            eduTaskType = "Special type"
        )
        return sdk.getMission().start(params)
    }

    private fun onFinishMission(sdk: EduTimeSdkInstance, mission: EduMissionContract) {
        val params = EduMissionFinishParams(
            contractId = mission.id,
            isSuccess = false,
            pointsAcquired = nextInt(10, 100)
        )
        sdk.getMission().finish(params)
    }

}