export var downloadResume = function() {
    openLinkInNewTab("http://localhost:8080/api/v1/document/resume");
}

export var downloadDocument = function(id) {
    openLinkInNewTab("http://localhost:8080/api/v1/document/download/" + id);
}

var openLinkInNewTab = function(link) {
    let hiddenElement = document.createElement("a");
    hiddenElement.href = link;
    hiddenElement.target = "_blank";
    hiddenElement.click();
}