"use strict";

/* Only runs filestack API logic if in a window that we have the upload button in */
/* Doing this to avoid console errors and so it doesn't run when not needed */

if ((window.location.href.indexOf("trucks") > -1) && ($("#logoutButton").index() > -1)) {

    const client = filestack.init(fileStackKey);
    const options = {
        onUploadDone: updateForm,
        maxSize: 10 * 1920 * 1080,
        accept: 'image/*',
        uploadInBackground: false,
    };

    /* Get references to the DOM elements */
    const picker = client.picker(options);
    // const form = document.getElementById('pick-form');
    // const fileInput = document.getElementById('fileupload');
    const btn = document.getElementById('picker');
    // const nameBox = document.getElementById('nameBox');
    // const urlBox = document.getElementById('urlBox');

    /* Event Listener */
    btn.addEventListener('click', function (e) {
        e.preventDefault();
        picker.open();
    });

    /* Helper to overwrite the field input value */
    function updateForm(result) {
        const fileData = result.filesUploaded[0];
        console.log(fileData.url);
        $("#imageURL").val(fileData.url);
        showSaveButtonAndHideUpload();
    }
}

/* Placeholder image logic */
/* image will no longer show up if an image uploaded .isPrimary() */
if ((window.location.href.indexOf(".rocks") > -1) || (window.location.href.indexOf("index") > -1)) {
    let numberOfTrucks = $("#mainTruckContainer").children().length;
    for (let i = 1; i <= numberOfTrucks; i++) {
        if ($("#truck_" + i)) {
            if (($("#truck_" + i).children().children().children().children().children().hasClass("active") === false)
                && ($("#truck_" + i).children().children().children().children().children().hasClass("secondaryCarouselImage") === true)) {
                let secondaryToPrimaryImage = $("#truck_" + i).children().children().children().children().children().first()[0].lastElementChild.currentSrc;
                $("#truck" + i + "carousel").append(
                    "<div id=\"truck_" + i + "_primaryImg\" " +
                    "class=\"carousel-item active mainCarouselImage\">" +
                    "<img class=\"d-block w-100 cardImageFixed\" " +
                    "src=\"" + secondaryToPrimaryImage + "\"" +
                    "alt=\"temporaryImage\">" +
                    "</div>"
                );
                document.querySelector("#truck" + i + "carousel > div.carousel-item.secondaryCarouselImage").remove();
            } else if (($("#truck_" + i).children().children().children().children().children().hasClass("active") === false)
                && ($("#truck_" + i).children().children().children().children().children().hasClass("secondaryCarouselImage") === false)) {
                $("#truck" + i + "carousel").append(
                    "<div id=\"truck_" + i + "_primaryImg\" " +
                    "class=\"carousel-item active mainCarouselImage\">" +
                    "<img class=\"d-block w-100 cardImageFixed\" " +
                    "src=\"../images/newplaceholder.png\" " +
                    "alt=\"placeholderImg\">" +
                    "</div>"
                );
            }
        }
    }
}

