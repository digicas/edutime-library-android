# Requirements on the information exchange between educational app and edutime client library

## Intro
Edutime client library enables the communication between the educational app and EduKids launcher / mobile management environment.

In order to do that Edutime client library is to be bundled within the education app, where below mentioned features can be invoked via API (and also required callbacks to be registered). 


### Schema of components

```
+----------------------------------------+
|            Kid's mobile                |
|                                        |
|+--------------+           +-----------+|
||   edu app    |           | EduKids   ||
||              |           | launcher  ||
||   +---(API)--+           |           ||
||   |          |           |           ||
||   | edutime  |           |           ||
||   | client   |<--------->|           ||
||   | library  |           |           ||
||   |          |           |           ||
|+---+----------+           +-----------+|
+----------------------------------------+
```

### Information exchange

There are 3 kinds of information to be exchanged:

- [x] User relevant
- [x] Time related
- [ ] Educational progress related

The data exchange between the edutime library and EduKids launcher shall happen every 15 seconds.

Educational app can communicate with the edutime library anytime (via defined API), but can receive up to 15 seconds old information.
Provided information is cached and send to the EduKids launcher on the next sync iteration.

- [ ] // TODO frequency yet to be discussed

Note: The general idea of the implementation of the edutime library is similar to Google Play Game Services (https://developers.google.com/games/services/android/quickstart).

## User related information exchange
Both educational app and EduKids launcher can be run under the different user profiles. We need to assure the below described information is related to the proper user.

#### [APICALL] Get the edutime user ID
Educational app gets the uuid of the EduKids launcher user, under which it can execute further described commands.

note: This uuid shall be generated on the very frequent bases within the EduKids launcher in order to be privacy compliant. Therefore the uuid acts rather as the session id.

- [ ] // TODO discuss the frequency or procedure of the user's uuid generation / usage


## Time related information exchange

Dealing with time and its valuation is the key aspect of the EduKids ecosystem - see [Kategorie času]( https://us20.campaign-archive.com/?u=2435031a45e9f4d33c738c65c&id=e8e15797a6 ) for the related categorisation (in czech).


### App own screentime related

#### [APICALL] Time off warning
Educational app gets the time(date) when the EduKids launcher stops the app due to the switch of the mobile to the more strict time category. 

This enables the educational app to warn its user in advance within its UI.  

#### [APICALL] Time on info
Assuming the educational app has the background process - it gets the time(date) when the EduKids launcher enables the app due to the switch of the mobile to the less strict time category. 

This enables the educational app's background process to differentiate between the quiet and non quiet mode (e.g. for notifications, etc.).  

#### [APICALL] Time category info
Educational app gets the information on under which time category the app is listed by the parent. It also receives the list of categories and their status (un/locked).

This enables to request the different / more relevant category if needed.

#### [APICALL] Time category request
Educational app can request to be listed under the different time category.

This will be propagated to the parent of the user, who might act upon this request.


### Influencing screentime of other apps
Educational apps have the privilege to influence the available screentime for other applications installed on the mobile. This is very strong and responsible privilege.

Kid has to gain / have **timecoins** ("časozlaťáky") in order run other apps (e.g. games, or video consumption apps).

We know that edu apps / games use some sort of achievemnts / badges, etc. (e.g. stars, virtual money) to show and motivate on the progress of the user.

EduKids cannot rely on this kind of information therefore the following is established.

The overall idea is that the educational app reports currently gained **edupoints** to the EduKids launcher. 
EduKids launcher translates those edupoints into the **timecoins** based on the rules and preference set by the parent (some coefficient function defined per educational app).
Timecoins can be further used for unlocking the time categories or spending on screentime of the other apps within certain time timecategory (as defined by parents).

The definition of under which conditions the app's edupoints are gained and their amount is fully up to the app developer. It can be related to the gained levels, stars, virtual money, etc.

#### [APICALL] Timecoins info
Educational app gets the info on current amount of timecoins the kid has available.

This enables the educational app to show this amount in its UI.

#### [APICALL] Report gained edupoints
Educational app reports the amount of edupoints gained since the last successfull edupoints report call.

Returns timepoints gained (as a deffered Promise / Future - deffered up to the sync time). 

This enables the educational app to show currently gained **timecoins** in its UI as the reward.

- [ ] // TODO clarify potential sync frequency issue


## Educational progresss related information exchange

- [ ] // TODO

## Out of scope: 

EduKids launcher manages the (dis)enablement of launching the apps, therefore related launcher internal triggers are not communicated to the app. 

- [ ] // TODO to be discussed
