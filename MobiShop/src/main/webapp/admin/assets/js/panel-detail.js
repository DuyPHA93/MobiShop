// Show image after uploaded
$('#file_upload').change(function(e) {
	const photo = document.getElementById('result_photo');
	const file = e.target.files[0];

	// Display
	photo.src = URL.createObjectURL(file);
	photo.onload = function() {
		URL.revokeObjectURL(photo.src) // free memory
	}
});