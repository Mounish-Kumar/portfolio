$(function() {
    // Load app
    $('#app').load('./views/app.html', function() {

        // Vh in mobile
        setViewportHeight();
        window.addEventListener('resize', () => {
            setViewportHeight();
        });

        lazyLoadImages();

        changeTheme();

        var containers = $('.container');

        $(document).scroll(function () {
            makeHeaderSmall();

            parallax();

            navigationActive(containers);
        });

        getIpAddress();

        addScript('./scripts/vanilla-tilt.min.js');
    });
});

function setViewportHeight() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty('--vh', `${vh}px`);
}

function lazyLoadImages() {
    if('loading' in HTMLImageElement.prototype) {
        const images = document.querySelectorAll('img[loading="lazy"]');
        images.forEach(img => {
            img.src = img.dataset.src;
        })
    } else {
        const script = document.createElement('script');
        script.src = './scripts/lazysizes.min.js';
        document.body.appendChild(script);
    }
}

function makeHeaderSmall() {
    let menu = document.getElementById('header');
    if(window.pageYOffset > 0) {
        menu.classList.add("scrolled");
    } else {
        menu.classList.remove("scrolled");
    }
}

function navigationActive(containers) {
    let scrollTop = (4 * 16) + $(this).scrollTop();
    let currentContainer = null;
    for(let container of containers) {
        if(scrollTop >= container.offsetTop) currentContainer = container;
    }
    if($(window).scrollTop() + $(window).height() == $(document).height()) { // Reached end of document
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

function getIpAddress() {
    if(!localStorage.getItem("IP_ADDRESS")) {
        $.getJSON("https://ipapi.co/json", function(data) {
            localStorage.setItem("IP_ADDRESS", data.ip);
        });
    }
}

function addScript(url) {
    const scriptTag = document.createElement('script');
    scriptTag.src = url;
    document.body.appendChild(scriptTag);
}