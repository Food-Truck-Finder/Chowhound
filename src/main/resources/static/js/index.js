var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
var infoWindow;

// prompt for user location, center map, add markers
function initMap() {
    geocoder = new google.maps.Geocoder();
    map = new google.maps.Map(document.getElementById('map'), {
        center: "600 Navarro Street, San Antonio, TX, USA",
        zoom: 12
    });
    infoWindow = new google.maps.InfoWindow;

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map.setCenter(pos);
            var marker = new google.maps.Marker({
                map: map,
                position: pos,
                animation: google.maps.Animation.DROP,
                icon: image
            });

            for (var i = 1; i < Object.entries($(".addressjs")).length - 1; i++) {
                geo(i);
            }
        }, function () {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

// add marker function
function geo(i) {
    geocoder.geocode({'address': Object.entries($(".addressjs"))[i][1].innerHTML}, function (results, status) {
        if (status == 'OK') {
            var contentString = '<div id="content">' +
                '<div id="siteNotice">' +
                '</div>' +
                '<h1 id="firstHeading" class="firstHeading">' + Object.entries($(".namejs"))[i][1].innerHTML + '</h1>' +
                '<div id="bodyContent">' +
                '<p>' + Object.entries($(".descjs"))[i][1].innerHTML + '</p>' +
                '<p>' + Object.entries($(".addressjs"))[i][1].innerHTML + '</p>' +
                '<p><a href="http://localhost:8080/trucks/' + i + '">' +
                'Visit truck</a> ' +
                '</p>' +
                '</div>' +
                '</div>';
            var infowindow = new google.maps.InfoWindow({
                content: contentString
            });
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location,
                animation: google.maps.Animation.DROP
            });
            marker.addListener('click', function () {
                infowindow.open(map, marker);
            });
        }
    });
}

// user did not allow browser to use there location
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    infoWindow.open(map);
}