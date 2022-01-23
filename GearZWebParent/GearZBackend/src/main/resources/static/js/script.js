$(document).ready(function() {
	$("#btnCancel").on("click", function() {
		window.opener.location.reload();
		window.close();
	});

	$("#imageFile").change(function() {
		if (!checkFileSize(this)) {
			return;
		}
		showAddedImage(this);
	});
	
});

function checkFileSize(fileInput) {
	sizeOfFile = fileInput.files[0].size;
	if (sizeOfFile > 1048576) {
		fileInput.setCustomValidity("image file size must not > 1MB");
		fileInput.reportValidity();
		return false;
	} else {
		fileInput.setCustomValidity("");
		return true;
	}
}

function showAddedImage(imageFile) {
	var file = imageFile.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#profilePic, #categoryImg, #brandImg, #productImg, #site-logo").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
}