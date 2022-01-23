var drop_down_cities
var drop_down_district
var drop_down_ward

$(document).ready(function () {
	drop_down_cities = $("#city")
	drop_down_district = $("#listDistrict")
	drop_down_ward = $("#listWard")

	drop_down_cities.on("change", function () {
		load_districts_for_city2()
	})

	drop_down_district.on("change", function () {
		load_wards_4_district2()
	})
	// load_districts_for_city2()
})


function load_wards_4_district2() {
	selectedDistrict = $("#listDistrict option:selected")
	districtId = selectedDistrict.val()
	url = contextPath + "wards/list_by_district/" + districtId;

	$.get(url, function (responseJSON) {
		drop_down_ward.empty()
		$.each(responseJSON, function (index, ward) {
			$("<option>").val(ward.id).text(ward.name).appendTo(drop_down_ward)
		})
	}).fail(function () {
		showError("Error while loading district for selected district")
	})
}

function load_districts_for_city2() {
	selected_city = $("#city option:selected")
	cityId = selected_city.val()

	url = contextPath + "districts/list_by_city/" + cityId

	$.get(url, function (responseJSON) {
		drop_down_district.empty()
		$.each(responseJSON, function (index, district) {
			$("<option>").val(district.id).text(district.name).appendTo(drop_down_district)
		})
	}).fail(function () {
		showError("Error while loading district for selected city")
	})
}