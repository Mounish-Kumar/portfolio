function sendMessage() {
    hideAllMessages();
    const requestBody = prepareRequestBody();

    if(validateMessage(requestBody)) {
        requestBody['ipAddress'] = localStorage.getItem("IP_ADDRESS");
        formLoading(true);
        
        $.ajax({
            url: "http://localhost:8080/api/v1/message/send",
            method: "POST",
            data: JSON.stringify(requestBody),
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
            success: function (data, textStatus, jqXHR) {
                formLoading(false);
                if(jqXHR.status === 200 && data && data.status === "SENT") {
                    setSuccessMessage("Thanks for your message! I'll get back to you in 48 hours.");
                    resetFields();
                }
            },
            error: function (error) {
                formLoading(false);
                const data = error && error.responseJSON;
                if(data) {
                    setErrorMessage(data.message)
                    setFieldErrorMessages(data.fieldErrorMessages);
                } else setErrorMessage(error.statusText);
            }
        });
    }
}

function hideAllMessages() {
    $(".response-message").addClass("hide"); // Hide message
    $(".response-message > span").html(""); // Empty message
    $('.field-message').addClass("hide"); // Hide field message
    $('.field-message').html(""); // Empty field message
    $('form > label').removeClass("error"); // Remove field error class
}

function prepareRequestBody() {
    return {
        name: $('[name="name"] > input').val(),
        email: $('[name="email"] > input').val(),
        mobile: $('[name="mobile"] > input').val(),
        message: $('[name="message"] > textarea').val()
    };
}

function validateMessage(requestBody) {
    let fieldErrorMessages = [];
    for(let key in requestBody) {
        if(!requestBody[key]) fieldErrorMessages.push({ field: key, message: 'Mandatory' });
        else {
            switch(key) {
                case 'email':
                    const emailRegex = /\S+@\S+\.\S+/;
                    if(!emailRegex.test(requestBody[key]))
                        fieldErrorMessages.push({ field: key, message: 'Invalid' });
                    break;
                case 'message':
                    if(requestBody[key].length < 10 || requestBody[key].length > 2000)
                        fieldErrorMessages.push({ field: key, message: 'Must be between 10 and 2000 characters' });
                    break;
            }
        }
    }

    if(fieldErrorMessages.length > 0) {
        setFieldErrorMessages(fieldErrorMessages);
        return false;
    } else {
        return true;
    }
}

function formLoading(readonly) {
    // Make fields readonly
    $('[name="name"] > input').prop('readonly', readonly);
    $('[name="email"] > input').prop('readonly', readonly);
    $('[name="mobile"] > input').prop('readonly', readonly);
    $('[name="message"] > textarea').prop('readonly', readonly);
    $('[name="send-message"]').prop('disabled', readonly);

    // Animate send button
}

function setSuccessMessage(message) {
    if(message) {
        $(".response-message.success > span").html(message); // Success message
        $(".response-message.success").removeClass("hide"); // Show success message
        setTimeout(function () {
            $(".response-message.success").addClass("hide");
            $(".response-message.success > span").html("");
        }, 15000); // Remove success message after 15 seconds
    }
}

function setErrorMessage(message) {
    if(message) {
        $(".response-message.error > span").html(message); // Error message
        $(".response-message.error").removeClass("hide"); // Show error message
    }
}

function setFieldErrorMessages(fieldErrorMessages) {
    if(fieldErrorMessages && fieldErrorMessages.length > 0) {
        fieldErrorMessages.map(fieldErrorMessage => {
            $('[name="' + fieldErrorMessage.field + '"]').addClass("error"); // Add field error class
            $('[name="' + fieldErrorMessage.field + '"] > .field-message.error').html(fieldErrorMessage.message); // Field error message
            $('[name="' + fieldErrorMessage.field + '"] > .field-message.error').removeClass("hide"); // Show field error message
        });
    }
}

function resetFields() {
    $('[name="name"] > input').val("");
    $('[name="email"] > input').val("");
    $('[name="mobile"] > input').val("");
    $('[name="message"] > textarea').val("");
}