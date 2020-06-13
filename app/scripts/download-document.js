function downloadResume() {
    let hiddenElement = document.createElement("a");
    hiddenElement.href = "http://localhost:8080/api/v1/document/resume";
    hiddenElement.target = "_blank";
    hiddenElement.click();
}

function downloadDocument(id) {
    let hiddenElement = document.createElement("a");
    hiddenElement.href = "http://localhost:8080/api/v1/document/download/" + id;
    hiddenElement.target = "_blank";
    hiddenElement.click();
}