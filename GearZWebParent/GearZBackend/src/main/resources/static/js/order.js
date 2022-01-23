$(document).ready(function () {
	format_order_amounts()
	format_product_amounts()

	$("#productList").on("change", ".input-quantity", function (e) {
		update_subtotal_by_quantity($(this))
		update_order_amounts()
	})

	$("#productList").on("change", ".input-unit-price", function (e) {
		update_subtotal_by_unit_price($(this))
		update_order_amounts()
	})

	$("#productList").on("change", ".input-prod-cost", function (e) {
		update_order_amounts()
	})

	$("#productList").on("change", ".input-shipping-cost", function (e) {
		update_order_amounts()
	})

	$("#pills-products").on("click", "#add-product-btn", function (e) {
		e.preventDefault()
		url = $(this).attr("href")
		$("#addProductModal").on("show.bs.modal", function () {
			$(this).find("iframe").attr("src", url)
		})
		$("#addProductModal").modal()
	})

	$("#productList").on("click", ".link-remove-product", function (e) {
		e.preventDefault()
		if (is_only_one_product()) {
			showWarning("Order must have at least 1 product")
		} else {
			remove_product($(this))
			update_order_amounts()
		}
	})

	$("#trackingList").on("click", ".link-remove-tracking", function (e) {
		e.preventDefault()
		delete_tracking_record($(this))
		update_tracking_count_numbers()
	})

	$("#pills-tracking").on("click", "#link-add-tracking", function (e) {
		e.preventDefault()
		add_new_tracking_record()
	})

	$("#trackingList").on("click", ".dropDownStatus", function (e) {
		dropDownList = $(this)
		row_number = dropDownList.attr("rowNumber")
		selected_option = $("option:selected", dropDownList)

		defaultDetail= selected_option.attr("defaultDescription")
		$("#trackingDetail" + row_number).text(defaultDetail)
	})
})

function add_new_tracking_record() {
	html = generate_tracking_record()
	$("#trackingList").append(html)
}

function generate_tracking_record() {
	nextCount = $(".hidden-tracking-id").length + 1
	trackingStatusDetail = $(".hidden-tracking-status-detail").val()
	rowId = "rowTracking" + nextCount
	emptyLineId = "emptyLine" + nextCount
	trackingDetailId = "trackingDetail" + nextCount
	currentDateTime = formatCurrentDateTime()

	html = `
	<div class="border rounded p-2" id="${rowId}"">
		<input type="hidden" name="trackingId" value="0" class="hidden-tracking-id">
		<div class="col-2">
			<div class="div-count-tracking">${nextCount}</div>
			<div class="mt-1"><a href="" class="fas fa-trash text-muted link-remove-tracking" rowNumber="${nextCount}"></a></div>
		</div>
		<div class="col-10">
			<div class="form-group row">
				<label class="col-form-label">Time:</label>
				<div class="col">
					<input type="datetime-local" name="trackingDate" value="${currentDateTime}" class="form-control"
							required>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-form-label">Status:</label>
				<div class="col">
					<select name="trackingStatus" class="form-control dropDownStatus" rowNumber="${nextCount}">
	`
	html += $("#trackingStatusOptions").clone().html();
	html += `
					</select>
				</div>
			</div>

			<div class="form-group row">
				<label class="col-form-label">Detail:</label>
				<div class="col">
					<textarea class="form-control" name="trackingDetail" id="${trackingDetailId}" cols="10" rows="2" required></textarea>
				</div>
			</div>
		</div>
	</div>
	<div id="'emptyLine' + ${nextCount}" class="row">&nbsp;</div>
	`
	return html
}

function formatCurrentDateTime() {
	date = new Date()
	year = date.getFullYear()
	month = date.getMonth() + 1
	day = date.getDate()
	hour = date.getHours()
	minute = date.getMinutes()
	second = date.getSeconds()

	if (month < 10) {
		month = "0" + month
	}
	if (day < 10) {
		day = "0" + day
	}
	if (hour < 10) {
		hour = "0" + hour
	}
	if (minute < 10) {
		minute = "0" + minute
	}
	if (second < 10) {
		second = "0" + second
	}

	return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second
}

