"use strict";

const client = filestack.init(fileStackKey);
const options = {
    onUploadDone: updateForm,
    maxSize: 10 * 1024 * 1024,
    accept: 'image/*',
    uploadInBackground: false,
    storeTo: {
        workflows: [workflowKey]
    },
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

    // Appending image to a card for testing //
    $(".gallery-wrapper").append(

        // vvvv meat and potatoes for getting the image to work vvvv //
        '<div class="carousel-item">' +
        '<img src="' + fileData.url + '"' + ' style="width: 400px; height: 400px;" alt="">' +  // use this to pull image uploaded URL //
        '</div>'
        // ^^^^ it works! ^^^^ //

    )
}






