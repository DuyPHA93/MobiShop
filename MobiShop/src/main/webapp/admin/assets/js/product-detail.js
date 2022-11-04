$('#product_type_select').change(function() {
	const productTypeId = $(this).val();
	$.get("brand", {action: 'getBrandAjax', productTypeId: productTypeId}, function(data, status){
		$('#brand_select').html(data);
  	});
})