function delete_tracking_record(link) {
	row_number = link.attr("rowNumber")
	$("#rowTracking" + row_number).remove()
	$("#emptyLine" + row_number).remove()
}

function update_tracking_count_numbers() {
	$(".div-count-tracking").each(function (index, element) {
		element.innerHTML = "" + (index + 1)
	})
}

function remove_product(link) {
	row_number = link.attr("rowNumber")
	$("#row" + row_number).remove()
	$("#blankLine" + row_number).remove()
	$(".div-count").each(function (index, element) {
		element.innerHTML = "" + (index + 1)
	})
}

function add_product(productId, productName) {
	get_shipping_cost(productId)
}

function get_shipping_cost(productId) {
	selected_city = $("#city option:selected")
	cityId = selected_city.val()
	selected_district = $("#listDistrict option:selected")
	district_name = selected_district.text()

	request_url = contextPath + "get_shipping_cost"
	params = {productId: productId, cityId: cityId, district: district_name}
	$.ajax({
		type: 'post',
		url: request_url,
		beforeSend: function (xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfToken)
		},
		data: params,
	}).done(function (shippingCost) {
		get_product_info(productId, shippingCost)
	}).fail(function (err) {
		showWarning(err.responseJSON.message)
		get_product_info(productId, 0.0)
	}).always(function () {
		$("#addProductModal").modal("hide")
	})
}

function get_product_info(productId, shippingCost) {
	request_url = contextPath + "products/get/" + productId
	$.get(request_url, function (productJSON) {
		productName = productJSON.name
		mainImagePath = contextPath.substring(0, contextPath.length - 1) + productJSON.imagePath
		productCost = $.number(productJSON.cost, 2)
		productPrice = $.number(productJSON.price, 2)

		html = generate_product_info_to_order(productId, productName, mainImagePath, productCost, productPrice, shippingCost)
		$("#productList").append(html)
		update_order_amounts()
	}).fail(function (err) {
		showWarning(err.responseJSON.message)
	})
}

function generate_product_info_to_order(productId, productName, mainImagePath, productCost, productUnitPrice, shippingCost) {
	nextCount = $(".hidden-product-id").length + 1
	rowId = "row" + nextCount
	quantityId = "quantity" + nextCount
	priceId = "price" + nextCount
	subtotalId = "subtotal" + nextCount
	blankLineId = "blankLine" + nextCount

	html = `
	<div class="border rounded p-2" id="${rowId}">
		<input type="hidden" name="detailId" value="0">
		<input type="hidden" name="productId" value="${productId}" class="hidden-product-id">
		<div class="row">
			<div class="col-1">
				<div class="div-count">${nextCount}</div>
				<div><a href="" class="fas fa-trash text-muted link-remove-product" rowNumber="${nextCount}"></a></div>
			</div>
			<div class="col-2">
				<img src="${mainImagePath}" class="img-fluid">
			</div>
			<b>${productName}</b>
		</div>
		<div class="row m-1">
			<table class="col">
				<tr>
					<td>Product Cost:&nbsp;</td>
					<td>
						<input type="text" name="_productCost" class="form-control m-1 input-prod-cost" required rowNumber="${nextCount}" value="${productCost}">
					</td>
				</tr>
				<tr>
					<td>Quantity:&nbsp;</td>
					<td>
						<input type="number" name="_quantity" step="1" rowNumber="${nextCount}" class="form-control m-1 input-quantity" required id="${quantityId}" value="1">
					</td>
				</tr>
				<tr>
					<td>Shipping Cost:&nbsp;</td>
					<td>
						<input type="text" name="_productShippingCost" class="form-control m-1 input-shipping-cost" required value="${shippingCost}">
					</td>
				</tr>
			</table>
			<table class="col">
				<tr>
					<td>Unit Price:&nbsp;</td>
					<td>
						<input type="text" name="_productUnitPrice" class="form-control m-1 input-unit-price" rowNumber="${nextCount}" required id="${priceId}" value="${productUnitPrice}">
					</td>
				</tr>
				<tr>
					<td>Subtotal:&nbsp;</td>
					<td>
						<input type="text" name="_productSubtotal" class="form-control m-1 input-subtotal" readonly id="${subtotalId}" value="${productUnitPrice}">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="${blankLineId}" class="row">&nbsp;</div>
	`
	return html;
}

