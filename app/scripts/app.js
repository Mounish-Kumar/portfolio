document.addEventListener("DOMContentLoaded", function() {
    // Load app
    $('#app').load('./views/app.html', function() {

        // Lazy load images
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

        // Theme on load
        changeTheme();

        // On scroll
        window.addEventListener("scroll", function () {
            // Make header small
            let menu = document.getElementById('header');
            if(window.pageYOffset > 0) {
                menu.classList.add("scrolled");
            } else {
                menu.classList.remove("scrolled");
            }

            // Parallax
            let scrollY = window.scrollY;
            let home = document.getElementById("home");
            let homeImage = home.getElementsByTagName("img")[0];
            let homeContent = home.getElementsByClassName("content")[0];
            let h1 = homeContent.getElementsByTagName("h1")[0];
            let h2 = homeContent.getElementsByTagName("h2")[0];
            let action = homeContent.getElementsByClassName("action")[0];
            if(window.innerWidth <= 767) { // Parallax for mobile
                homeContent.style.top = 3 * 16 + scrollY * 0.8 + 'px';
                homeContent.style.transform = 'scale(' + (1 + scrollY / (764 * 1.6)) + ')';
                homeImage.style.bottom = 0 - scrollY * 0.8 + 'px';
                action.style.top = 0 - scrollY * 0.7 + 'px';
                action.style.transform = 'scale(' + (1 + scrollY / 764) + ')';
            } else { // Parallax for tablet & desktop
                homeImage.style.bottom = 0 - scrollY * 1.2 + 'px';
                homeImage.style.transform = 'scale(' + (1 + scrollY / (464 * 2)) + ')';
                homeContent.style.bottom = 0 - scrollY * 0.8 + 'px';
                h2.style.fontSize = (1.5 + scrollY / (464 * 1.2)) + 'rem';
                h1.style.fontSize = (2.5 + scrollY / 464) + 'rem';
            }
        });

        // Save IP address
        if(!localStorage.getItem("IP_ADDRESS")) {
            $.getJSON("https://ipapi.co/json", function(data) {
                localStorage.setItem("IP_ADDRESS", data.ip);
            });
        }

        // Add Scripts
        const vanilla_tilt = document.createElement('script');
        vanilla_tilt.src = './scripts/vanilla-tilt.min.js';
        document.body.appendChild(vanilla_tilt);
    });
});