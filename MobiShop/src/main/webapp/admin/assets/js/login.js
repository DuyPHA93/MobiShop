const loginBtn = document.querySelector('.login-button');
const usernameInput = document.querySelector('input[name="username"]');
const passwordInput = document.querySelector('input[name="password"]');
const frm = document.getElementById('frm');

loginBtn.addEventListener('click', function(e) {
	e.preventDefault();
	let isValid = true;
	let msg = "";
	const username = usernameInput.value;
	const password = passwordInput.value;
	
	if (!username) {
		msg = "Username is required !";
		isValid = false;
	} else if (!password) {
		msg = "Password is required !";
		isValid = false;
	}
	
	if (!isValid) {
		alert(msg);
		return false;
	}
	
	frm.submit();
})