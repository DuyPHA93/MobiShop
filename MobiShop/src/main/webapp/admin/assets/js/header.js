const accountInfo = document.querySelector('.account-info');

accountInfo.addEventListener('click', function() {
	const menu = this.querySelector('ul');
	menu.classList.toggle('hide');
})