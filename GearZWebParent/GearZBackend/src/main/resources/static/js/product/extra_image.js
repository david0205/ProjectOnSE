var extraImagesCount = 0;

$(document).ready(function() {
    $("input[name='extraImage']").each(function(index) {
        extraImagesCount++;
        $(this).change(function() {
            if (!checkFileSize(this)) {
                return;
            }
            showAddedExtraImage(this, index);
        });
    });
    
    $("a[name='removeExtraImageButton']").each(function(index) {
       $(this).click(function() {
           removeExtraImage(index);
       });
    });
});

function showAddedExtraImage(fileInput, index) {
    var file = fileInput.files[0];

    fileName = file.name;
    imageNameHiddenField = $("#imageName" + index);
    if (imageNameHiddenField.length) {
        imageNameHiddenField.val(fileName);
    }

	var reader = new FileReader();
	reader.onload = function(e) {
		$("#extraProductImg" + index).attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
    if (index >= extraImagesCount - 1) {
        addNextExtraImageSection(index + 1);
    }
}

function addNextExtraImageSection(index) {
    htmlExtra = `
    <div class="floated_box" id="divExtraImage${index}">
        <div class="img-formgroup form-group border text-center rounded mr-2 d-block">
            <div class="mb-2">
                <label for="imageFile" id="extraImageHeader${index}">Extra Image #${index + 1}</label>
            </div>
            <img id="extraProductImg${index}" src="${default_img}" alt="">
            <input type="file" class="form-control-file mt-4" name="extraImage"
                accept="image/*" onchange="showAddedExtraImage(this, ${index})">
        </div>
    </div>
    `;

    removeBtn = `
        <a role="button" href="javascript:removeExtraImage(${index - 1})" style="color: #fff; text-decoration: none;"><i class="fas fa-times-circle"></i></a>
    `;
    $("#main_container").append(htmlExtra);
    $("#extraImageHeader" + (index - 1)).append(removeBtn);
    extraImagesCount++;
}

function removeExtraImage(index) {
    $("#divExtraImage" + index).remove();
}