'use strict';

var myApp = angular.module('myApp', []);

var raisePopup = function (error) {
    document.getElementById("popup-window-message").textContent = error;
    var savedWindow = document.getElementById("popup-window");
    savedWindow.style.display = 'block';
};
