This is a news client application which retrieves the latest articles from this endpoint https://newsapi.org/v2/everything

It follows the **MVVM** architecture and **Google Architecture Guidelines**. Where the model is a **Repository** responsible for retrieving and updating data from network (through **NewsApi**) and database (through **ArticlesCache**).

For network handling I've used **Retrofit** (with **RxJava** Call Adapters, and **Gson** for json parsing). 

For database I've used **Room**.

For image downloading I've used **Glide**. 

For the communication between the view and the model I've used Android **ViewModel** and **LiveData**, introduced in **Google Architecture Component**. 

For dependency injection I've implemented a very simple ServiceLocator (**Injection.kt**).

And for the unit testing I've used **Junit, Mockito and Mokito-Kotlin.** 

The app is written 100 percent in Kotlin, and it follows SOLID principles and reactive programming concepts as much as possible.

**Notes:**

I first thought of using Android new Paging library, but I've faced a few performance and architectural issues, so I've implemented the pagination natively. Non-complete samples of the approach of using Paging library can be found on those branches https://github.com/EslamAbdelkader/News/tree/firstApproach https://github.com/EslamAbdelkader/News/tree/secondApproach

The application's minSdkVersion is 24, because it uses kotlin.forEach which uses java streams that are only supported starting API 24.
