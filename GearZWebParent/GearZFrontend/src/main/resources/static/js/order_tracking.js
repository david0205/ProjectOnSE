var return_modal
var modal_title
var note_field
var orderId

var div_reason
var div_message
var send_request_btn
var cancel_btn

$(document).ready(function () {
  $.noConflict();
  $('#table-orders').dataTable({
      "pagingType": "full_numbers",
      "lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
      "autoWidth": false,
      "columnDefs": [
          { "searchable": false, "targets": [1, 2, 3, 4, 5] },
          { "width": "60px", "targets": [0] }
      ]
  });

  $(".link-order-details").on("click", function (e) {
      e.preventDefault()
      $("#order-details-modal").modal("show").find(".modal-content").load($(this).attr("href"))
  })

  return_modal = $("#return-order-modal")
  modal_title = $("#return-order-modal-title")
  note_field = $("#return-additional-info")

  div_reason = $("#div-reason")
  div_message = $("#div-message")
  send_request_btn = $("#send-request-btn")
  cancel_btn = $("#cancel-btn")
  
  handle_return_order()
})

function show_return_modal(link) {
  div_message.hide()
  div_reason.show()
  send_request_btn.show()
  cancel_btn.text("Cancel")
  note_field.val("")

  orderId = link.attr("orderId")
  modal_title.text("Return Order ID #" + orderId)
  return_modal.modal("show")
}

function show_message_dialog(message) {
  div_reason.hide()
  send_request_btn.hide()
  cancel_btn.text("Close")
  div_message.text(message)
  div_message.show()
}

function handle_return_order() {
  $(".link-return-order").on("click", function (e) {
    e.preventDefault()
    show_return_modal($(this))
  })
}

function submit_return_order_form() {
  reason = $("input[name='return-reason']:checked").val()
  addition_info = note_field.val()
  send_return_order_request(reason, addition_info)
  return false;
}

function send_return_order_request(reason, addition_info) {
  request_url = context_path + "orders/return"
  requestBody = {orderId: orderId, reason: reason, additionalInfo: addition_info}

  $.ajax({
    type: "post",
    url: request_url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfToken)
    },
    data: JSON.stringify(requestBody),
    contentType: 'application/json'
  }).done(function (returnResponse) {
    show_message_dialog("The return request has been made")
    update_status(orderId)
  }).fail(function (err) {
    show_message_dialog(err.responseText)
  })
}

function update_status(orderId) {
  $(".textOrderStatus" + orderId).each(function (index) {
    $(this).text("RETURN_REQUESTED")
  })
  $(".linkReturn" + orderId).each(function (index) {
    $(this).hide()
  })
}