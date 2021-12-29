import walmartImg from "../assets/images/walmart.webp";
import centricoImg from "../assets/images/centrico.webp";
import sellaImg from "../assets/images/sella.webp";
import fabrickImg from "../assets/images/fabrick.webp";
import hypeImg from "../assets/images/hype.webp";
import axerveImg from "../assets/images/axerve.webp";
import wiproImg from "../assets/images/wipro.webp";
import sbsaImg from "../assets/images/sbsa.webp";
import walmartWhiteImg from "../assets/images/walmart_white.webp";
import centricoWhiteImg from "../assets/images/centrico_white.webp";
import sellaWhiteImg from "../assets/images/sella_white.webp";
import fabrickWhiteImg from "../assets/images/fabrick_white.webp";
import hypeWhiteImg from "../assets/images/hype_white.webp";
import axerveWhiteImg from "../assets/images/axerve_white.webp";
import wiproWhiteImg from "../assets/images/wipro_white.webp";
import sbsaWhiteImg from "../assets/images/sbsa_white.webp";

export var changeTheme = function (newTheme) {
  if (newTheme) {
    let oldTheme = newTheme === "dark-theme" ? "light-theme" : "dark-theme";

    document.getElementById(oldTheme).classList.remove("display-none");
    document.getElementById(newTheme).classList.add("display-none");

    let body = document.getElementsByTagName("body")[0];
    body.classList.add(newTheme);
    body.classList.remove(oldTheme);

    changeImages(newTheme);
  } else {
    if (isDay()) changeTheme("light-theme");
    else changeTheme("dark-theme");
  }
};

var changeImages = function (theme) {
  const baseUrl = "../assets/images/";

  switch (theme) {
    case "light-theme":
      document.getElementById("walmart").src = walmartImg;
      document.getElementById("centrico").src = centricoImg;
      document.getElementById("sella").src = sellaImg;
      document.getElementById("fabrick").src = fabrickImg;
      document.getElementById("hype").src = hypeImg;
      document.getElementById("axerve").src = axerveImg;
      document.getElementById("wipro").src = wiproImg;
      document.getElementById("sbsa").src = sbsaImg;
      break;

    case "dark-theme":
      document.getElementById("walmart").src = walmartWhiteImg;
      document.getElementById("centrico").src = centricoWhiteImg;
      document.getElementById("sella").src = sellaWhiteImg;
      document.getElementById("fabrick").src = fabrickWhiteImg;
      document.getElementById("hype").src = hypeWhiteImg;
      document.getElementById("axerve").src = axerveWhiteImg;
      document.getElementById("wipro").src = wiproWhiteImg;
      document.getElementById("sbsa").src = sbsaWhiteImg;
      break;
  }
};

var isDay = function () {
  const startTime = "07:00:00";
  const endTime = "19:00:00";
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
};
