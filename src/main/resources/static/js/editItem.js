"use strict";

$(document).ready(() => {
    $("#itemImage").change(() => {
        console.log("Hello");
        let fileExtension = ['jpeg', 'jpg', 'png', 'gif', 'bmp'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
        }
    });
});

