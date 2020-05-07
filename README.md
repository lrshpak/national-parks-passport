# National Parks Passport
This was a project I did in my software development for handheld devices class in Spring 2019

#### Components
- [DarkSky API](https://darksky.net/dev)
- [National Parks API](https://www.nps.gov/subjects/digital/nps-data-api.htm)
- [Firebase](https://firebase.google.com/?gclid=EAIaIQobChMIgfqc5OWg6QIVA4rICh1bwQrhEAAYASAAEgJ8W_D_BwE)
- Android Alcatel Phone
- Kotlin

#### How it Works
When the user opens up the app they are prompted to either create an account or log into an existing account. All user information is stored using Firebase. Once the user is in the system they can type in a state in the United States. Then using the National Parks API, the app queries all the national parks in that state, they are displayed in a recycler view. The user can then select a park and using the National Parks API again, the app queries the address and information about the park. The user can then click a Weather button and using the Dark Sky API the app will show current weather information for that park. The information displayed is that which might be important for hiking or being outside, it shows visibilty, humidity, temperature, and the forecast

#### Features I would like to add
- The user can save a national park so they can easily retrieve it
- The app will show pictures from the park the user chooses
- The app can show driving directions to the park
- The app will give information about park fees
