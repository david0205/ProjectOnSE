$(document).ready(function () {
  load_cities()

  $("#city").on("change", function () {
    load_districts_for_city()
  })

  $("#district").on("change", function () {
    load_wards_for_district()
  })
})

function reload_niceSelect() {
  $('.custom-select-form select').niceSelect('destroy');
  $('.custom-select-form select').niceSelect();
}

function load_wards_for_district() {
  selected_city = $("#district option:selected")
  districtId = selected_city.val();
  url = context_path + "wards/list_by_district/" + districtId;
  
  $.get(url, function (responseJSON) {
    $("#ward").empty().append('<option value="" selected>Ward*</option>')
    $.each(responseJSON, function (index, ward) {
      $("<option>").val(ward.id).text(ward.name).appendTo($("#ward"))
      reload_niceSelect()
    })
  })
}

function load_districts_for_city() {
  selected_city = $("#city option:selected")
  cityId = selected_city.val().split("-")[0];
	url = context_path + "districts/list_by_city/" + cityId;

  $.get(url, function (responseJSON) {
    $("#district").empty().append('<option value="" selected>District*</option>')
		$.each(responseJSON, function (index, district) {
			$("<option>").val(district.id).text(district.name).appendTo($("#district"))
      reload_niceSelect()
		})
	})
}

function load_cities() {
  url = context_path + "cities/list";
  $.get(url, function (responseJSON) {
		$.each(responseJSON, function (index, city) {
			optionValue = city.id
			$("<option>").val(optionValue).text(city.name).appendTo($("#city"))
      reload_niceSelect()
		})
	})
}