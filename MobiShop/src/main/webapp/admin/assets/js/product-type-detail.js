// Show image after uploaded
$('#_file').change(function(e) {
	const photo = document.getElementById('result_photo');
	const file = e.target.files[0];

	// Display
	photo.style.display = "block";
	photo.src = URL.createObjectURL(file);
	photo.onload = function() {
		URL.revokeObjectURL(photo.src) // free memory
	}
});