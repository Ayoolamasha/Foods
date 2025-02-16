**FILE STRUCTURE**
The file is structured by features where each related is placed in the same package.
  - apiService: This package contains the retrofit apiservice implementation
  - common: This package contains util files and classes that can be used everywhere in the app
  - di: Contains dependency injection classes
  - entryPoint: This is the entry point into the app. It contains the MainActivity and the BaseApplication class
  - feature trips: This contains the fragments for the trips feature
  - network: This contains the network files

**PROJECT SET UP**
- Android Studio
- Gradle version 7.5.1
- Kolin version 1.6.0

**Tech Stack/LIBRARIES USED**
Written in Kotlin.
Hilt for dependency injection.
Jetpack Compose for UI and navigation.
Kotlin Coroutines for threading.
Retrofit for communicating with the API.
Kotlin Flows reactive network calls.

**ENDPOINT**
The app contains four different endpoints which are managed by the BaseUrlInterceptor class 
  - Trips endpoint: This is used to create and fetch trips
  - Flight endpoint: This is used to get flight details
  - Hotel endpoint: This is used to get hotel details
  - Activity endpoint: This is used to get activity details

**APK LINK**
https://drive.google.com/file/d/1dOL91Uk37n7GxWUobgJ8FxEYT4PoSxDs/view?usp=sharing
    
