var isPageLoaded = false;
var charDelay = 80;
var textDelay = 800;

document.onreadystatechange = function () {
    if(document.readyState === "complete") isPageLoaded = true;
};

function addText(txt) {
    return new Promise((resolve, reject) => {
        let i = 0;
        let interval = setInterval(() => {
            if(i < txt.length) {
                document.getElementById("typing").innerHTML += txt.charAt(i);
                i++;
            } else {
                clearInterval(interval);
                resolve();
            }
        }, charDelay);
    });
}

function removeText() {
    return new Promise((resolve, reject) => {
        let interval = setInterval(() => {
            let txt = document.getElementById("typing").innerHTML;
            if(txt.length) {
                document.getElementById("typing").innerHTML = txt.substring(0, txt.length - 1);
            } else {
                clearInterval(interval);
                resolve();
            }
        }, charDelay);
    });
}

function delay(time) {
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve(), time);
    });
}

function stopPageLoading() {
    return new Promise((resolve, reject) => {
        if(isPageLoaded) {
            document.getElementById("app").classList.remove("display-none");
            document.getElementById("loader").classList.add("display-none");
            reject();
        } else resolve();
    });
}

function addRemoveText() {
    let dataStr = document.getElementById("typing").getAttribute("data");
    let dataArr = dataStr.split(",");
    let promise = null;
    for(data of dataArr) {
        let text = data.trim();
        if(text) {
            if(promise) promise = promise.then(() => addText(text));
            else promise = addText(text);
            promise = promise.then(() => delay(textDelay))
                                .then(stopPageLoading)
                                .then(removeText)
                                .then(stopPageLoading);
        }
    }
    return promise;
}

function infiniteAddRemoveText() {
    addRemoveText().then(infiniteAddRemoveText);
}

infiniteAddRemoveText();