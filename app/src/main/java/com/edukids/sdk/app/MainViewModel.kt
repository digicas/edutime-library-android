package com.edukids.sdk.app

import android.content.Intent
import android.view.View
import androidx.lifecycle.viewModelScope
import com.edukids.sdk.EduSdk
import com.edukids.sdk.EduSdkInstance
import com.edukids.sdk.app.recycler.TextItem
import com.edukids.sdk.model.EduMissionContract
import com.edukids.sdk.model.EduMissionFinishParams
import com.edukids.sdk.model.EduMissionStartParams
import com.google.android.material.button.MaterialButton
import com.skoumal.teanity.list.BindingAdapter
import com.skoumal.teanity.viewmodel.TeanityViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

class MainViewModel : TeanityViewModel() {

    val adapter = BindingAdapter<TextItem> {
        it.setVariable(BR.viewModel, this)
    }

    // ---

    private var pendingMission: EduMissionContract? = null
    private var sdk: EduSdkInstance? = null

    @Throws(IllegalStateException::class)
    fun start(intent: Intent) {
        sdk = EduSdk().getNewInstance(intent)
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
                measureResult { onStartMission(sdk) }.also {
                    button.text = "Finish Mission"
                }.getOrNull()
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
            measureResult { sdk.getTimeConstraints() }
        }
    }

    fun onScreenTimeCategoriesClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            measureResult { sdk.getScreenTimeCategoryConstraints() }
        }
    }

    fun onCurrencyStatsClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            measureResult { sdk.getCurrencyStats() }
        }
    }

    fun onSkillLevelClicked() {
        val sdk = sdk ?: return

        viewModelScope.launch {
            measureResult { sdk.getSkillLevel() }
        }
    }

    // ---

    private suspend fun onStartMission(sdk: EduSdkInstance): Result<EduMissionContract> {
        val params = EduMissionStartParams(
            isRetry = false,
            skills = listOf("Test skill"),
            eduTaskType = "Special type"
        )
        return sdk.getMission().start(params)
    }

    private fun onFinishMission(sdk: EduSdkInstance, mission: EduMissionContract) {
        val params = EduMissionFinishParams(
            contractId = mission.id,
            isSuccess = false,
            pointsAcquired = nextInt(10, 100)
        )
        sdk.getMission().finish(params)
    }

}