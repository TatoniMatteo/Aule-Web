const androidSwitch = document.getElementById("androidSwitch");
const switchText = document.querySelector(".switch-text");

androidSwitch.addEventListener("change", function () {
    if (this.checked) {
        switchText.textContent = "Si";
    } else {
        switchText.textContent = "No";
    }
});
