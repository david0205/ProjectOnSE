var btnLoadDistrictsForWards
var btnAddWard
var btnUpdateWard
var btnDeleteWard
var dropDownDistrictsForWards
var dropDownWards
var fieldWardName

$(document).ready(function () {

	btnLoadDistrictsForWards = $("#btnLoadDistrictsForWards")
	dropDownDistrictsForWards = $("#dropDownDistrictsForWards")
	dropDownWards = $("#dropDownWards")

	btnAddWard = $("#btnAddWard")
	btnUpdateWard = $("#btnUpdateWard").prop("disabled", true)
	btnDeleteWard = $("#btnDeleteWard").prop("disabled", true)
	
	fieldWardName = $("#fieldWardName")

	load_districts_4_wards()

	btnLoadDistrictsForWards.click(function () {
		load_districts_4_wards()
	})

	dropDownDistrictsForWards.on("change", function () {
		load_wards_4_district()
	})

	dropDownWards.on("change", function () {
		changeFormStateToSelectedWard()
	})

	btnAddWard.click(function () {
		if (btnAddWard.val() == "Add") {
			add_ward()
		} else {
			changeFormStateToNew3()
		}
	})

	btnUpdateWard.click(function () {
		update_selected_ward()
	})

	btnDeleteWard.click(function () {
		delete_selected_ward()
	})
})

function delete_selected_ward() {
	wardId = dropDownWards.val()

	url = contextPath + "wards/delete/" + wardId;

	$.ajax({
		type: 'delete',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		}
	}).done(function () {
		alert("The selected ward has been deleted")
		$("#dropDownWards option[value='" + wardId + "']").remove()
		changeFormStateToNew3()
	})
}

function validateFormWard() {
	formWard = document.getElementById("formWard")
	if (!formWard.checkValidity()) {
		formWard.reportValidity()
		return false
	}
	return true
}

function update_selected_ward() {
	if (!validateFormWard()) {
		return
	}

	url = contextPath + "wards/save"
	wardId = dropDownWards.val()
	wardName = fieldWardName.val()

	selectedDistrict = $("#dropDownDistrictsForWards option:selected")
	districtId = selectedDistrict.val()
	districtName = selectedDistrict.text()

	json = {id: wardId, name: wardName, district: {id: districtId, name: districtName}}

	$.ajax({
		type: 'post',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function (wardId) {
		alert("The selected ward has been updated")
		$("#dropDownWards option:selected").text(wardName)
		changeFormStateToNew3()
	})
}

function add_ward() {
	if (!validateFormWard()) {
		return
	}
	
	url = contextPath + "wards/save"
	wardName = fieldWardName.val()

	selectedDistrict = $("#dropDownDistrictsForWards option:selected")
	districtId = selectedDistrict.val()
	districtName = selectedDistrict.text()

	json = {name: wardName, district: {id: districtId, name: districtName}}

	$.ajax({
		type: 'post',
		url: url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: JSON.stringify(json),
		contentType: 'application/json'
	}).done(function (wardId) {
		alert("New ward added")
		selectNewlyAddedWard(wardId, wardName)
	})
}

function load_wards_4_district() {
	selectedDistrict = $("#dropDownDistrictsForWards option:selected")
	districtId = selectedDistrict.val()
	url = contextPath + "wards/list_by_district/" + districtId;

	$.get(url, function (responseJSON) {
		dropDownWards.empty()
		$.each(responseJSON, function (index, ward) {
			$("<option>").val(ward.id).text(ward.name).appendTo(dropDownWards)
		})
	}).done(function () {
		changeFormStateToNew3()
	})
}

function load_districts_4_wards() {
	url = contextPath + "districts/list"
	$.get(url, function (responseJSON) {
		dropDownDistrictsForWards.empty()
		$.each(responseJSON, function (index, district) {
			optionValue = district.id
			$("<option>").val(optionValue).text(district.name).appendTo(dropDownDistrictsForWards)
		})
	}).done(function () {
		btnLoadDistrictsForWards.val("Refresh District List")
	})
}

function changeFormStateToNew3() {
	btnAddWard.val("Add")
	$("#labelWardName").text("Ward:")

	btnUpdateWard.prop("disabled", true)
	btnDeleteWard.prop("disabled", true)

	fieldWardName.val("").focus()
}

function changeFormStateToSelectedWard() {
	btnAddWard.prop("value", "New")
	btnUpdateWard.prop("disabled", false)
	btnDeleteWard.prop("disabled", false)

	$("#labelWardName").text("Selected Ward:")

	selectedWardName = $("#dropDownWards option:selected").text()
	fieldWardName.val(selectedWardName)
}

function selectNewlyAddedWard(wardId, wardName) {
	$("<option>").val(wardId).text(wardName).appendTo(dropDownWards)
	$("#dropDownWards option[value='" + wardId + "']").prop("selected", true)
	fieldWardName.val("").focus()
}