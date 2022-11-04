$('#receive_order_button').click(function() {
	$('#_action').val('receiveOrder');
	
	$('#frm').submit();
})

$('#delivery_order_button').click(function() {
	$('#_action').val('deliveryOrder');
	
	$('#frm').submit();
})

$('#confirm_delivered_button').click(function() {
	$('#_action').val('deliveredOrder');
	
	$('#frm').submit();
})

$('#confirm_complete_button').click(function() {
	$('#_action').val('completeOrder');
	
	$('#frm').submit();
})

$('#cancel_order_btn').click(function() {
	$('#_action').val('cancelOrder');
	
	$('#frm').submit();
})