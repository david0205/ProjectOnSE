$(document).ready(function() {
	$('a[data-toggle="pill"]').on('show.bs.tab', function(e) {
		console.log($(e.target).attr('href'));
		sessionStorage.setItem('activeTab', $(e.target).attr('href'));
	});
	var activeTab = sessionStorage.getItem('activeTab');
	if (activeTab) {
		$('#v-pills-tab a[href="' + activeTab + '"]').tab('show');
	}

	//#region Delete buttons
	$('.button-delete-user').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'user');
	});
	
	$('.button-delete-category').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'category');
	});
	
	$('.button-delete-brand').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'brand');
	});

	$('.button-delete-product').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'product');
	});

	$('.button-delete-customer').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'customer');
	});

	$('.button-delete-rate').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'shipping rate');
	});

	$('.button-delete-order').on('click', function(e) {
		e.preventDefault();
		showConfirmDelete($(this), 'order');
	});
	//#endregion

	//#region Datatables
	$('#tableUsers').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0, 1, 5, 6, 7] },
			{ "width": "60px", "targets": [0, 6] }
		]
	});

	$('#tableCategories').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [10, 20, 30, 40],
		"autoWidth": false,
		"order": [], //Initial no order.
        "aaSorting": [],
		"columnDefs": [
			{ "searchable": false, "targets": [0, 1, 3, 4] },
			{ "orderable": false, "targets": [0, 1, 2, 3, 4] },
			{ "width": "60px", "targets": [0] }
		]
	});
	
	$('#tableBrands').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0, 3, 4] },
			{ "width": "60px", "targets": [0] }
			
		]
	});

	$('#tableProducts').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0] },
			{ "width": "60px", "targets": [0, 5] }
		]
	});

	$('#tableCustomers').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0, 6, 7] },
			{ "width": "60px", "targets": [6, 7] },
			{ "width": "30px", "targets": [0] }
		]
	});

	$('#tableShippingRates').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [4, 8, 12, 16, 20, 24, 27, 32, 36, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0, 5, 6] },
			{ "width": "30px", "targets": [0] }
		]
	});

	$('#tableOrders').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [10, 20, 30, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0] },
			{ "width": "30px", "targets": [0] }
		]
	});

	$('#tableOrdersForShipper').dataTable({
		"pagingType": "full_numbers",
		"lengthMenu": [10, 20, 30, 40],
		"autoWidth": false,
		"columnDefs": [
			{ "searchable": false, "targets": [0] },
			{ "width": "50px", "targets": [0] }
		]
	});
	//#endregion

	addFilterDatatable("#tableProducts", "#dropdownCategoryFilter", "#categoryFilter", "Categories");

	$(".button-product-detail").on("click", function(e) {
		e.preventDefault();
		linkDetailURL = $(this).attr("href");
		window.open(linkDetailURL, 'popup', "resizable=0,height=800,width=1000");
	});

	$('#logoutLink').on('click', function(e) {
		e.preventDefault();
		document.logout.submit();
	});

	$(".button-customer-detail").on('click', function (e) {
		e.preventDefault();
		detail_url = $(this).attr("href")
		$("#detailModal").modal("show").find(".modal-content").load(detail_url)
	})

	$(".customer-details-link").on('click', function (e) {
		e.preventDefault();
		detail_url = $(this).attr("href")
		$("#detailModal").modal("show").find(".modal-content").load(detail_url)
	})

	$(".button-order-detail").on('click', function (e) {
		e.preventDefault();
		detail_url = $(this).attr("href")
		$("#orderDetailModal").modal("show").find(".modal-content").load(detail_url)
	})
});

function addFilterDatatable(table_id, div_id, select_tag_id, columnName) {
	//Get a reference to the new datatable
	var table = $(table_id).DataTable();

	//Take the category filter drop down and append it to the datatables_filter div. 
    //You can use this same idea to move the filter anywhere withing the datatable that you want.
	$(table_id + "_filter.dataTables_filter").append($(div_id));

	//Get the column index for the Category column to be used in the method below ($.fn.dataTable.ext.search.push)
    //This tells datatables what column to filter on when a user selects a value from the dropdown.
    //It's important that the text used here (columnName) is the same for used in the header of the column to filter
	var categoryIndex = 0;
	$(table_id + " th").each(function (i) {
		if ($($(this)).html() == columnName) {
			categoryIndex = i;
			return false;
		}
	});

	//Use the built in datatables API to filter the existing rows by the 'columnName' column
	$.fn.dataTable.ext.search.push(
		function (settings, data, dataIndex) {
			var selectedItem = $(select_tag_id).val();
			var category = data[categoryIndex];
			if (selectedItem === "" || category.includes(selectedItem)) {
				return true;
			}
			return false;
		}
	);

	//Set the change event for the Category Filter dropdown to redraw the datatable each time a user selects a new filter.
	$(select_tag_id).change(function (e) {
		table.draw();
	});
	table.draw();
}

function openPopUp() {
	url = $("a[id='" + this.event.target.id + "']").attr('href');
	if (this.event.target.id == "btnAddProduct" || this.event.target.id.includes("btnEditProduct")) {
		window.open(url, 'popup', "resizable=0,height=800,width=1000");	
	} else {
		window.open(url, 'popup', "resizable=0,height=800,width=800");
	}
	return false;
}

function openPopUp2() {
	url = $('#account-item').attr('href');
	window.open(url, 'popup', "resizable=0,height=800,width=800");
	return false;
}