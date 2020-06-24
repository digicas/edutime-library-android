package com.edukids.sdk.model

import android.os.Parcelable
import java.util.concurrent.Future

interface ScreenTimeCategoryConstraints : Parcelable {

    /* Category currently assigned to this time period */
    val currentCategory: ScreenTimeCategory

    /* Category currently assigned to this app */
    val assignedCategory: ScreenTimeCategory

    /* Available categories under which the app can be listed */
    val availableCategories: List<ScreenTimeCategory>

    /* Tries to set current category to the provided one. It might however fail to do so under some circumstances (ie. Category is locked by child's parent, etc) */
    suspend fun suggestCorrectCategory(categoryId: String): Result<ScreenTimeCategoryConstraints>

    /* Same as ^ but compliant with java */
    @Throws()
    fun suggestCorrectCategoryAsync(categoryId: String): Future<ScreenTimeCategoryConstraints>

}