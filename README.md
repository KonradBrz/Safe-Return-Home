# Safe-Return-Home
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
Project is intended to catching user current location and sending information of location by e-mail or sms, to the person the user chose. This app is working on the background using Service with AsyncTask and WakeLock to keep the device awake and let the app go working incognito. The app using gps provider for sending messages by sms and network provider for sending messages by e-mail. Unfortanetly at this moment gps location and network location are dupicated. Except that location reader get informations about altitude, accuracy, speed and informations about current weather conditions for current location.

## Technologies
* Retrofit - 2.4.0
* SQLite

## Setup
Safe Return Home is available on Google Play 
https://play.google.com/store/apps/details?id=com.tashfaelapp.konrad.safereturnhome

## Code Examples
Show examples of usage:

```
Date currentTime = Calendar.getInstance().getTime();
String str = (String) currentTime.toString().subSequence(0, 19);
```

## Future features

* Firebase instead SQLite
* More functions if comes about Google Maps api

To-do list:
* Organize the project (DRY)
* Organize the project (single responsibility)

## Status
Project is: _in progress_

## Inspiration
Inspiration was care of family

## Contact
konrad.gilewski@gmail.com
