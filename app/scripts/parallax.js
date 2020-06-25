function parallax() {
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
}