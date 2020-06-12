function sendMessage() {
    $(".response-message").addClass("hide"); // Hide message
    $(".response-message > span").html(""); // Empty message
    $('.field-message').addClass("hide"); // Hide field message
    $('.field-message').html(""); // Empty field message
    $('form > label').removeClass("error"); // Remove field error class

    const requestBody = {
        name: $('[name="name"] > input').val(),
        email: $('[name="email"] > input').val(),
        mobile: $('[name="mobile"] > input').val(),
        message: $('[name="message"] > textarea').val(),
        ipAddress: localStorage.getItem("IP_ADDRESS")
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/message/send",
        method: "POST",
        data: JSON.stringify(requestBody),
        headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json"
        },
        success: function (data, textStatus, jqXHR) {
            if (jqXHR.status === 200 && data && data.status === "SENT") {
                $(".response-message.success > span").html("Message sent! We'll get back to you in 48 hours."); // Success message
                $(".response-message.success").removeClass("hide"); // Show success message
                setTimeout(function () {
                    $(".response-message.success").addClass("hide");
                    $(".response-message.success > span").html("");
                }, 15000); // Remove success message after 15 seconds

                // Reset field values
                $('[name="name"] > input').val("");
                $('[name="email"] > input').val("");
                $('[name="mobile"] > input').val("");
                $('[name="message"] > textarea').val("");
            }
        },
        error: function (error) {
            const data = error && error.responseJSON;
            if (data) {
                if (data.message) {
                    $(".response-message.error > span").html(data.message); // Error message
                    $(".response-message.error").removeClass("hide"); // Show error message
                }

                if (data.fieldErrorMessages && data.fieldErrorMessages.length > 0) {
                    data.fieldErrorMessages.map(fieldErrorMessage => {
                        $('[name="' + fieldErrorMessage.field + '"]').addClass("error"); // Add field error class
                        $('[name="' + fieldErrorMessage.field + '"] > .field-message.error').html(fieldErrorMessage.message); // Field error message
                        $('[name="' + fieldErrorMessage.field + '"] > .field-message.error').removeClass("hide"); // Show field error message
                    });
                }
            }
        }
    });
}