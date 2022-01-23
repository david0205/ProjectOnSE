function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}
function showError(message) {
	showModalDialog("Error", message);
}
function showWarning(message) {
	showModalDialog("Warning", message);
}
function showConfirmDelete(link, entityName) {
	entity = link.attr("entity");
	$('#modalBody').text("Are you sure you want to delete " + entityName + " " + entity + "?");
	$('#btnYes').attr('href', link.attr('href'));
	$('#confirmDeleteModalDialog').modal();
}