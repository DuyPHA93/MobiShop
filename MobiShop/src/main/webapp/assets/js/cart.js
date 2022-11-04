const onInputQty = function(e, input) {
	$(input).val($(e).val());
	
	if ($('#update-cart-btn').hasClass('disabled')) {
		$('#update-cart-btn').removeClass('disabled');
	}
}

const deleteItem = function(e) {
	$(e).closest('tr').remove();
	$('#frmCart').submit();
}

$('#checkout-btn').click(function() {
	$.get("auth", {action: 'checkLoginAjax'}, function(data, status) {
		if (data.trim() == "false") {
			alert("Bạn cần phải đăng nhập để tiếp tục tiến hành thanh toán.");
			window.location.replace(ctx + "/auth");
		} else {
			window.location.replace(ctx + "/checkout");
		}
	})
})