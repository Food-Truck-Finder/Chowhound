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
   $("#truckCards").append(

       // card layout start //
       '<div class="card col-sm-12 col-md-4 col-lg-3 col-xl-2 align-content-center" >' +

                        // vvvv meat and potatoes for getting the image to work vvvv //
       '<img src="' + fileData.url + '">' +  // use this to pull image uploaded URL //
                                        // ^^^^ it works! ^^^^ //

       '<div class="card-body">' +
       '<h5 class="card-title">Card title</h5>' +
       '<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card&#39;s content.</p>' +
       '<a href="#" class="btn btn-primary">Go somewhere</a>' +
       '</div>' +
       '</div>'
       // card layout end //
   )
}








