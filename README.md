## Build Steps
1. Add the following keys to your local.properties file. They can be obtained from registering in developer.marvel.com
```groovy
marvelPrivateKey={YourPrivateKey}
marvelPublicKey={YourPublicKey}
```
2. Run the project. Devices I used for testing: Pixel 3a and Pixel 7 Pro.
3. Recommendation is to view the application in DarkMode as that was the focus.

## Requirements
- [x] Provide a means of loading a requested comic id and displaying a detail page. 
- [x] Provide examples of ui and unit test. UI test can be found in the **ComicDetailScreenTest**. Unit test can be found in the api module.
- [X] Provide list of dependencies (this can be found in the `Libraries Used` Section).
- [X] Provide build steps for adding keys locally. 

## Feature List
- Loads Requested Comic Id. Has UI states for success, failure, loading.
- Supports offline viewing as comic results are stored in a local db.
- Supports light/dark mode
- Supports landscape orientation (Design could be improved if there's a reference to use for the orientation)

## Libraries Used
- Ktor as the library used for http client / network traffic: https://ktor.io/docs/client-create-and-configure.html#close-client
- Coil as the image loading library. Lightweight: https://github.com/coil-kt/coil
- Hilt for dependency injection: https://developer.android.com/training/dependency-injection/hilt-android
- Room for offline db storage: https://developer.android.com/training/data-storage/room
- The rest of the dependencies are standard for Android compose and ui-testing.

## Application Composition
Modules: app, api

![Arch Diagram](https://github.com/EpiphX/marvel/blob/main/docs/app-composition-diagram.png)

### App Module

#### UI Package
- The ViewModels, Components, and Screens. All of the app's presentation/rendering logic.

#### Data Package
- The repositories that encapsulate the data sources for rest / storage interaction. Currently only ComicsRepository is supported.

### Api Module

#### MarvelSDK
- Encapsulates the Rest endpoints, Business logic of generating hash, and transformation of images into requested size.
- The idea of this SDK is that it could eventually be converted to be multi-platform, and not glued to a specific UI implementation or platform. 

## Future Improvements if more time
- Improve theming on light mode.
- Improve navigation logic.
- Improve landscape design.
- Add a paginated comic list, or maybe a search, to make it easier to find comics to display.
- For the detail screen, we could add a swipe gesture to navigate between comics.
- For the application, it could have a way for the user to clear the local db.
