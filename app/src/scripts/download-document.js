import { serviceBaseUrl } from "./app.js";

export var downloadResume = function () {
  openLinkInNewTab(`${serviceBaseUrl}/document/resume`);
};

export var downloadDocument = function (id) {
  openLinkInNewTab(`${serviceBaseUrl}/document/download/${id}`);
};

var openLinkInNewTab = function (link) {
  let hiddenElement = document.createElement("a");
  hiddenElement.href = link;
  hiddenElement.target = "_blank";
  hiddenElement.click();
};