function is_already_added(productId) {
	product_exists = false
	$(".hidden-product-id").each(function (e) {
		aProductId = ($(this)).val()
		if (aProductId == productId) {
			product_exists = true
			return;
		}
	})
	return product_exists
}

function is_only_one_product() {
	productCount = $(".hidden-product-id").length
	return productCount == 1
}

function update_order_amounts() {
	total_cost = 0.0
	$(".input-prod-cost").each(function () {
		row_number = $(this).attr("rowNumber")
		quantity_value = $("#quantity" + row_number).val()

		product_cost = parseFloat($(this).val().replace(",", ""))
		total_cost += parseInt(quantity_value) * product_cost
	})
	$("#productCost").val($.number(total_cost, 2))

	order_subtotal = 0.0
	$(".input-subtotal").each(function () {
		product_subtotal = parseFloat($(this).val().replace(",", ""))
		order_subtotal += product_subtotal;
	})
	$("#subtotal").val($.number(order_subtotal, 2))

	shipping_cost = 0.0
	$(".input-shipping-cost").each(function () {
		product_shipping_cost = parseFloat($(this).val().replace(",", ""))
		shipping_cost += product_shipping_cost;
	})
	$("#shippingCost").val($.number(shipping_cost, 2))
	
	tax = parseFloat($("#tax").val().replace(",", ""))
	order_total = order_subtotal + tax + shipping_cost
	$("#total").val($.number(order_total, 2))
}

function update_subtotal_by_unit_price(input) {
	unit_price_value = input.val().replace(",", "")
	row_number = input.attr("rowNumber")
	quantity_field = $("#quantity" + row_number)
	quantity_value = parseFloat(quantity_field.val())

	new_subtotal = parseFloat(quantity_value) * parseFloat(unit_price_value)
	subtotal_field = $("#subtotal" + row_number)
	subtotal_field.val($.number(new_subtotal, 2))
}

function update_subtotal_by_quantity(input) {
	quantity_value = input.val()
	row_number = input.attr("rowNumber")
	unit_price_field = $("#price" + row_number)
	unit_price_value = parseFloat(unit_price_field.val().replace(",", ""))

	new_subtotal = parseFloat(quantity_value) * unit_price_value
	subtotal_field = $("#subtotal" + row_number)
	subtotal_field.val($.number(new_subtotal, 2))
}

function format_product_amounts() {
	$(".input-prod-cost").each(function () {
		format_number($(this))
	})
	$(".input-shipping-cost").each(function () {
		format_number($(this))
	})
	$(".input-unit-price").each(function () {
		format_number($(this))
	})
	$(".input-subtotal").each(function () {
		format_number($(this))
	})
}

function format_order_amounts() {
	format_number($("#productCost"))
	format_number($("#subtotal"))
	format_number($("#shippingCost"))
	format_number($("#tax"))
	format_number($("#total"))
}

function format_number(field_ref) {
	field_ref.val($.number(field_ref.val(), 2))
}

function remove_thousand_separator(field) {
	field.val(field.val().replace(",", ""))
}

function processFormBeforeSubmit() {
	selected_city = $("#city option:selected").text()
	selected_district = $("#listDistrict option:selected").text()
	selected_ward = $("#listWard option:selected").text()

	$("#cityName").val(selected_city)
	$("#districtName").val(selected_district)
	$("#wardName").val(selected_ward)

	remove_thousand_separator($("#productCost"))
	remove_thousand_separator($("#subtotal"))
	remove_thousand_separator($("#shippingCost"))
	remove_thousand_separator($("#tax"))
	remove_thousand_separator($("#total"))

	$(".input-prod-cost").each(function (e) {
		remove_thousand_separator($(this))
	})
	
	$(".input-unit-price").each(function (e) {
		remove_thousand_separator($(this))
	})
	$(".input-subtotal").each(function (e) {
		remove_thousand_separator($(this))
	})
	$(".input-shipping-cost").each(function (e) {
		remove_thousand_separator($(this))
	})
}