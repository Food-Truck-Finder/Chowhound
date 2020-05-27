"use strict";

const client = filestack.init(fileStackKey);
const options = {
    onUploadDone: updateForm,
    maxSize: 10 * 1024 * 1024,
    accept: 'image/*',
    uploadInBackground: false,
    storeTo: {
        workflows: ["63039cab-55c0-406c-a2a0-c24ab75c0edd"]
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

form.addEventListener('submit', function (e) {
    e.preventDefault();
    alert('Submitting: ' + fileInput.value);
});

// Helper to overwrite the field input value

function updateForm(result) {
    const fileData = result.filesUploaded[0];
    fileInput.value = fileData.url;

    // Some ugly DOM code to show some data.
    const name = document.createTextNode('Selected: ' + fileData.filename);
    const url = document.createElement('a');
    url.href = fileData.url;
    url.appendChild(document.createTextNode(fileData.url));
    nameBox.appendChild(name);
    urlBox.appendChild(document.createTextNode('Uploaded to: '));
    urlBox.appendChild(url);
    console.log(JSON.stringify(result));
}

$("#appendNewCard").click(function (e) {
    e.preventDefault();
    $("#truckCards").append(
        '<div class="card col-sm-12 col-md-4 col-lg-3 col-xl-2 align-content-center" >' +
        '<img src="" class="card-img-top" alt="">' +
        '<div class="card-body">' +
        '<h5 class="card-title">Card title</h5>' +
        '<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card&#39;s content.</p>' +
        '<a href="#" class="btn btn-primary">Go somewhere</a>' +
        '</div>' +
        '</div>'
    );
});







