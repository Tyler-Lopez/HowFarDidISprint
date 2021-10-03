<p align="center"><img src="https://user-images.githubusercontent.com/77797048/135735284-01f5575f-918d-42f3-8d66-b5949f4435f5.png" width="300px"></p>

# About
     
This Android application in-development will allow the user to track their fastest 400 meter sprint times day-to-day. This application serves as a personal exploration in both working with the Android Locations API and in persisting user data with Room. The user should open the app, click "Start", then simply run 400 meters. Throughout the run, their current distance, speed, and the amount of seconds which have yet elapsed will be displayed. If the user has not yet sprinted once today, they are encouraged to do so.

In the featured picture, the user has just walked 405 meters in 238 seconds with their last location data reporting a speed of 1.64 meters per second.

Data is persisted, so users may review their historical sprints. By default, the "Sprint History" is sorted ascending by seconds it took them to run 400 meters.

## Features
* Use of AnimatedNavHost navController, MVVM and clean architecture
* Persistent top navigation bar
* Use of Google Locations API
* Utilization of Room persistence library
* Single permission request

## To-Do
* The application must not be paused in anyway while recording data or it will cease to record data. This was a quick fix for another issue which must be resolved in future.
* Add other sprint distances?
* Rebrand to "Mile a day", "Run a day", introduce more streak-based features?
* Publish on PlayStore

## UI Examples
<p align="center">
<img src="https://user-images.githubusercontent.com/77797048/135735287-ae45b395-aa19-4f79-9db1-f12d2b810757.png" width="300px">
<img src="https://user-images.githubusercontent.com/77797048/135764841-cd15936e-f254-49bd-9383-c07a80230650.jpg" width="300px">
<img src="https://user-images.githubusercontent.com/77797048/135764844-3bcb901a-01a8-4ad3-b6f5-494fe85f8dd8.jpg" width="300px">
<img src="https://user-images.githubusercontent.com/77797048/135764845-a69d79e2-70ff-4089-825c-782b4b08b5c4.jpg" width="900px">

</p>
