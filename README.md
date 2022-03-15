# Game Listing

Games listing app that list console games from [rawg.io](https://rawg.io) and store it into database then display to user.

## Screenshots

* ### Phone:

<p float="left" align="center">
<img src="/screenshots/phone_demo.gif" width="40%"/>
</p>

* ### Tablet:

<p float="left" align="center">
<img src="/screenshots/tablet_demo_1.png" width="45%"/>
<img src="/screenshots/tablet_demo_2.png" width="45%"/>
</p>

<p float="left" align="center">
<img src="/screenshots/tablet_demo_3.png" width="90%"/>
</p>

## Features

- [x] Modularization
- [x] Persistence
- [x] Pagination
- [x] Material Design
- [x] Support for Tablets (Multi Pane)
- [x] Rotation Support
- [x] Unit / UI Testing (Partial)
- [ ] Memory cache

## Need to know

- The app is offline first and I used `RemoteMediator` from `Paging` library with `Room` to help achieving SSOT (Single Source of Truth).
- There's a unit test on API in the `data` layer.
- Used clean architecture with MVVM to achieve code separation, easy testing, maintainable code and more. Also MVVM have a huge support from Google
  and the community.
- What I liked the most in the task is:
    + Multi screens support, because it's nice to see an app that works on almost any screen device.
    + Pagination using `RemoteMediator` was fun because when I was working on it I discovered that it handles most of the work I need to do to achieve
      the offline first feature.

## What to improve if I have time

- There's issue in mediator `LoadState` cause to show error screen when there's no internet connection when the data is already cached instead of
  showing it directly.
- The pagination not working when first time storing in db, you must close the app and reopen it again.
- The app is almost ready for the store, only a few bugs to fix if I have more time.
- Because of limited time this is other layers test cases from previous projects, examples:

```kotlin
// Define Mockito as a test runner
@RunWith(MockitoJUnitRunner::class)
class UseCaseTest {

  // Custom rule to switch all RX schedulers to trampoline 
  @get:Rule val immediateRule = RxImmediateSchedulerRule()

  // Mocking the repository and entity
  @Mock private lateinit var repository: Repository
  @Mock private lateinit var entity: Entity

  @Test fun `when use case called return complete`() {

    // Act
    whenever(repository.getData())
      .thenReturn(Single.just(listOf(entity)))

    // Assert
    UseCase(repository).build()
      .test()
      .apply {
        assertComplete()
        dispose()
      }
  }
}
```

```kotlin
// Define Mockito as a test runner
@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {

  // Rule to handle LiveData
  @get:Rule val executorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ViewModel

  // Mocking the UseCase and LiveData
  @Mock private lateinit var useCase: UseCase
  @Mock private lateinit var liveData: Observer<Resource<List<UIModel>>>

  // To capture the values from LiveData
  @Captor private lateinit var captor: ArgumentCaptor<Resource<List<UIModel>>>

  // Initializing the test dependencies
  @Before fun setup() {
    viewModel = ViewModel(useCase, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @Test fun `call getData() then return success with two states`() {
    viewModel.liveData.observeForever(livaData)

    // Act
    val entity = Entity("", "", "", "", "", "", 0L, 0L)

    whenever(useCase.build())
      .doReturn(Single.just(listOf(entity, entity)))

    viewModel.getData()

    verify(livaData, times(2))
      .onChanged(captor.capture())

    // Assert
    captor.allValues.apply {
      assertEquals(LOADING, first().state)
      assertEquals(SUCCESS, last().state)
      assertEquals(2, last().data?.count())
    }
  }

  // Remove observers
  @After fun tearDown() {
    viewModel.liveData.removeObserver(livaData)
  }
}
```

```kotlin
// UI Test using Espresso
class MainActivityTest {

  @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)

  @Before fun setup() {
    IdlingPolicies.setIdlingResourceTimeout(15, TimeUnit.SECONDS)
  }

  @Test fun click_on_menu_item() {
    onView(withContentDescription("More options")).perform(click())
    onView(withText(R.string.action_sort_stars)).perform(click())
  }

  @Test fun check_shimmer_loading_is_visible() {
    onView(withId(R.id.mainShimmerLoadingInclude))
      .check(matches(withEffectiveVisibility(VISIBLE)))
  }

  @Test fun check_refresh_layout_visibility_then() {
    waitUntilViewIsDisplayed(withId(R.id.mainRefreshLayout))

    onView(withId(R.id.mainRefreshLayout))
      .check(matches(withEffectiveVisibility(VISIBLE)))
      .perform(swipeDown())
  }

  @Test fun expand_collapse_recycler_view_items() {
    waitUntilViewIsDisplayed(withId(R.id.mainRefreshLayout))

    onView(withId(R.id.mainRecyclerView))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()),
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()),
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()),
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()),
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()),
      )
  }
}
```

## Comments

- The task was very good and challenging, made me to do some research to apply the best practices.
- It have a lot of requirements but on the other hand I was free to determine the time.
- In my point of view I don't think we need the memory cache feature since we store in db, but it can done easily by passing configured `Cache` object
  to `OkHttpClient` that used by `Retrofit`.
- A side note, I estimated the task on the main features including pagination without the other bonus requirements but I figured that making most of
  bonus features will be good at the end.

## API Key

Get one from [rawg.io/apidocs](https://rawg.io/apidocs) and put it inside `local.properties` like this `API_KEY="api_key_here"`

## Built With

* Kotlin
* MVVM
* Clean Architecture

## Libraries

* RxKotlin
* RxAndroid
* Dagger (Hilt)
* Room
* Paging 3
* Retrofit
* gson
* okhttp
* Timber
* Glide
* Mockito
