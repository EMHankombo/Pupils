
# Android Technical Test

Application which retrieves and sends data to Bridge International test Api (https://androidtechnicaltestapi-test.bridgeinternationalacademies.com/swagger/index.html). I used MVVM architecture with repository pattern and RxJava for asynchronous operations. Utilized Room database for local persistence and Koin for dependency injection. For unit tests I used Mockito. 

Assumption is that at least 1 successful API call will be made and the result of that will be used to populate the database. Otherwise, if a database query is made to get pupils before that, it will return an empty list. 

1st (see the list of all pupils) and 2nd (able to add pupils are implemented) requirements are implemented. 3rd requirement (requirements 1 and 2 should continue when I am offline.  With data synchronising when I'm next online) is partially implemented. You can see list of all pupils when offline however being able to add pupils is pending. I would use WorkManager  to sync with the backend in order to implement the final part of 3rd requirement.
