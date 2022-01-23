var confirmText
var confirmModalDialog
var yesBtn
var noBtn
var iconNames = {
    'PICKED': 'fa-people-carry',
    'SHIPPING': 'fa-shipping-fast',
    'DELIVERED': 'fa-box-open',
    'RETURNED': 'fa-undo'
}

$(document).ready(function () {
    confirmText = $("#confirmText")
    confirmModalDialog = $("#confirmModal")
    yesBtn = $("#btn-yes")
    noBtn = $("#btn-no")

    $(".link-update-status").on("click", function (e) {
        e.preventDefault()
        showUpdateConfirmModal($(this))
    })

    eventHandlerForYesBtn()
})

function eventHandlerForYesBtn() {
    yesBtn.click(function (e) {
        e.preventDefault()
        sendRequestToUpdateOrderStatus($(this))
    })
}

function sendRequestToUpdateOrderStatus(button) {
    requestURL = button.attr("href")
    $.ajax({
		type: 'post',
		url: requestURL,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
	}).done(function (response) {
        showMessageModal("Order updated successfully")
        updateStatusIconColor(response.orderId, response.status)
        console.log(response.orderId)
        console.log(response.status)
	}).fail(function (err) {
        showMessageModal("Error occurred while updating order status")
    })
}

function updateStatusIconColor(orderId, status) {
    $("#link" + status + orderId).replaceWith("<a class='btn btn-success' role='button' onclick='return false;'><i class='fas " + iconNames[status] + "'></i></a>")
}

function showMessageModal(message) {
    noBtn.text("Close")
    yesBtn.hide()
    confirmText.text(message)
}

function showUpdateConfirmModal(link) {
    noBtn.text("NO")
    yesBtn.show()
    orderId = link.attr("orderId")
    _status = link.attr("status")
    yesBtn.attr("href", link.attr("href"))
    confirmText.text("Are you sure you want to update status of the order ID #" + orderId + " to " + _status + "?")
    confirmModalDialog.modal()
}