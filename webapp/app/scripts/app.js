'use strict';

var myApp = angular.module('myApp', []);

var raisePopup = function (message) {
    document.getElementById("popup-window-message").textContent = message;
    var savedWindow = document.getElementById("popup-window");
    savedWindow.style.display = 'block';
};

// js-logger: 'debug', 'info', 'notice', 'warning', 'error', 'critical', 'alert', 'emergency'
let logger = Logging.colorConsole({level: 'debug'});