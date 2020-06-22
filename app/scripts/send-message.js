var animationDelay = 1000;

function sendMessage() {
    hideAllMessages();
    const requestBody = prepareRequestBody();

    if(validateMessage(requestBody)) {
        requestBody['ipAddress'] = localStorage.getItem("IP_ADDRESS");
        formLoading(true, null);
        
        $.ajax({
            url: "http://localhost:8080/api/v1/message/send",
            method: "POST",
            data: JSON.stringify(requestBody),
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json"
            },
            success: function (data, textStatus, jqXHR) {
                if(jqXHR.status === 200 && data && data.status === "SENT") {
                    formLoading(false, 'success');
                    setTimeout(() => {
                        setSuccessMessage("Thanks for your message! I'll get back to you in 48 hours.");
                        resetFields();
                    }, animationDelay);
                } else {
                    formLoading(false, 'error');
                }
            },
            error: function (error) {
                formLoading(false, 'error');
                setTimeout(() => {
                    const data = error && error.responseJSON;
                    if(data) {
                        setErrorMessage(data.message)
                        setFieldErrorMessages(data.fieldErrorMessages);
                    } else setErrorMessage(error.statusText);
                }, animationDelay);
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
        let fieldErrorMessage = validateField(key);
        if(fieldErrorMessage) fieldErrorMessages.push(fieldErrorMessage);
    }

    if(fieldErrorMessages.length > 0) {
        return false;
    } else {
        return true;
    }
}

function validateField(key) {
    let fieldErrorMessage = null;
    let value = $('[name="'+ key +'"] > input').val();
    if(key === 'message') value = $('[name="'+ key +'"] > textarea').val();

    if(!value) fieldErrorMessage = { field: key, message: 'Mandatory' };
    else {
        switch(key) {
            case 'email':
                const emailRegex = /\S+@\S+\.\S+/;
                if(!emailRegex.test(value))
                fieldErrorMessage = { field: key, message: 'Invalid' };
                break;
            case 'message':
                if(value.length < 10 || value.length > 2000)
                fieldErrorMessage = { field: key, message: 'Must be between 10 and 2000 characters' };
                break;
        }
    }

    if(fieldErrorMessage) setFieldErrorMessages([ fieldErrorMessage ]);
    else {
        $('[name="'+ key +'"] > .field-message').addClass("hide"); // Hide field message
        $('[name="'+ key +'"] > .field-message').html(""); // Empty field message
        $('[name="'+ key +'"]').removeClass("error"); // Remove field error class
    }
    return fieldErrorMessage;
}

function formLoading(isLoading, result) {
    // Make fields readonly
    $('[name="name"] > input').prop('readonly', isLoading);
    $('[name="email"] > input').prop('readonly', isLoading);
    $('[name="mobile"] > input').prop('readonly', isLoading);
    $('[name="message"] > textarea').prop('readonly', isLoading);
    $('[name="send-message"]').prop('disabled', isLoading);

    // Animate send button
    if(isLoading) { // Sending animation
        $('[name="send-message"] > span').text('Sending');
        $('[name="send-message"]').addClass('sending');
    } else {
        if(result === 'success') { // Sent animation
            $('[name="send-message"] > span').text('SENT');
            $('[name="send-message"]').addClass('sent');
        } else if(result === 'error') { // Failed animation
            $('[name="send-message"] > span').text('FAILED');
            $('[name="send-message"]').addClass('failed');
        }
        setTimeout(function() { // Remove animation
            $('[name="send-message"] > span').text('SEND');
            $('[name="send-message"]').removeClass('sending sent failed');
        }, animationDelay + 3000);
    }
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