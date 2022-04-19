## Architecture

This project follows concept of [Clean Architecture](http://five.agency/android-architecture-part-1-every-new-beginning-is-hard/).

As you can see below, modules are arranged from top to bottom. A module doesn't have access to anything **above** them. However, a module has access to anything **below** them.

app

↓

data (can have network & local submodules)

↓

domain

### Modules
#### app
This module contains UI related classes and resources.

##### Classes that reside in app module
- Activities
- Fragments
- UIModels
- ViewModels
- resources (e.g. layout, navigation, strings, menu, styles, etc.)

#### data
This module is responsible for providing data for the app. All database and api call implementations **must only** reside here. 

Each feature contains domain's `Repository` implementation and a sub package for its network and persistence implementation.

##### Classes that reside in data module
###### Persistence 
- Database (e.g. Room)
- DBModels (e.g. ArticleDBModel)
- SharedPreferences
- Implementation of domain's `*Repository` interfaces (e.g. ArticleLocalSourceImpl)

###### Network
- Retrofit
- OkHttp
- Request classes
- Response classes (e.g. GetArticleResponse)
- DTO - [Data Transfer Objects]((https://softwareengineering.stackexchange.com/a/171480)) (e.g. ArticleDTO)
- Implementation of domain's `*Repository` interfaces (e.g. ArticleRemoteSourceImpl)

### domain
A pure kotlin module (must not have android dependencies). This module contains app's use cases. The app's different functionalities can easily be understood just by looking at the use cases inside this module.

Each feature contains `Repository` interfaces (to be implemented by `data` module) and it's use cases.

The use cases inside this app must be agnostic in a way that it should have no idea where the data is coming from and doesn't care about the underlying implementations.

**Example**:
Let's say we have  `GetArticleUseCase`, the use case should only expect the `Repository` to return `Article` and nothing more. It should not care about the underlying implementations on how the `User` was retrieved.

##### Classes that reside in domain module
- Domain models (e.g. Article, Session, etc.)
- Use Cases - used in `app` modules ViewModels.
- Repository Interfaces (to be implemented by `data` module)
- Other classes that are used globally in the app can be stored here too.
