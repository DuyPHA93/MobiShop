$('.product-category-side .menu .menu-badge').click(function() {
	const item = $(this).closest('li');
	item.toggleClass("open-submenu");
})