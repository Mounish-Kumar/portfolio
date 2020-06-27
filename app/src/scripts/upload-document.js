function getBase64(id) {
    return new Promise((resolve, reject) => {
        let selectedFiles = document.getElementById(id).files;
        if(selectedFiles.length > 0) {
            let fileToUpload = selectedFiles[0];
            let fileReader = new FileReader();
            fileReader.onload = function(event) {
                let base64 = event.target.result;
                resolve(base64);
            };
            fileReader.readAsDataURL(fileToUpload);
        }
    });
    
}

function getFileName(id) {
    let fileName = null;
    let fullPath = document.getElementById(id).value;
    if(fullPath) {
        let startIndex = (fullPath.indexOf('\\') >= 0 ? fullPath.lastIndexOf('\\') : fullPath.lastIndexOf('/'));
        fileName = fullPath.substring(startIndex);
        if(fileName.indexOf('\\') === 0 || fileName.indexOf('/') === 0) {
            fileName = fileName.substring(1);
        }
    }
    return fileName;
}

function uploadDocument() {
    elementId = "uploadDoc";
    getBase64(elementId).then((base64) => {
        const requestBody = {
            key: "resume",
            fileName: getFileName(elementId),
            fileContent: base64
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/document",
            method: "POST",
            data: JSON.stringify(requestBody),
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
            success: function (data, textStatus, jqXHR) {
                if(jqXHR.status === 200) {
                    console.log(data);
                }
            },
            error: function (error) {
                console.log(error.responseJSON);
            }
        });
    });
}