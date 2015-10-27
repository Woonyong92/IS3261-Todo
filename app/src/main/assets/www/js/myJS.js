// default values for latitude and longitude

var lat = 1.3667;
var lng = 103.75;

var markers = [];

// drop down list

var locName;

document.getElementById("locationName").addEventListener("change",
  function(){
	  locName = document.getElementById("locationName").value;
	  switch(locName) {
		case "Singapore":
		   lat = 1.33;
		   lng = 103.83;
		   break;
		case "Pittsburgh, USA":a
		   lat = 40.4417;
		   lng = -79.89;
		   break;
	    case "Rome, Italy":
		   lat = 41.9;
		   lng = 12.4833;
		   break;
	    case "Florence, Italy":
		   lat = 43.7667;
		   lng = 11.25;
		   break;
		case "Pisa, Italy":
		   lat = 43.7167;
		   lng = 10.3833;
		   break;
		case "Siena, Italy":
		   lat = 43.3333;
		   lng = 11.3333;
		   break;
	    case "Venice, Italy":
		   lat = 45.4383;
		   lng = 12.3267;
		   break;
		case "Paris, France":
		   lat = 48.8667;
		   lng = 2.3333;
		   break;
		default:
		   lat = 1.33;
		   lng = 103.83;
		   break;
	  }
	  document.getElementById('inputLat').value = lat;
	  document.getElementById('inputLng').value = lng;
  }, true);




// show google map

var myOnlyMap = new google.maps.Map(
   document.getElementById("myMap"), {
	   zoom: 15,
	   center: new google.maps.LatLng(lat, lng),
	   mapTypeId: google.maps.MapTypeId.ROADMAP }
);

// Button to go to app

document.getElementById("start").addEventListener("click", goToApp, true);

// go to new location and show marker
function goToApp() {

   function validClick()
   {
      valid.performClick();
      document.getElementById("ok").value = "J'accepte";
   }




// Button for showing city map and weather

document.getElementById("go").addEventListener("click", goNewLocation, true);

// go to new location and show marker
function goNewLocation() {

	var newLat=document.getElementById('inputLat').value;
	var newLng=document.getElementById('inputLng').value;

	lat = newLat;
	lng = newLng;

	myOnlyMap.setCenter(new google.maps.LatLng(lat,lng));
    //map.setCenter =  new google.maps.LatLng(lat, lng);
	myOnlyMap.setZoom(15);

	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat,lng),
		map: myOnlyMap
	});

    markers.push(marker);


    var cityName = locName;

    var theUrl = 'http://api.worldweatheronline.com/free/v1/weather.ashx';

    var paramString = 'q='+ cityName + '&format=json&num_of_days=5&key=b4b555e71b24530b342bc2bac7df12d9401885fd';

    var xmlHttp = null;
    xmlHttp = new XMLHttpRequest();


	xmlHttp.onreadystatechange = function() {//Call a function when the state changes.
	// note: if async == false, do not write this function
        if (xmlHttp.readyState == 4) {
            //if (xmlHttp.status == 200 || xmlHttp.status == 0) {
            if (xmlHttp.status == 200) {
               var JSONDataPost2 = JSON.parse(xmlHttp.responseText);

               document.getElementById("JSONText").innerHTML =
                                                "Asynchronous GET <br \>" +
                                                "Temperature (deg C) in " + locName + " = " + JSONDataPost2.data.current_condition[0].temp_C + "<br \>" +
                                                "Temperature (deg F) in " + locName + " = " + JSONDataPost2.data.current_condition[0].temp_F;

            }
        }
    }

    getString = theUrl + "?" + paramString;

    xmlHttp.open( "GET", getString, true );   // true means asynchronous

	xmlHttp.send(null);

}


// buttons for markers


// show marker
function setMarker() {
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat,lng),
		map: myOnlyMap
	});

    markers.push(marker);
}

// after clicking the button, put a marker on current location

document.getElementById("marking").addEventListener("click", setMarker, true);

// after clicking this button, delete all markers
document.getElementById("clearMarker").addEventListener("click", function() {
	for (var i=0; i<markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}, true);

