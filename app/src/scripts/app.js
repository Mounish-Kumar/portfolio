import '../styles/index.scss';

import $ from 'jquery';
import "./vanilla-tilt.min.js";

import { changeTheme } from "./change-theme";
import { parallax } from "./parallax";
import { downloadResume, downloadDocument } from "./download-document";
import { sendMessage, validateField } from "./send-message";
import { uploadDocument } from "./upload-document";

$(function() {

    // Window functions
    window.changeTheme = changeTheme;
    window.downloadResume = downloadResume;
    window.downloadDocument = downloadDocument;
    window.sendMessage = sendMessage;
    window.validateField = validateField;
    window.uploadDocument = uploadDocument;

    // Change vh
    setViewportHeight();
    if(window.innerWidth >= 768) { // Change vh on resize only for Tablet & desktop
        window.addEventListener('resize', () => {
            setViewportHeight();
        });
    }

    changeTheme();

    let containers = $('.container');

    $(document).scroll(function () {
        makeHeaderSmall();

        parallax();

        navigationActive(containers);
    });

    getIpAddress();
});

var setViewportHeight = function() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`);
}

var makeHeaderSmall = function() {
    let menu = document.getElementById('header');
    if(window.pageYOffset > 0) {
        menu.classList.add("scrolled");
    } else {
        menu.classList.remove("scrolled");
    }
}

var navigationActive = function(containers) {
    let scrollTop = (4 * 16) + $(window).scrollTop();
    let currentContainer = null;
    for(let container of containers) {
        if(scrollTop >= container.offsetTop) currentContainer = container;
    }
    if(scrollTop + $(window).height() >= $(document).height()) { // Reached end of document
        let lastIndex = containers.length - 1;
        currentContainer = containers[lastIndex];
    }
    if(window.history.pushState) {
        let id = '#' + currentContainer.id;
        window.history.pushState(null, null, id);
        $('#header').find(".active").removeClass("active");
        $('[href="' + id + '"]').addClass("active");
    }
}

var getIpAddress = function() {
    if(!localStorage.getItem("IP_ADDRESS")) {
        $.getJSON("https://ipapi.co/json", function(data) {
            localStorage.setItem("IP_ADDRESS", data.ip);
        });
    }
}

var addScript = function(url) {
    let scriptTag = document.createElement('script');
    scriptTag.src = url;
    document.body.appendChild(scriptTag);
}