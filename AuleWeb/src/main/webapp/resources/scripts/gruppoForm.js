const searchInput = document.getElementById("searchInput");
const optionsList = document.getElementById("optionsList");
const categorie = optionsList.querySelectorAll(".categoria");
const optionItems = optionsList.querySelectorAll("li");
const selectedKeyInput = document.getElementById("selectedKey");

searchInput.addEventListener("blur", function () {
// Aggiorna le opzioni solo se l'input perde il focus
    setTimeout(function () {
        optionsList.style.display = "none";
    }, 200); // Aggiungi un ritardo per consentire la selezione dell'opzione
});
searchInput.addEventListener("input", function () {
    const searchTerm = searchInput.value.toLowerCase();
    let foundMatch = false;
    categorie.forEach(function (item) {
        const optionText = item.dataset.text.toLowerCase();
        if (optionText.includes(searchTerm)) {
            item.style.display = "block";
            foundMatch = true;
        } else {
            item.style.display = "none";
        }
    });
    // Mostra o nascondi l'opzione speciale "Nessuna corrispondenza"
    const noMatchOption = document.getElementById("no-match");
    if (foundMatch) {
        noMatchOption.style.display = "none";
    } else {
        noMatchOption.style.display = "flex";
        const userInputElement = noMatchOption.querySelector(".user-input");
        const value = capitalize(searchTerm);
        userInputElement.textContent = value;
        noMatchOption.setAttribute("data-text", value);
    }

    optionsList.style.display = "block";
});
optionItems.forEach(function (item) {
    item.addEventListener("click", function () {
        const selectedText = item.dataset.text;
        const selectedKey = item.dataset.key;
        searchInput.value = selectedText;
        selectedKeyInput.value = selectedKey;
        optionsList.style.display = "none";
    });
});
function capitalize(str) {
    const words = str.split(" ");
    const capitalizedWords = words.map(word => {
        if (word.length > 0) {
            return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
        } else {
            return word;
        }
    });
    return capitalizedWords.join(" ");
}
