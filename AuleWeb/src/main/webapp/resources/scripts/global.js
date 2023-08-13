setActiveMenuItem();

function setActiveMenuItem() {
    var urlParams = new URLSearchParams(window.location.search);
    var currentPage = urlParams.get('page');

    var menuItems = document.querySelectorAll('.sidebar li');
    menuItems.forEach(function (item) {
        item.classList.remove('active');
    });

    var activeItem = document.querySelector('.sidebar li a[href="amministrazione?page=' + currentPage + '"]');
    console.log(activeItem);
    if (!activeItem) {
        activeItem = document.querySelector('.sidebar li a[href="amministrazione?page=dashboard"]');
    }
    activeItem.parentElement.classList.add('active');
}