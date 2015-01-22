# couponduniatest

Here are the list of things done in this project...

On launching the app it will track the latitude and longitude of my current location from the class "GPSTracker " service  which will track the location and compute the latitude and longitude using GPS provider.

On every launch of the app it will check for network connectivity.If network connection is on it will start a AsyncTask "LoadData"  where it will fetch the data from the server and write it to a file, parse the JSON response string and  display the data on the  listview on PostExecute .If no  internet connection then it will simply read the data from the file and load it in the ListView.

While fetching data from the server in doInTheBackground it creates an object of a class "JsonProvider" and calls the function "getJsonFromUrl()" from the class JSONProvider where it will open a HTTPConnection to the url and returns the server response after the background job is done.

The server response is stored in the object of class  "ServerResponse.class" where it has fields

=>String response="" to store the JSON response from the server,

=>String error="" to store the error that might have occurred while connecting to the url or if the server return a status code other than 200.

onPostExecute it will check if the server response returned has any error or not, if not then it will simple parse the JSON string, loads the data in the listview using the "ListAdapter" class.

While parsing JSON string it creates an ArrayList of RetaurantInfo class "ArrayList<RestaurantInfo>" and stores the information about each restaurant in this Array-list.

The "RestaurantInfo" class contains fields like:

=>latitude
=>longitude
=>distance= distance form my current location
=>name
=>logoUrl
=>NeighbourhoodPlace
=>offers
=>categories
It creates a new RestaurantInfo object for each restaurant and adds the object to this ArrayList.
The ArrayList is sorted with respect to the distance field and computed the sorted array in the Listview.

The refresh button on the top is used to track the current location and recompute the distance and display the Listview.
I have used the picasso jar file for loading images
