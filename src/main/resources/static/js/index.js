var flag = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
var truck = '../images/chowhound-finalfinalcolor (1).png';
var infoWindowIsOpen = false;
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
                icon: flag,
                zIndex: 7
            });
            for (var i = 0; i < Object.entries($(".addressjs")).length - 1; i++) {
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
            var contentString = '<div id="content" style="background: radial-gradient(circle, rgba(255,255,255,1) 0%, rgba(255,240,240,1) 0%, rgba(255,255,255,1) 100%); padding: 5px; border-radius: 5px; border-bottom: 1px #fddddd solid;\n' +
                '    border-top: 1px solid #fddddd;">' +
                '<h3 id="firstHeading" class="firstHeading text-center" ><a style="font-family: \'Bangers\', cursive, bold;\n' +
                '    color: firebrick" href="https://chowhound.rocks/trucks/' + Object.entries($(".idjs"))[i][1].innerHTML + '">' + 'Visit ' +
                Object.entries($(".namejs"))[i][1].innerHTML + '</a></h3>' +
                '<div id="bodyContent" style="">' +
                '<p style="font-size: 1em; font-weight: bold; text-align: center; margin-bottom: 3px">' + Object.entries($(".addressjs"))[i][1].innerHTML + '</p>' +
                '<p style="font-size: 1.5em; text-align: center;\n' +
                '; padding: 3px; border-radius: 5px;">' + Object.entries($(".descjs"))[i][1].innerHTML + '</p>' +
                '</p>' +
                '</div>' +
                '</div>';
            var infowindow = new google.maps.InfoWindow({
                content: contentString
            });
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location,
                animation: google.maps.Animation.DROP,
                icon: truck,
                zIndex: 5
            });
            marker.addListener('click', function () {
                infowindow.open(map, marker);
            });
            map.addListener('click', function () {
                infowindow.close();
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