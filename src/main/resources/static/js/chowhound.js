"use strict";
$(document).ready(function () {

    /* Only runs filestack API logic if in a window that we have the upload button in */
    /* Doing this to avoid console errors and so it doesn't run when not needed */
    if (window.location.href.indexOf("trucks") > -1) {

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

    /* NEW TRUCK EVENT TO SHOW AND HIDE ADDITIONAL INFO */
    function showOwnerRegisterInfo() {
        $("#ownerEmail").removeClass("hidden");
        $("#ownerPhoneNumber").removeClass("hidden");
        $("#ownerTwitterUrl").removeClass("hidden");
        $("#ownerFacebookUrl").removeClass("hidden");
        $("#ownerInstagramUrl").removeClass("hidden");
    }

    function hideOwnerRegistrationInfo() {
        $("#ownerEmail").addClass("hidden");
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


    /* Placeholder image logic */
    /* image will no longer show up if an image uploaded .isPrimary() */
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
                    "src=\"../images/placeholderImg.png\" " +
                    "alt=\"placeholderImg\">" +
                    "</div>"
                );
            }
        }
    }
});















