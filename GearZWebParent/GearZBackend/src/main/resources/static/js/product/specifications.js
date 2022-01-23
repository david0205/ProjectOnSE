var extraSpecDetailCount = 0;

$(document).ready(function() {
    $("input[name='specIDs']").each(function(index) {
        extraSpecDetailCount++;
    });

    $("a[name='removeSpecDetailButton']").each(function(index) {
       $(this).click(function() {
           removeSpecDetail(index);
       });
    });
    
    $("input[name='extraSpecDetailCount']").val(extraSpecDetailCount);
    $("#btnAddMoreDetail").click(function() {
        c = $("input[name='extraSpecDetailCount']").val(extraSpecDetailCount);
        console.log(c);
    })
});

function removeSpecDetail(index) {
    $("#specsSection" + index).remove();
}

function addNextSpecificationSection() {
    allSpecSections = $("[id^='specsSection']");
    specsDivsCount = allSpecSections.length;

    specs_section = `
    <div class="container-detail1 d-flex align-items-center mt-2" id="specsSection${specsDivsCount}">
        <input type="hidden" name="specsIDs" value="0"/>
        <div class="form-group d-flex align-items-center">
            <label class="form-control-placeholder " for="specName">Name:</label>
            <input type="text" name="specName" id="specName" class="form-control" required>
        </div>
        <div class="form-group d-flex ml-5">
            <label class="form-control-placeholder " for="specValue">Value:</label>
            <input type="text" name="specValue" id="specValue" class="form-control" required>
        </div>
    </div>
    `;

    $("#divProductSpecification").append(specs_section);
    
    previousDivSpecSection = allSpecSections.last();
    previousDivSpecId = previousDivSpecSection.attr("id");

    removeBtn = `
    <div class="remove-row ml-2">
        <a class="far fa-times-circle" role="button" style="text-decoration: none;" href="javascript:removeSpecificationSectionById('${previousDivSpecId}')"></a>
    </div>
    `;

    previousDivSpecSection.append(removeBtn);

    $("input[id='specName']").last().focus();

    extraSpecDetailCount++;
}

function removeSpecificationSectionById(id) {
    $("#" + id).remove();
}