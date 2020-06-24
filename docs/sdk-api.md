# EduSDK

Library is initialized automatically via background launch, and so requires no user/developer input whatsoever.

## Instance

In order to execute any actions you need a instance of the sdk. Instance is initialized through an Intent which the app was launched with.

### Definition

```kotlin
object EduSdk {

  fun getInstance(intent: Intent): EduSdkInstance

}

interface EduSdkInstance {

  suspend fun getTimeConstraints(): TimeConstraints
  suspend fun getScreenTimeCategoryConstraints(): CategoryConstraints
  suspend fun getCurrencyStats(): CurrencyStats

  @Throws()
  fun getTimeConstraintsAsync(): Future<TimeConstraints>
  @Throws()
  fun getScreenTimeCategoryConstraintsAsync(): Future<CategoryConstraints>
  @Throws()
  fun getCurrencyStatsAsync(): Future<CurrencyStats>

  fun getMission(): Mission

}
```

### Example

```kotlin
lateinit var eduSdk: EduSdkInstance

fun onCreate(/*...*/) {
  //...
  eduSdk = EduSDK.getInstance(intent)
}
```

## Time Constraints

App might request the time remaining before it's shut down in favor of more relevant app.

### Definition

```kotlin
interface TimeConstraints {
  
  /** 
   * Datetime at which the app will be (or was) started.
   */
  val startTimestamp: Long
  
  /**
   * Datetime at which the app will be stopped if was previously running.
   */
  val stopTimestamp: Long

}
```

### Example

```kotlin
suspend fun getTimeConstraints(eduSdk: EduSdkInstance) {
  val constraints = eduSdk.getTimeConstraints()
}
```

```java
public void getTimeConstraints(EduSdkInstance eduSdk) {
  Future<TimeConstraints> constraints = eduSdk.getTimeConstraintsAsync()
}
```

## Screen Time Category Constraints

App might request its category as defined in launcher by default or child's parent.

### Definition

```kotlin
interface ScreenTimeCategoryConstraints {
  
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
```

### Example

```kotlin
suspend fun getCategoryConstraints(eduSdk: EduSdkInstance) {
  val constraints = eduSdk.getScreenTimeCategoryConstraints()
}
```

```java
public void getCategoryConstraints(EduSdkInstance eduSdk) {
  Future<CategoryConstraints> constraints = eduSdk.getScreenTimeCategoryConstraintsAsync()
}
```

## Time Category

Defines configuration and state of given category.

### Definition

```kotlin
interface ScreenTimeCategory {

  /* Category might be locked by the child's parent */
  val isLocked: Boolean
  /* Category is currently selected as default */
  val isSelected: Boolean

  /* Ids are normalized and unique */
  val id: String
  val name: String
  val description: String

}
```

### Example

```kotlin
suspend fun getCategory(constraints: ScreenTimeCategoryConstraints) {
  val category = constraints.currentCategory
}
```

## Mission

Allows access to states activities to the EduSDK backend. It provides itself the information needed for making useful statistics and reporting on currency gained through the excercise.

Please note that it has to be called at the very start and very end of the excercise to compute the time it took to complete the task.

### Definition

```kotlin
interface EduMission {

  /* Starts the mission. Sdk will mark the time of the function invocation */
  suspend fun start(params: EduMissionStartParams): MissionContract
  /* Ends the mission. Sdk will mark the time of the function invocation */
  fun finish(params: EduMissionFinishParams)
  
  /* @see [start] */
  @Throws()
  fun startAsync(params: EduMissionStartParams): Future<MissionContract>
  
  /* 
   * Lets the sdk know that user has completed all of the missions available.
   */
  fun complete()

}

interface EduMissionStartParams {

  /* Notifies the sdk that the task is going to be restarted, possibly subtracting points from the total or adding smaller bonus amount */
  val isRetry: Boolean
  /* 
   * Skill description. Conforms to no particular pattern.
   * (eg. addition single numbers over 10) */
  val skills: List<String>
  /* 
   * List of disciplines that this particular excercise requires.
   * (eg. math, english, ...) */
  val disciplines: List<String>? = null
  /* Permits app to send the sdk additional data to accompany the "start" entry */
  val dataBundle: Any? = null
  /* 
   * States exact task type which is being performed. 
   * (eg. math pyramid)
   */
  val eduTaskType: String

}

interface EduMissionFinishParams {

  /* Tells the SDK which contract does it want to end. This parameter is received from the result of `start(...)`*/
  val contractId: String
  /* Notifies the sdk that the mission was successful, so the underlying sdk knows that no further excercises are necessary */
  val isSuccess: Boolean
  /* Reports number of points acquired by completing the excercise. Also known as Edupoints */
  val pointsAcquired: Int
  /* Permits app the send the sdk additional data to accompany the "finish" entry */
  val dataBundle: Any? = null

}

interface MissionContract {

  /* Id of the created contract. */  
  val id: String

}
```

### Example

```kotlin
fun startMission(eduSdk: EduSdkInstance) {
  val mission = eduSdk.getMission()
  scope.launch {
    val params = collectStartParams() // async
    mission.start(params)
  }
}
```

```kotlin
fun endMission(eduSdk: EduSdkInstance) {
  val mission = eduSdk.getMission()
  scope.launch {
    val params = collectFinishParams() // async
    mission.finish(params)
  }
}
```

## Currency

Currency is computed internally (by the underlying launcher) by submitting individual Missions. App can listen to changes or make one-shot requests.

Returns Timecoin values.

### Definition

```kotlin
interface CurrencyStats {

  val currentAmount: Long
  val earnedInInstance: Long
  val currentlyEarnable: Long

}
```

### Example One-Shot

```kotlin
suspend fun getCurrencyStats(eduSdk: EduSdkInstance) {
  val currency = eduSdk.getCurrencyStats()
}
```

```java
public void getCurrencyStats(EduSdkInstance eduSdk) {
  Future<CurrencyStats> currency = eduSdk.getCurrencyStatsAsync()
}
```

### Example Observer

```kotlin
fun observeCurrencyStats(eduSdk: EduSdkInstance) {
  eduSdk.observeCurrencyStats().observe(this, Observer {
    it.earnedInInstance // fetch points for this instance
  })
}
```

## Skill Level

Provides comprehensive list of skills the user has already acquired in this or other apps.

SkillSet should always come filled from top to bottom. Discipline, as the lowest - the broadest - level, will be only filled if the user has mastered the category completely in other apps. Your app should try some harder excercises to evaluate the user's performance and adjust accordingly.

### Definition

```kotlin
interface SkillLevel {

  val mastered: SkillSet
  val inProgress: SkillSet
  val failed: SkillSet

}

interface SkillSet {

  val skill: List<String>
  val categories: List<String>
  val subcategories: List<String>
  val disciplines: List<String>

}
```

### Example

```kotlin
suspend fun getSkillLevel(eduSdk: EduSdkInstance) {
  val skill = eduSdk.getSkillLevel()
}
```

```java
public void getSkillLevel(EduSdkInstance eduSdk) {
  Future<SkillLevel> skill = eduSdk.getSkillLevelAsync()
}
```
