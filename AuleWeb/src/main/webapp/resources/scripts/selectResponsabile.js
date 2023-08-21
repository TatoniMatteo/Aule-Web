const responsabileInput = document.getElementById("responsabileInput");
const responsabiliContainer = document.querySelector(".responsabili");
const responsabile = document.querySelectorAll(".responsabile");
const responsabileKey = document.getElementById("responsabile");

responsabileInput.addEventListener("input", function () {
    const inputValue = responsabileInput.value.toLowerCase();
    responsabile.forEach(option => {
        const optionValue = option.textContent.toLowerCase();
        if (optionValue.indexOf(inputValue) !== -1) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    });
    responsabiliContainer.style.display = "block";
});

responsabile.forEach(option => {
    option.addEventListener("click", function () {
        responsabileInput.value = option.textContent;
        responsabileKey.value = option.getAttribute("data-value");
        responsabiliContainer.style.display = "none";
    });
});

document.addEventListener("click", function (event) {
    if (!responsabiliContainer.contains(event.target) && event.target !== responsabileInput) {
        responsabiliContainer.style.display = "none";
    }
});
