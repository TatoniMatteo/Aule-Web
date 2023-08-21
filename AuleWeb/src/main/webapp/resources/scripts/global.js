const url = window.location.href;
var menuItems = document.querySelectorAll('.sidebar li');
menuItems.forEach(function (item) {
    item.classList.remove('active');
});

var activeItem = null;

// Cerca tra tutte le voci di menu per trovare una corrispondenza con l'URL
menuItems.forEach(function (item) {
    const itemName = item.textContent.toLowerCase().trim();
    if (url.includes(itemName)) {
        activeItem = item;
    }
});

// Se nessuna corrispondenza è stata trovata, imposta una voce di default come attiva
if (!activeItem) {
    activeItem = document.querySelector('.sidebar li a[href="amministrazione?page=dashboard"]').parentElement;
}

activeItem.classList.add('active');
