$('#choose_all').change(function(e) {
	$('.choose').each(function(index, element) {
		if ($(e.target).is(':checked')) {
			$(element).prop('checked', true);
		} else {
			$(element).prop('checked', false);
		}

		$(element).change();
	})
})

$('.choose').change(function(e) {
	if ($(e.target).is(':checked')) {
		$(e.target).closest('tr').addClass("row-warning");
	} else {
		$(e.target).closest('tr').removeClass("row-warning");
	}
})

$('.table-paging ul li.disabled').click(function(e) {
	e.preventDefault();
});

$('#select_perPage').change(function() {
	$('#frm').submit();
})

$('#select_filter').change(function() {
	$('#frm').submit();
})

$('.action .delete').click(function(e) {
	e.preventDefault();

	if (confirm("Bạn có chắc là muốn xóa không ?")) {
		const url = window.location.pathname + '?action=delete&id=' + $(this).data('id');

		$.post(url, function(data, status) {
			location.reload(true);
		});
	}
})

$('.action .disable').click(function(e) {
	e.preventDefault();

	if (confirm("Bạn có chắc là muốn tắt không ?")) {
		const url = window.location.pathname + '?action=disable&id=' + $(this).data('id');

		$.post(url, function(data, status) {
			location.reload(true);
		});
	}
})