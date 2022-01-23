var btnLoadCitiesForDistricts
var btnAddDistrict
var btnUpdateDistrict
var btnDeleteDistrict
var dropDownCitiesForDistricts
var dropDownDistricts
var fieldDistrictName

$(document).ready(function () {
	
	btnLoadCitiesForDistricts = $("#btnLoadCitiesForDistricts")
	dropDownCitiesForDistricts = $("#dropDownCitiesForDistricts")
	dropDownDistricts = $("#dropDownDistricts")
	
	btnAddDistrict = $("#btnAddDistrict")
	btnUpdateDistrict = $("#btnUpdateDistrict").prop("disabled", true)
	btnDeleteDistrict = $("#btnDeleteDistrict").prop("disabled", true)
	
	fieldDistrictName = $("#fieldDistrictName")
	
	loadCities4Districts()

	btnLoadCitiesForDistricts.click(function () {
		loadCities4Districts()
	})

	dropDownCitiesForDistricts.on("change", function () {
		loadDistricts4City()
	})

	dropDownDistricts.on("change", function () {
		changeFormStateToSelectedDistrict()
	})

	btnAddDistrict.click(function () {
		if (btnAddDistrict.val() == "Add") {
			addDistrict()
		} else {
			changeFormStateToNew2()
		}
	})

	btnUpdateDistrict.click(function () {
		updateSelectedDistrict()
	})

	btnDeleteDistrict.click(function () {
		deleteSelectedDistrict()
	})
})

function selectNewlyAddedDistrict(districtId, districtName) {
	$("<option>").val(districtId).text(districtName).appendTo(dropDownDistricts)
	$("#dropDownDistricts option[value='" + districtId + "']").prop("selected", true)
	fieldDistrictName.val("").focus()
}

function changeFormStateToNew2() {
	btnAddDistrict.val("Add")
	$("#labelDistrictName").text("District:")

	btnUpdateDistrict.prop("disabled", true)
	btnDeleteDistrict.prop("disabled", true)

	fieldDistrictName.val("").focus()
}

function changeFormStateToSelectedDistrict() {
	btnAddDistrict.prop("value", "New")
	btnUpdateDistrict.prop("disabled", false)
	btnDeleteDistrict.prop("disabled", false)

	$("#labelDistrictName").text("Selected District:")

	selectedDistrictName = $("#dropDownDistricts option:selected").text()
	fieldDistrictName.val(selectedDistrictName)
}

function deleteSelectedDistrict() {
	districtId = dropDownDistricts.val()

	url = contextPath + "districts/delete/" + districtId;

	$.ajax({
		type: 'delete',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		}
	}).done(function () {
		alert("The selected district has been deleted")
		$("#dropDownDistricts option[value='" + districtId + "']").remove()
		changeFormStateToNew2()
	})
}

function validateFormDistrict() {
	formDistrict = document.getElementById("formDistrict")
	if (!formDistrict.checkValidity()) {
		formDistrict.reportValidity()
		return false
	}
	return true
}

function updateSelectedDistrict() {
	if (!validateFormDistrict()) {
		return
	}

	url = contextPath + "districts/save"
	districtId = dropDownDistricts.val()
	districtName = fieldDistrictName.val()
	
	selectedCity = $("#dropDownCitiesForDistricts option:selected");
	cityId = selectedCity.val().split("-")[0];
	cityName = selectedCity.text()
	
	json = {id: districtId, name: districtName, city: {id: cityId, name: cityName}}
	
	$.ajax({
		type: 'post',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function (districtId) {
		alert("The selected district has been updated")
		$("#dropDownDistricts option:selected").text(districtName)
		changeFormStateToNew2()
	})
}


function addDistrict() {
	if (!validateFormDistrict()) {
		return
	}
	
	url = contextPath + "districts/save"
	districtName = fieldDistrictName.val()

	selectedCity = $("#dropDownCitiesForDistricts option:selected");
	cityId = selectedCity.val().split("-")[0];
	cityName = selectedCity.text()

	json = {name: districtName, city: {id: cityId, name: cityName}}

	$.ajax({
		type: 'post',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function (districtId) {
		alert("New district added")
		selectNewlyAddedDistrict(districtId, districtName)
	})
}

function loadDistricts4City() {
	selectedCity = $("#dropDownCitiesForDistricts option:selected");
	cityId = selectedCity.val().split("-")[0];
	url = contextPath + "districts/list_by_city/" + cityId;

	$.get(url, function (responseJSON) {
		dropDownDistricts.empty()
		$.each(responseJSON, function (index, district) {
			$("<option>").val(district.id).text(district.name).appendTo(dropDownDistricts)
		})
	}).done(function () {
		changeFormStateToNew2()
	})
}

function loadCities4Districts() {
	url = contextPath + "cities/list"
	$.get(url, function (responseJSON) {
		dropDownCitiesForDistricts.empty()
		$.each(responseJSON, function (index, city) {
			optionValue = city.id + "-" + city.code
			$("<option>").val(optionValue).text(city.name).appendTo(dropDownCitiesForDistricts)
		})
	}).done(function () {
		btnLoadCitiesForDistricts.val("Refresh City List")
	})
}