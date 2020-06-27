import centricoImg from  "../assets/images/centrico.png";
import sellaImg from "../assets/images/sella.png";
import hypeImg from "../assets/images/hype.png";
import axerveImg from "../assets/images/axerve.png";
import wiproImg from "../assets/images/wipro.png";
import sbsaImg from "../assets/images/sbsa.png";
import centricoWhiteImg from "../assets/images/centrico_white.png";
import sellaWhiteImg from "../assets/images/sella_white.png";
import hypeWhiteImg from "../assets/images/hype_white.png";
import axerveWhiteImg from "../assets/images/axerve_white.png";
import wiproWhiteImg from "../assets/images/wipro_white.png";
import sbsaWhiteImg from "../assets/images/sbsa_white.png";

export var changeTheme = function(newTheme) {
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

var changeImages = function(theme) {
    const baseUrl = "../assets/images/";

    switch(theme) {
        case "light-theme":
            document.getElementById("centrico").src = centricoImg;
            document.getElementById("sella").src = sellaImg;
            document.getElementById("hype").src = hypeImg;
            document.getElementById("axerve").src = axerveImg;
            document.getElementById("wipro").src = wiproImg;
            document.getElementById("sbsa").src = sbsaImg;
            break;

        case "dark-theme":
            document.getElementById("centrico").src = centricoWhiteImg;
            document.getElementById("sella").src = sellaWhiteImg;
            document.getElementById("hype").src = hypeWhiteImg;
            document.getElementById("axerve").src = axerveWhiteImg;
            document.getElementById("wipro").src = wiproWhiteImg;
            document.getElementById("sbsa").src = sbsaWhiteImg;
            break;
    }
}

var isDay = function() {
    const startTime = '07:00:00';
    const endTime = '19:00:00';
    const currentDate = new Date();

    let startDate = new Date(currentDate.getTime());
    startDate.setHours(startTime.split(":")[0]);
    startDate.setMinutes(startTime.split(":")[1]);
    startDate.setSeconds(startTime.split(":")[2]);

    let endDate = new Date(currentDate.getTime());
    endDate.setHours(endTime.split(":")[0]);
    endDate.setMinutes(endTime.split(":")[1]);
    endDate.setSeconds(endTime.split(":")[2]);

    return startDate < currentDate && endDate > currentDate;
}