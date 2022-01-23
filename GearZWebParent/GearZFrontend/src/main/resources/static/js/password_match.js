function check_password_match(confirm_password) {
    if (confirm_password.value != $("#fullPassword").val()) {
        confirm_password.setCustomValidity("Password mismatch")
    } else {
        confirm_password.setCustomValidity("");
    }
}