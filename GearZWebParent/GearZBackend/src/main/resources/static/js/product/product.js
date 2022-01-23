$(document).ready(function() {
    $("#btnCancel").on("click", function() {
        window.opener.location.reload();
        window.close();
    });

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    $("#brand").change(function() {
        $("#category").empty();
        getCategories(); 
    });
    getCategoriesForNewProduct();

});

function getCategories() {
    brand_url = brandURL + "/" + $("#brand").val() + "/categories";
    $.get(brand_url, function(responseJson) {
        $.each(responseJson, function(index, category) {
            $("<option>").val(category.id).text(category.name).appendTo($("#category"));
        });
    });
}

function getCategoriesForNewProduct() {
    categoryIdField = $("#categoryId");
    editMode = false;

    if (categoryIdField.length) {
        editMode = true;
    }

    if (!editMode) {
        getCategories();
    }
}

function checkExisted(form) {
    prodId = $("#id").val();
    prodName = $("#name").val();
    csrfValue = $("input[name='_csrf']").val();
    params = {id: prodId, name: prodName, _csrf: csrfValue};
    
    $.post(_url, params, function(response) {
        if (response == "OK") {
            form.submit();
        } else if (response == "Exist") {
            showWarning("Product '" + prodName + "'" + " is already existed.");
        }
    }).fail(function() {
        showError("Could not connect to server");
    });
    
    return false;
}