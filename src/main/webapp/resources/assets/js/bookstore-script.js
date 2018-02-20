$(document).ready(function(e) {
	toggleCheckbox($('#cbToggle').is(":checked"));
	$("#sbAddress").change(function() {
		$(this).next().html('');
		getAddressAjax(this.value);
	});
	$('#cbToggle').change(function() {
		toggleCheckbox($(this).is(":checked"));
	});
});

var xmlHttpReq = false;
function getAddressAjax(addrId) {
	var url = 'address.json?action=LoadAddress&id=' + addrId;
	xmlHttpReq = new XMLHttpRequest();
	xmlHttpReq.open("GET", url, true);
	xmlHttpReq.send();
	xmlHttpReq.onreadystatechange = getAddressAjaxCallBack;
}

function getAddressAjaxCallBack() {
	if (xmlHttpReq.readyState == 4 && xmlHttpReq.status == 200) {
		var html = "";
		var jsonAddr = $.parseJSON(xmlHttpReq.responseText);
		if (!$.isEmptyObject(jsonAddr)) {
			html = "<h4>" + jsonAddr.firstName + "&nbsp;" + jsonAddr.lastName
					+ "</h4>";
			html += "<div style='margin-top: 20px'><h5>" + jsonAddr.street1
					+ ",</h5><h5>" + jsonAddr.street2 + "</h5>";
			html += "<h5>" + jsonAddr.city + ",</h5><h5>" + jsonAddr.state
					+ "&nbsp;&nbsp;" + jsonAddr.postalCode + "</h5></div>";
		}
		document.getElementById("divShippingAddress").innerHTML = html;
	}
}

function toggleCheckbox(checked) {
	if (checked) {
		$('#divSavedCard').show();
		$('#divCardDetails').hide();
	} else {
		$('#divSavedCard').hide();
		$('#divCardDetails').show();
	}
}