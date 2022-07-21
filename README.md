
# Most Popular News

Most Popular News is an Android application consuming the [NY Times API](https://developer.nytimes.com/)
to display most popular news over the last 1,7 or 30 days. The app follows clean architecture principles
and uses MVVM with the Android architecture components dependency injection is used in application using Hilt Library.


## Project Setup

1. Clone the repo using ```https://github.com/faiz786/MostPopularNews.git```
1. Open the project in Android Studio
1. Generate a [NY Times API Key](https://developer.nytimes.com/get-started)
1. Add the API Key to your local `gradle.properties` file with the key `NY_TIMES_API_KEY`
   and the value of your API Key e.g `NY_TIMES_API_KEY="cjCe8546151551894561s"`
1. Sync and build your project.
1. Hit "Run". Done!
   Note:Api Key is Already Added in gradle.properties, if you want to add new api key generate api key on new york times dev portal and paste the key in gradle.properties
## Architecture Overview

The application has three layers guided by clean architecture to provide clear
separation of concerns:

![Three layer architecture](https://developer.android.com/topic/libraries/architecture/images/mad-arch-domain-overview.png)

The Application is Divided into
#UI containing Activities Fragments And ViewModels for handling the states of UI
#domain for handling models repositories
#data which is divided into local and remote local being Room database for showing news in offline cases and remote data is handled through retrofit



### Running the tests

The application contains unit tests that run on the local machine and instrumented tests that
require the use of connected Android device.

1. Run the task `./gradlew connectedCheck` to run both local unit tests and instrumented tests
1. Run the task `./gradlew test` for local unit tests
1. Locate the test coverage results at `/app/build/reports/coverage/androidTest/debug`
1. Locate the local unit test results at
    - HTML test result files at `/app/build/reports/tests/`
    - XML test result files at `/app/build/test-results/`
1. Locate the instrumented test results at
    - HTML test result files at `/app/build/reports/androidTests/connected/`
    - XML test result files at `/app/build/outputs/androidTest-results/connected/`

Note: To Run Test and generate Report You can Better Do The Following Steps instead of above commands
For Running Test Right Click On Test Folder inside src directory and click Run Tests in MostPopularNews
you can locate test report at app>build>reports>index.html
For Generating Code Coverage Report Right Click on Test Folder inside src directory and click Run Tests in MostPopularNews with coverage
after it finishes you will see report on the right hand side of android studio there you need to click generate coverage report on icon with arrow
there you need to specify output directory and you need check mark open generated html in browser to view report immediately in browser
it looks something like this
![ScreenShot](/Screenshots/coverage_report.png)
## Libraries


Libraries used in the whole application are:

- [Jetpack](https://developer.android.com/jetpack)🚀
    - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
      and act as a channel between use cases and ui
    - [View Binding](https://developer.android.com/topic/libraries/data-binding) - View binding is a feature that allows you to more easily write code that interacts with views
    - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
    - [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation support for Android
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box
- [Gson](https://github.com/square/moshi) - Library that can be used to convert Kotlin Objects into/from their JSON representation
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Threads on steroids for Kotlin
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.🔥🔥
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [mockK](https://mockk.io/) - Mocking framework that comprehensively supports Kotlin features
- [JUnit Rules](https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/rules) - JUnit rules provide more flexibility and reduce the boilerplate code required in tests
- [Glide](https://github.com/bumptech/glide) - Media management and image loading framework for Android
- [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
