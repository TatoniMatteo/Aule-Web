const androidSwitch = document.getElementById("androidSwitch");
const switchText = document.querySelector(".switch-text");

if (androidSwitch) {
    androidSwitch.addEventListener("change", function () {
        if (androidSwitch.checked) {
            switchText.textContent = "Si";
            androidSwitch.value = true;
        } else {
            switchText.textContent = "No";
            androidSwitch.value = true;
        }
    });
}
