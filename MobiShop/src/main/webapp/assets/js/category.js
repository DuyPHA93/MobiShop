$(document).ready(function() {
	$(".category .caption").click(function() {
		const category = $(this).closest('.category');
		category.find('.menu').toggle('collapse');
	});
})