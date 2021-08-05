<img src="https://user-images.githubusercontent.com/2817833/119845185-e8471100-bebd-11eb-85f9-d5c7d3a7aa78.png" widht="400"/>

# Scout 

![Build Status](https://github.com/abhishekdewan101/GameTracker/actions/workflows/android.yml/badge.svg)
[![Android Release Build](https://github.com/abhishekdewan101/Scout/actions/workflows/android-release.yml/badge.svg)](https://github.com/abhishekdewan101/Scout/actions/workflows/android-release.yml)


Scout is a project aimed at developing an application that helps iOS and Android users keep track of their ever growing game libraries and then help them make decisions on what they should be playing next.



https://user-images.githubusercontent.com/2817833/121840417-0c159f80-cc91-11eb-9cb4-5317ba61e6ad.mp4


## Download

<a href='https://play.google.com/store/apps/details?id=com.abhishek101.gamescout&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="250"/></a>

## Features

- Ability to search for games using [IGDB's](https://api-docs.igdb.com/) api
- Create custom lists that you organize your games into.
- Personalized results based on platforms you own and genres you like.
- Personal stats on what types of games you like to play, how many hours you've played, etc.

## Getting Started
1. Clone the repo.
2. In the root of the project create a local.properties files where you would be adding your authentication params.
3. Follow the steps [here](https://api-docs.igdb.com/#account-creation) to setup a Twitch Dev Account and create a ClientId and ClientSecret.
4. In local.properties please add the following
    ```
    useTwitchAuthentication=false
    clientId=<clientId>
    clientAuthenticationUrl=https://id.twitch.tv/oauth2/token?client_id=<clientId>&client_secret=<clientSecret>&grant_type=client_credentials
    ```
5. Run the application and you should be able to authenticate the app using your twitch creds and use the app.

## Technologies Used

Game Tracker is suppose to be a showcase for the following things:

- Kotlin Multiplatform Mobile
- Jetpack Compose
- Swift UI

### Kotlin Multiplatform Mobile

WIP

### Jetpack Compose

WIP

### Swift UI

WIP
