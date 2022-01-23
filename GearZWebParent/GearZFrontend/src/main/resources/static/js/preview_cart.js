$(document).ready(function () {
  preview_cart()

  var url = context_path + "amount"
  $.get(url, function (response) {
    $("<strong>").text(response).appendTo($(".cart_bt"))
  })
})

function preview_cart() {
  var url = context_path + "cart-items"
  total = 0;
  $.get(url, function (response) {
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
      $("<strong>Total</strong><span>$" + total.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + "</span>").appendTo($("#grand-total"))
  })
}