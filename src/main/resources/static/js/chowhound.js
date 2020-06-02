"use strict";

const client = filestack.init(/*INSERT API KEY HERE*/);
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
    console.log(fileData.url);
    $("#imageURL").val(fileData.url);
    alert("Image successfully uploaded.");

}










