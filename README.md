# SnackTruck

## Running the app

1. The app should run on any device with ICS or newer. I was only able to test on my own device, which runs Pie (SDK 28).

2. Similarly, the UI tests were only done on my phone, so it is possible that they will fail on other devices.
    - There are some areas where is should have used `onData()` rather than `onView()`, which can cause tests to fail on smaller or landscape devices.

3. Otherwise, usage should be pretty intuitive based on the specs.

4. Exceptions:
    - Pressing "Submit" without any items checked will not do anything
    - Adding new items clears the check boxes
    - New snack items are added to the end of the list, regardless of category
    - Closing the app will clear the added items
    
## Test Plan

1. Unit test as much as possible. Howeer, since the backend is mocked, most of the app is UI, so there wasn't much I was able to Unit test. 
Many things that were testable through UTs would also be covered by UI tests, for example adding new snacks. With more time, I would add more unit tests even if UI tests would cover them, since unit tests are lower cost and would presumable be run more often, catching mistakes earlier.

2. UI testing is done through [Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing) and [Hamcrest matchers](https://github.com/hamcrest/JavaHamcrest). I use them because they were the most popular and therefore the most supported, including official Google support. Espresso atutomates UI actions and can do assertions, using Hamcrest to match UI elements.

3. Test plan for UI is based off of what I would have done manually if I wanted to make sure the app worked. Each of the app's three priorities are tested in a separate class, mirroring their development. Within each priority, first I test basic functionality (eg, ordering a single item). Then I progress to more edge cases and complex behaviors, paying attention to previously completed parts of the app when new parts are added, making sure to update those to make sure they interact properly with the new features.

4. For this homework, I was not able to add all the testing I would have wanted. However, since I knew I wouldn't have enough time from the start, I had decided beforehand which tests I wanted to write, which allowed me to write the tests in a different order than I otherwise would have. 
    - If I had more time, I would test more of the interaction between filtering menu or adding items and ordering them, with focus on making sure the currently selected items remain the same through filter changes and possible menu changes. I would also do more tests with key elements in scrolling views not visible.
    - Conversely, if I had less time, I would skip some of coverage tests (eg, testing every combination of filters). I would focus on just showing one or two tests out of a group of very similar tests to show the idea behind them. This also makes it more practical to inline functions and hardcode values, which should result in code that is written faster, though less future-proof
    
## Other libraries used
1. [https://github.com/cbeust/klaxon](klaxon): A Kotlin parser for JSON. I hardcoded the snack items in a JSON file for more flexibility.
2. I also referenced some of my other [Android code](https://github.com/calvin-li/Quest), particularly for the code for the Alert Dialogs. 

&nbsp;


* For Priority three (adding new items), I would try to understand why adding items needed to be done on the same devices that ordering is done on, especially since the idea seems to be to give frontline employees the ability to add to the menu. If it really was necessary, then I would propose adding an authentication system for adding new items, leveraging third party systems like Google Auth.
