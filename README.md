# Scout 

![Build Status](https://github.com/abhishekdewan101/GameTracker/actions/workflows/android.yml/badge.svg)


Scout is a project aimed at developing an application that helps iOS and Android users keep track of their ever growing game libraries and then help them make decisions on what they should be playing next.


https://user-images.githubusercontent.com/2817833/115657460-c7eacc00-a2eb-11eb-82fb-9be253d91bed.mov



## Features

- Ability to search for games using [IGDB's](https://api-docs.igdb.com/) api
- Create custom lists that you organize your games into.
- Personalized results based on platforms you own and genres you like.
- Personal stats on what types of games you like to play, how many hours you've played, etc.

## Read More

I sometimes blog about my journey in this project at my blog [learning-to-code](https://abhishekdewan101.github.io/learning-to-code/)

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
