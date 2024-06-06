## Libraries Used
- Ktor as the library used for http client / network traffic. https://ktor.io/docs/client-create-and-configure.html#close-client
- Coil as the image loading library. Lightweight, Great support for Kotlin First: https://github.com/coil-kt/coil
- Hilt for dependency injection https://developer.android.com/training/dependency-injection/hilt-android
- Room for offline db storage https://developer.android.com/training/data-storage/room
- The rest of the dependencies are standard dependencies for compose and ui-testing.

## Application Composition
Modules: app, api

### UI Module
- Consists of ViewModels, Components, and Screens. All of the app's presentation logic for drawing to screen will live here.

### Data Module
- Consists of the repositories that encapsulate the Marvel API's.
- Optionally, could generalize and create a Marvel Kotlin SDK, to make it easier for Kotlin Multi-platform to be adopted by any consuming client.

### Build Steps
- TODO: Need to define the steps of where to set the developer key. This should be in a .env kind of file, so it isn't committed to the repo. 
