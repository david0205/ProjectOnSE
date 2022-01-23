$(document).ready(function(){
  update_total()

  $("input[id='add-to-cart-btn']").on("click", function () {
    add_to_cart()
  })

  $('.add-to-cart-btn-home-product-listing')
  .add('.add-to-cart-btn-prod-listing')
  .add('.add-to-cart-btn-search-listing').click(function () {
    console.log($(this).attr('id'))
    add_to_cart_2($(this).attr('id'), 1)
  })
  
	$(".btn_inc").on("click", function (e) {
    var productId = e.target.id
    var quantity = $('#quantity_' + productId).val()
    update_quantity(productId, quantity)
  })

  $(".remove-product").on("click", function (e) {
    e.preventDefault();
    remove_product_from_cart($(this))
  })
})

function show_empty_cart() {
  $(".cart-table-list").hide()
  $("#section-total").hide()
  $("#section-empty-cart").removeClass('d-none')
}

function remove_a_row(rowNumber, count) {
  if (count >= 1) {
    $("#cart_row_" + rowNumber).remove()
  } else {
    show_empty_cart()
  }
}

function remove_product_from_cart($remove_btn) {
  var url = $remove_btn.parent().attr("href")

  remove_a_row($remove_btn.parent().attr('rowNumber'), $("#cart_rows tr").length)
  $.ajax({
    type: "delete",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfToken)
    }
  }).done(function (response) {
    $('.toast-body').text(response)
    $('.toast').toast('show')
    cart_item_amount()
    add_item_to_preview_cart()
    remove_a_row($remove_btn.parent().attr('rowNumber'), $("#cart_rows tr").length)
    console.log($("#cart_rows tr").length)
  }).fail(function () {
    $('.toast-body').text('Error while remove product from cart.')
    $('.toast').toast('show')
  })
}

function update_total() {
  var total = 0.0
  $('strong[class="subtotal"]').each(function (index, element) {
    total += parseFloat(element.innerHTML.replaceAll(",", ""))
  })
  $('#total').text('$' + total.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,'))
}

function update_subtotal(updated_subtotal, productId) {
  $('#subtotal_' + productId).text(updated_subtotal)
}

function update_quantity(productId, quantity) {
  var url = context_path + "cart/update/" + productId + "/" + quantity
  $.ajax({
    type: "post",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfToken)
    }
  }).done(function (response) {
    update_subtotal(Number(response).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,'), productId)
    add_item_to_preview_cart()
    update_total()
  }).fail(function () {
    $('.toast-body').text('Error while adding product to cart.')
    $('.toast').toast('show')
  })
}

function add_item_to_preview_cart() {
  var url_2 = context_path + "cart-items"
  var total = 0.0
  $.get(url_2, function (response) {
    $(".preview-cart").empty()
    $.each(response, function name(index, item) {
      $("#grand-total").empty()
      var main_image = context_path + item.itemMainImage.substring(1)
      total += item.subTotalPrice
      $("<li><a>" 
      + "<figure><img src='" + main_image + "' width='50' height='50' class='lazy'></figure>"
      + "<strong><span>" + item.itemQuantity + "x " + item.itemName +"</span>$" + item.subTotalPrice.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + "</strong>"
      + "</a></li>").appendTo($(".preview-cart"))
    })
    $('#total').text('$' + total.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,'))
  }).done(function () {
    $("#grand-total").empty()
    $("<strong>Total</strong><span>$" + parseFloat(total).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + "</span>").appendTo($("#grand-total"))
  })
}

function cart_item_amount() {
  var url = context_path + "amount"
  $.get(url, function (response) {
		$(".cart_bt").empty()
		$("<strong>").text(response).appendTo($(".cart_bt"))
	})
}

function add_to_cart_2(productId, quantity) {
  var url = context_path + "cart/add/" + productId + "/" + quantity

  $.ajax({
    type: "post",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfToken)
    }
  }).done(function (response) {
    $('.toast-body').text(response)
    $('.toast').toast('show')
    cart_item_amount()
    add_item_to_preview_cart()
  }).fail(function () {
    $('.toast-body').text('Error while adding product to cart.')
    $('.toast').toast('show')
  })

}

function add_to_cart() {
  var quantity = $('.button_inc').parent().find("input").val()
  var url = context_path + "cart/add/" + productId + "/" + quantity

  $.ajax({
    type: "post",
    url: url,
    beforeSend: function (xhr) {
      xhr.setRequestHeader(csrfHeaderName, csrfToken)
    }
  }).done(function (response) {
    $('.toast-body').text(response)
    $('.toast').toast('show')
    cart_item_amount()
    add_item_to_preview_cart()
  }).fail(function () {
    $('.toast-body').text('Error while adding product to cart.')
    $('.toast').toast('show')
  })

}