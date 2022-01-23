var btnLoadCities
var btnAddCity
var btnUpdateCity
var btnDeleteCity
var dropdownCities
var fieldCityName
var fieldCityCode

$(document).ready(function () {
	btnLoadCities = $("#btnLoadCities")
	dropdownCities = $("#dropDownCities")

	btnAddCity = $("#btnAddCity")
	btnUpdateCity = $("#btnUpdateCity").prop("disabled", true)
	btnDeleteCity = $("#btnDeteleCity").prop("disabled", true)

	fieldCityName = $("#fieldCityName")
	fieldCityCode = $("#fieldCityCode")

	loadCities()

	btnLoadCities.click(function () {
		loadCities()
	})

	dropdownCities.on("change", function () {
		updateFormState()
	})

	btnAddCity.click(function () {
		if (btnAddCity.val() == "Add") {
			addCity()
		} else {
			changeFormStateToNew()
		}
	})

	btnUpdateCity.click(function () {
		updateSelectedCity()
	})

	btnDeleteCity.click(function () {
		deleteSelectedCity()
	})
})

function deleteSelectedCity() {
	optionValue = dropdownCities.val()
	cityId = optionValue.split("-")[0]
	url = contextPath + "cities/delete/" + cityId;
	$.ajax({
		type: 'delete',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		}
	}).done(function () {
		alert("The city has been deleted")
		$("#dropDownCities option[value='" + optionValue + "']").remove();
		changeFormStateToNew()
	})
}

function validateFormCity() {
	formCity = document.getElementById("formCity")
	if (!formCity.checkValidity()) {
		formCity.reportValidity()
		return false
	}
	return true
}

function updateSelectedCity() {
	if (!validateFormCity()) {
		return
	}

	url = contextPath + "cities/save"
	cityName = fieldCityName.val()
	cityCode = fieldCityCode.val()
	cityId = dropdownCities.val().split("-")[0]

	json = {id: cityId, name: cityName, code: cityCode}
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function (cityId) {
		$("#dropDownCities option:selected").text(cityName)
		$("#dropDownCities option:selected").val(cityId + "-" + cityCode)
		alert("The city has been updated")
		changeFormStateToNew()
	})

}

function addCity() {
	if (!validateFormCity()) {
		return
	}
	
	url = contextPath + "cities/save"
	cityName = fieldCityName.val()
	cityCode = fieldCityCode.val()
	json = {name: cityName, code: cityCode}
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function () {
		alert("New city added")
	})
}


function changeFormStateToNew() {
	btnAddCity.val("Add")
	$("#labelCityName").text("City:")
	
	btnUpdateCity.prop("disabled", true)
	btnDeleteCity.prop("disabled", true)
	
	fieldCityName.val("").focus()
	fieldCityCode.val("")
}

function updateFormState() {
	btnAddCity.prop("value", "New")
	btnUpdateCity.prop("disabled", false)
	btnDeleteCity.prop("disabled", false)

	// Show city name and city code in the field when select a city
	$("#labelCityName").text("Selected City:")
	fieldCityName.val($("#dropDownCities option:selected").text())

	cityCode = dropdownCities.val().split("-")[1]
	fieldCityCode.val(cityCode)
}

function loadCities() {
	url = contextPath + "cities/list"
	$.get(url, function (responseJSON) {
		dropdownCities.empty()
		$.each(responseJSON, function (index, city) {
			optionValue = city.id + "-" + city.code
			$("<option>").val(optionValue).text(city.name).appendTo(dropdownCities)
		})
	}).done(function () {
		btnLoadCities.val("Refresh City List")
	})
}