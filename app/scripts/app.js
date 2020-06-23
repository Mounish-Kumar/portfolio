document.addEventListener("DOMContentLoaded", function() {
    // Load app
    $('#app').load('./views/app.html', function() {

        // Lazy load images
        if ('loading' in HTMLImageElement.prototype) {
            const images = document.querySelectorAll('img[loading="lazy"]');
            images.forEach(img => {
                img.src = img.dataset.src;
            })
        } else {
            const script = document.createElement('script');
            script.src = './scripts/lazysizes.min.js';
            document.body.appendChild(script);
        }

        // Make header small on scroll
        window.addEventListener("scroll", function () {
            let menu = document.getElementById('header');
            if (window.pageYOffset > 0) {
                menu.classList.add("scrolled");
            } else {
                menu.classList.remove("scrolled");
            }
        });

        // Save IP address
        if (!localStorage.getItem("IP_ADDRESS")) {
            $.getJSON("https://ipapi.co/json", function (data) {
                localStorage.setItem("IP_ADDRESS", data.ip);
            });
        }

        // Add Scripts
        const vanilla_tilt = document.createElement('script');
        vanilla_tilt.src = './scripts/vanilla-tilt.min.js';
        document.body.appendChild(vanilla_tilt);

    });
});