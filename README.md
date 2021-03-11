# technews
Technews is a work-in-progress app that lists tech related articles from different sources powered by [News API](https://newsapi.org/). Technews allows you to bookmark articles for future reading.

## Getting Started
### Development setup
Android Studio 3.0 or newer.

### API Keys
You need to obtain an API key from [News API](https://newsapi.org/).

#### Placing API Key inside the project.

1. After obtaining the API Key, in your Android Studio. Create a `build.properties` file at the root level of your project and place the API key inside like this:
```
NEWS_API_KEY="inser_your_api_key_here"
```

2. Open `build.gradle` file inside `data` module and paste this block of code just above the `android {` block.
```
def buildPropertiesFile = rootProject.file("build.properties")
def buildProperties = new Properties()

buildProperties.load(new FileInputStream(buildPropertiesFile))
```

Keep `build.gradle` open for the next step.

3. Lastly, inside the `android {` block, you can find `defaultConfig {` block. Paste this line inside `defaultConfig {` block.
```
buildConfigField "String", "NEWS_API_KEY", buildProperties['NEWS_API_KEY']
```

And you're all set!

## 
### Built with 
* [Clean Architecture](http://five.agency/android-architecture-part-1-every-new-beginning-is-hard/) - Architechture pattern that separates business logic with other components of the app. (Reading this beautiful article in the link provided would help a lot!)
* [Kotlin](https://kotlinlang.org/) - App is purely written in Kotlin.
* [Retrofit 2](http://square.github.io/retrofit/) - For handling network requests.
* [Hilt - Dagger](https://dagger.dev/hilt/) - Dependency injection framework.
* [Gson](https://github.com/google/gson) - For serializing/deserializing JSON objects.
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html) - For persisting data.
* [Paging](https://developer.android.com/topic/libraries/architecture/paging) - For pagination.
* [Glide](https://bumptech.github.io/glide/) - Image loading library.
* [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics/) - For crash reporting.
* [Mockito](https://github.com/mockito/mockito) - For mocking objects in unit testing.
* [Mockito-Kotlin](https://github.com/nhaarman/mockito-kotlin) - Helper class for using mockito with kotlin.
* [jUnit](https://junit.org/junit4/) - For unit testing.

### Todo
* Build unit tests
* Local pagination in Bookmarks screen
* Add sources screen to allow users to pick news sources that they want.

### Inspiration
To enhance my knowledge and skills in developing better android apps.

## Contribution
If you found an error. Filing an issue together with submitting a pull request (through forking the app) would be cool.


<a href='https://play.google.com/store/apps/details?id=jermaine.technews&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height=150 width=400/></a>
