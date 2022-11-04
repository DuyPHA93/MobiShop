$(".products-tab .tab").click(function() {
	const tabid = $(this).data('tabid');
	
	const parent = $(this).closest('.products-tab');

	parent.find('.tab').removeClass('active');
	$(this).addClass('active');

	parent.find('.content').removeClass('show');
	$('#' + tabid).addClass('show');
});

$('.product-carousel').flickity({
	// options
	cellAlign: 'left',
	contain: true,
	wrapAround: true,
	autoPlay: true,
	pauseAutoPlayOnHover: false,
	groupCells: '100%',
	pageDots: false
});