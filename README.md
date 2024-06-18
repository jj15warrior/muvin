# muvin app - the ultimate toolkit for BLE wardriving, scanning, research and alerts.

inspiration: rfparty-mobile. the rfparty app is not mantained for over 2 years at the time of creating this project,
so i decided to code my own version. In current times mobile devices are much more capable, so we can increase certain
parameters and increase the overall usability of the app. RFParty lacks many filters, selectable text and is in general
very annoying to use.


how muvin BLE works: there are 3 main threaded components. The main process draws the map, updates graphs and loads data from the ROOM
database. Thread No. 2 Abuses android's built-in BLE scanner on max settings which is <b>NOT</b> recommended by the android documentation.
It causes massive energy usage so i highly recommend using this app with an external power supply like a power-bank or a car charger.
Process No. 3 categorizes data using algorithms and pushes that data to a SQLite database via the Android ROOM wrapper.

TODO: data categorization models - 
<pre>
text tokenization and proximity
optimal graphics
data saving (i suck at SQL, but will commit soon)
cool icons and UI changes
alert system, not for malicious usecases of course 😏🚓
pause scan button
</pre>