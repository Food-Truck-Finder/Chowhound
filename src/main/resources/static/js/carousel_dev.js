"use strict";

const client = filestack.init(fileStackKey);
const options = {
    onUploadDone: updateForm,
    maxSize: 10 * 1920 * 1080,
    accept: 'image/*',
    uploadInBackground: false,
};
const picker = client.picker(options);

// Get references to the DOM elements

const form = document.getElementById('pick-form');
const fileInput = document.getElementById('fileupload');
const btn = document.getElementById('picker');
const nameBox = document.getElementById('nameBox');
const urlBox = document.getElementById('urlBox');

// Add our event listeners

btn.addEventListener('click', function (e) {
    e.preventDefault();
    picker.open();
});



// Helper to overwrite the field input value

function updateForm(result) {
    const fileData = result.filesUploaded[0];
    alert("Image successfully uploaded.");

    // Appending image to carousel for testing //
    $("#carouselPics").append(

        // vvvv meat and potatoes for getting the image to work vvvv //
        '<div class="item">' +
        '<img src="' + fileData.url + '"' + ' alt="">' +  // use this to pull image uploaded URL //
        '</div>'
        // ^^^^ it works! ^^^^ //

    )
}

// Slider functionality //

$('.slider').slick({
    draggable: true,
    arrows: true,
    dots: false,
    fade: true,
    speed: 900,
    infinite: true,
    cssEase: 'cubic-bezier(0.7, 0, 0.3, 1)',
    touchThreshold: 100,
    autoplay: true,
    autoplaySpeed: 3000
});







