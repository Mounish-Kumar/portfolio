function changeTheme(newTheme) {
    if(newTheme) {
        let oldTheme = newTheme === 'dark-theme' ? 'light-theme' : 'dark-theme';

        document.getElementById(oldTheme).classList.remove("display-none");
        document.getElementById(newTheme).classList.add("display-none");

        let body = document.getElementsByTagName("body")[0];
        body.classList.add(newTheme);
        body.classList.remove(oldTheme);

        changeImages(newTheme);
    } else {
        if(isDay()) changeTheme('light-theme');
        else changeTheme('dark-theme');
    }
}

function changeImages(theme) {
    const baseUrl = "./assets/images/";

    switch(theme) {
        case "light-theme":
            document.getElementById("centrico").src = baseUrl + "centrico.png";
            document.getElementById("sella").src = baseUrl + "sella.png";
            document.getElementById("hype").src = baseUrl + "hype.png";
            document.getElementById("axerve").src = baseUrl + "axerve.png";
            document.getElementById("wipro").src = baseUrl + "wipro.png";
            document.getElementById("sbsa").src = baseUrl + "sbsa.png";
            break;

        case "dark-theme":
            document.getElementById("centrico").src = baseUrl + "centrico_white.png";
            document.getElementById("sella").src = baseUrl + "sella_white.png";
            document.getElementById("hype").src = baseUrl + "hype_white.png";
            document.getElementById("axerve").src = baseUrl + "axerve_white.png";
            document.getElementById("wipro").src = baseUrl + "wipro_white.png";
            document.getElementById("sbsa").src = baseUrl + "sbsa_white.png";
            break;
    }
}

function isDay() {
    let startTime = '07:00:00';
    let endTime = '19:00:00';
    let currentDate = new Date();

    let startDate = new Date(currentDate.getTime());
    startDate.setHours(startTime.split(":")[0]);
    startDate.setMinutes(startTime.split(":")[1]);
    startDate.setSeconds(startTime.split(":")[2]);

    let endDate = new Date(currentDate.getTime());
    endDate.setHours(endTime.split(":")[0]);
    endDate.setMinutes(endTime.split(":")[1]);
    endDate.setSeconds(endTime.split(":")[2]);

    let isValid = startDate < currentDate && endDate > currentDate;
    return isValid;
}