/* Favorites & searched truck placeholder image handling */
if ((window.location.href.indexOf("favorites") > -1) || (window.location.href.indexOf("searchTerm") > -1)) {
    function favoritesTruckIdGetter() {
        let truckIdBucket = [];
        let truckIdsToCheck = [];
        let numberOfTrucks = $("#mainTruckContainer").children().length;
        for (let i = 1; i < 1000; i++) {
            if (document.querySelector("#truck_" + i)) {
                truckIdsToCheck.push(i.toString());
                if (truckIdsToCheck.length === numberOfTrucks) {
                    truckIdBucket = truckIdsToCheck;
                    console.log(truckIdBucket);
                    break;
                }
            }
        }
        truckIdBucket.forEach(function (truck) {
            favoritesTruckImageHandler(truck);
        });
    }

    function favoritesTruckImageHandler(truckId) {
        if ($("#truck_" + truckId)) {
            if (($("#truck_" + truckId).children().children().children().children().children().hasClass("active") === false)
                && ($("#truck_" + truckId).children().children().children().children().children().hasClass("secondaryCarouselImage") === true)) {
                let secondaryToPrimaryImage = $("#truck_" + truckId).children().children().children().children().children().first()[0].lastElementChild.currentSrc;
                $("#truck" + truckId + "carousel").append(
                    "<div id=\"truck_" + truckId + "_primaryImg\" " +
                    "class=\"carousel-item active mainCarouselImage\">" +
                    "<img class=\"d-block w-100 cardImageFixed\" " +
                    "src=\"" + secondaryToPrimaryImage + "\"" +
                    "alt=\"temporaryImage\">" +
                    "</div>"
                );
                document.querySelector("#truck" + truckId + "carousel > div.carousel-item.secondaryCarouselImage").remove();
            } else if (($("#truck_" + truckId).children().children().children().children().children().hasClass("active") === false)
                && ($("#truck_" + truckId).children().children().children().children().children().hasClass("secondaryCarouselImage") === false)) {
                $("#truck" + truckId + "carousel").append(
                    "<div id=\"truck_" + truckId + "_primaryImg\" " +
                    "class=\"carousel-item active mainCarouselImage\">" +
                    "<img class=\"d-block w-100 cardImageFixed\" " +
                    "src=\"../images/notfound.png\" " +
                    "alt=\"placeholderImg\">" +
                    "</div>"
                );
            }
        }
    }
    favoritesTruckIdGetter();
}

/* NEW TRUCK EVENT TO SHOW AND HIDE ADDITIONAL INFO */
function showOwnerRegisterInfo() {
    $("#primaryImgUploadButton").removeClass("hidden");
    $("#ownerPhoneNumber").removeClass("hidden");
    $("#ownerTwitterUrl").removeClass("hidden");
    $("#ownerFacebookUrl").removeClass("hidden");
    $("#ownerInstagramUrl").removeClass("hidden");
}

function hideOwnerRegistrationInfo() {
    $("#primaryImgUploadButton").addClass("hidden");
    $("#ownerPhoneNumber").addClass("hidden");
    $("#ownerTwitterUrl").addClass("hidden");
    $("#ownerFacebookUrl").addClass("hidden");
    $("#ownerInstagramUrl").addClass("hidden");
}

/* TRUCK VIEW EVENT TO HIDE UPLOAD PIC & SHOW SAVE BUTTON */
function showSaveButtonAndHideUpload() {
    $("#truckImageAddPictureButton").addClass("hidden");
    $("#saveImg").removeClass("hidden");
}

/* Trucks crate page anchor handling */
if (window.location.href.indexOf("trucks/create") > -1) {
    $("#addATruckButton").css("color", "rgba(0, 0, 0, 1)");
    $("#addATruckButton").css("font-weight", "bold");
}

/* Favorite trucks page anchor handling */
if (window.location.href.indexOf("/favorites") > -1) {
    $("#favoriteTruckButton").css("color", "rgba(0, 0, 0, 1)");
    $("#favoriteTruckButton").css("font-weight", "bold");
    $("#navBottom").css("position", "fixed");

}

if (window.location.href.indexOf("/login") > -1) {
    $("#navBottom").css("position", "fixed");
}

if (window.location.href.indexOf("update_profile") > -1) {
    $("#navBottom").css("position", "fixed");
}

if (window.location.href.indexOf("/register") > -1) {
    $("#navBottom").css("position", "fixed");
}

if (window.location.href.indexOf("/about") > -1) {
    $("#navBottom").css("position", "fixed");
}

if (window.location.href.indexOf("/trucks/create") > -1) {
    let ownerCheckbox = document.getElementById("ownerCheckbox");
    if (ownerCheckbox.checked === false) {
        $("#navBottom").css("position", "fixed");
    }
    if (ownerCheckbox.checked === true) {
        $("#navBottom").css("position", "");
        $("#createContainerBody").css("margin-bottom", "50px");
    }
    ownerCheckbox.addEventListener("click", function () {
        if (ownerCheckbox.checked === false) {
            $("#navBottom").css("position", "fixed");
        } else if (ownerCheckbox.checked === true) {
            $("#navBottom").css("position", "");
            $("#createContainerBody").css("margin-bottom", "50px");
        }
    })
}

// if (window.location.href.indexOf("/index") === -1) {
//     $("#backgroundImg").css("background-image", "none");
// }














