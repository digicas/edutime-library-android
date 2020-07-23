# Requirements on the information exchange between educational app and edutime client library

## Intro
Edutime client library enables the communication between the educational app and EduKids launcher / mobile management environment.

In order to do that Edutime client library is to be bundled within the education app, where below mentioned features can be invoked via API (and also required callbacks to be registered). 


### Schema of components

```
+------------------------------------------+
|              Kid's mobile                |     +---------+
|                                          |     | parents |
| +--------------+           +-----------+ |     | devices |
| |   edu app    |           | EduKids   |<----->+---------+
| |              |           | launcher  | |     +----------+
| |   +---(SDK)--+           |           | |     | edu apps |
| |   |          |           |           |<----->| registry |
| |   | edutime  |           |           | |     +----------+
| |   | client   |<--------->|           | |     +------------+
| |   | library  |           |           |<----->| edu skills |
| |   |          |           |           | |     | analytics  |
| +---+----------+           +-----------+ |     +------------+
+------------------------------------------+
```

### Information exchange

There are 3 kinds of information to be exchanged:

1. User relevant
2. Time related
3. Educational progress related

The data exchange between the edutime library and EduKids launcher shall happen every 15 seconds.

Educational app can communicate with the edutime library anytime (via defined API), but can receive up to 15 seconds old information.
Provided information is cached and send to the EduKids launcher on the next sync iteration.

- [x] // TODO frequency yet to be discussed
 --> conclusion: communication between the client library and EduKids launcher will happen instantly, i.e. without caching in SDK

Note: The general idea of the implementation of the edutime library is similar to Google Play Game Services (https://developers.google.com/games/services/android/quickstart).

## User related information exchange
Both educational app and EduKids launcher can be run under the different user profiles. We need to assure the below described information is related to the proper user.

#### [SDKCALL] Get the edutime user ID
Educational app gets the uuid of the EduKids launcher user, under which it can execute further described commands.

note: This uuid shall be generated on the very frequent bases within the EduKids launcher in order to be privacy compliant. Therefore the uuid acts rather as the session id.

- [x] // TODO discuss the frequency or procedure of the user's uuid generation / usage
-->  conclusion: there will be stable launcher's user uuid generated for the particular edu app (based on edu apps SDKKEY)

SDKKEY will be generated in the edu apps registry and can be used across several versions and platforms of the app


## Time related information exchange

Dealing with time and its valuation is the key aspect of the EduKids ecosystem - see [Kategorie času]( https://us20.campaign-archive.com/?u=2435031a45e9f4d33c738c65c&id=e8e15797a6 ) for the related categorisation (in czech).


### App own screentime related

#### [SDKCALL] Time off warning
Educational app gets the time(date) when the EduKids launcher stops the app due to the switch of the mobile to the more strict time category. 

This enables the educational app to warn its user in advance within its UI.  

#### [SDKCALL] Time on info
Assuming the educational app has the background process - it gets the time(date) when the EduKids launcher enables the app due to the switch of the mobile to the less strict time category. 

This enables the educational app's background process to differentiate between the quiet and non quiet mode (e.g. for notifications, etc.).  

#### [SDKCALL] ScreenTime category info
Educational app gets the information on under which time category the app is listed by the parent. It also receives the list of categories and their status (un/locked).

This enables to request the different / more relevant category if needed.

#### [SDKCALL] ScreenTime category suggest
Educational app can suggest to be listed under the different screentime category.

This will be propagated to the parent of the user, who might act upon this suggestion.


### Influencing screentime of other apps
Educational apps have the privilege to influence the available screentime for other applications installed on the mobile. This is very strong and responsible privilege.

Kid has to gain / have **timecoins** ("časozlaťáky") in order run other apps (e.g. games, or video consumption apps).

We know that edu apps / games use some sort of achievemnts / badges, etc. (e.g. stars, virtual money) to show and motivate on the progress of the user.

EduKids cannot rely on this kind of information therefore the following is established.

The overall idea is that the educational app reports currently gained **edupoints** to the EduKids launcher. 
EduKids launcher translates those edupoints into the **timecoins** based on the rules and preference set by the parent (some coefficient function defined per educational app).
Timecoins can be further used for unlocking the time categories or spending on screentime of the other apps within certain time timecategory (as defined by parents).

The definition of under which conditions the app's edupoints are gained and their amount is fully up to the app developer. It can be related to the gained levels, stars, virtual money, etc.

#### [SDKCALL] Timecoins info
Educational app gets the info on current amount of timecoins the kid has available.

This enables the educational app to show this amount in its UI.

#### [SDKCALL] Report gained edupoints
Educational app reports the amount of edupoints gained since the last successfull edupoints report call.

Returns timepoints gained (as a deffered Promise / Future - deffered up to the sync time). 

This enables the educational app to show currently gained **timecoins** in its UI as the reward.

- [x] // TODO clarify potential sync frequency issue
--> conclusion: immediate callback -> no sync frequency issues


## Educational progresss related information exchange

Reporting of the educational progress is the mandatory feature of the apps privileged by EduKids.

App by this reporting in fact declares: 

"Kid (user) managed the task(s) in these **forms**, which required these **skills** as declared within these **(sub)categories** of these **disciplines**.

There will be skills analytics platform (ala Google analytics) available for the app developer / producer to get the insight on educational coverage (potential vs real achievemnts). 

Each educational application shall register its discipline (math / physics / languages / ...) coverage during the certification towards the EduKids platform. (This is done within the certification process and not by using edutime library.)

Enumeration of skills and related (sub)categories and disciplines will be defined by EduKids project.

- [x] // TODO discuss versioning and propagation of changes of the skills / (sub)categories / disciplines enumeration
--> enumeration will be hard-bundled in the SDK version
 
#### [SDKCALL] Report educational progress
It is expected that the educational app reports every minute user's educational progress through edutime client library to EduKids launcher. The progress is further propagated to parents and teachers. EduKids platform can remap app reported skills for the parent's report. 

Educational app reports JSON with: 

 - list of skills which were required for fulfillment of the tasks
 - revision number of the skills enumeration

For each used skill it reports: 

 - related educational (sub)categories and disciplines
 - related task forms 
 - when (timedate) the skill was gained

Example: skill: adding single numbers over 10, subcategory: addition and subtraction, category: arithmetic, discipline: math


Task forms are freely defined by the educational app. 


- [ ] // TODO discuss certainty on gained skill - level within skill / skill; e.g. via task solution time aspect 


#### [SDKCALL] Get the current level of user's educational skills
Educational app receives the skills / (sub)categories / disciplines, which the user mastered. This is received only for registered disciplines. 

This enables the educational app to let the user jump into more difficult tasks in order to avoid being bored by the initial low skill tasks.


## Out of scope: 

EduKids launcher manages the (dis)enablement of launching the apps, therefore related launcher internal triggers are not communicated to the app. 

- [ ] // TODO to be discussed


## Other topics to be discussed

- [x] versioning / deprecation of the edutime client library
--> launcher will be able to communicate with older - deprecated library versions

