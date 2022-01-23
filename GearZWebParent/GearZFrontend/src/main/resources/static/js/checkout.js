$(document).change(function () {
  place_order_buttons()
})
function place_order_buttons () {
  var cod = $("input[id=radio-COD]:checked")
  var paypal_credit_cart = $("input[id=radio_paypal_credit_cart]:checked")

  if (cod.length > 0) {
    $("#cod-place-order").removeClass("d-none")
    $(".cod-not-supported").removeClass("d-none")
  } else if (cod.length <= 0) {
    $("#cod-place-order").addClass("d-none")
    $(".cod-not-supported").addClass("d-none")
  }

  if (paypal_credit_cart.length > 0) {
    $("#paypal-button-container").removeClass("d-none")
  } else if (paypal_credit_cart.length <= 0) {
    $("#paypal-button-container").addClass("d-none")
  }
}

paypal.Buttons({
  createOrder: function (data, actions) {
    // specify order details
    return actions.order.create({
      "intent": "CAPTURE",
      "payer": {
        "name": {
          "given_name": customerFirstName,
          "surname": customerLastName
        },
        "email_address": customerEmail,
      },
      "purchase_units": [
        {
          "amount": {
            "currency_code": "USD",
            "value": paymentTotal.replace(",", "")
          }
        }
      ],
      "application_context": {
        "shipping_preference": "NO_SHIPPING"
      }
    })
  },

  onApprove: function (data, actions) {
    // payment approved
    return actions.order.capture().then(function (details) {
      orderId = details.id
      validateOrder(orderId)
    });
  },

  onCancel: function (data) {
    // customer cancelled transaction
    alert("cancelled")
  },

  onError: function (err) {
    // error that prevents customer from checkout
    console.log(err)
    console.log(err)
    console.log(customerFirstName + " " + customerLastName)
    alert("error")
  }
}).render("#paypal-button-container") // Display PayPal payment options button

function validateOrder(orderId) {
  $("#orderId").val(orderId)
  $("#paypal_form").submit()
}