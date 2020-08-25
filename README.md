# Squash-Video-Analysis
Android Application Repo


# Description 
PS : The app's interface works best on Pixel 3 API 29 emulators but I'm trying to refine the interface to be flexible and suit all 
emulator sizes. Reason being the app is client based and was oriented to the client's specific device. I am currently developing more knowledge on interface design to optimize the design :)

Android Application, developed jointly with professional squash athlete and elite coach Ahad Raza. 
It is designed to help professional squash athletes improve their game through detailed video analysis and real time coaching metrics.
The app is designed specifically for Ahad's coaching business to make its operation more efficient and less error prone. Processes that were previously done on paper are now automized. A common practice for squash coaches is to review match videos of the athletes they train and record a set of statistics that evaluate the player's performance. It is often a tedious task to perform by hand and is why this app is the perfect solution.

The application features an XML interface that models a squash court that coaches can interact with to record the shots hit by players along the court's dimensions. It records details about where each shot was hit as well as its type and automatically outputs the metrics (Avg. volleys hit, Winners to errors ratio, etc...) based on the data input. 

# Prerequsites 
To run the application, you will need to install Android Studio 3.5.3 (latest version at time of development) and 
pull the project from github to a local repo. Afterwards, open the project from the top left button in android
studio. You will find that your left bar with the title project is now populated with a bunch of folders. 

Most importantly, the app folder contains most of the work used for development. At the top of the screen, 
you will find a dropdown tab with title Unknown Device. Open that tab and create a virtual device which allows you to 
run the app on an android emulator (for example, create a Nexus 5 device with Android 10.0 API 29 option). 

# Tests 
After successfully running the app, you will find that a squash court interface pops up. Now, you can start a rally and 
record the different shots and their type made by a player you wish to analyze. Repeat this process until you finish a rally then click 
rally end. After successfully inputing information for a full game, click on end game and a set of metrics will appear that analyze the 
player's performance based on the information given. Return to main screen to start a new game. 

# Tools 
Android Studio - Java - Google Firebase

# Acknowledgment 
Special to thanks to professional trainer and squash coach Ahad Raza who has offered me the opportunity to work on this project and 
provided the background information necessary to analyze the data. Check out his new website and definetley try out some of his training
programs which can be found at https://www.arproformance.com/
