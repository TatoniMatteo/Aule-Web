const inputFile = document.getElementById("input-file");
const fileLabel = document.getElementById("file-label");
const importButton = document.getElementById("import_button");


inputFile.addEventListener("change", function () {
    if (inputFile.files.length > 0) {
        const fileName = inputFile.files[0].name;
        const fileExtension = fileName.split(".").pop().toLowerCase();

        if (fileExtension === "csv") {
            fileLabel.innerHTML = `<i class="fas fa-file-csv"></i> ${fileName}`;
            fileLabel.classList.remove("error");
        } else {
            fileLabel.innerHTML = '<i class="fas fa-exclamation-circle"></i> Seleziona un file CSV';
            fileLabel.classList.add("error");
        }
    } else {
        fileLabel.innerHTML = '<i class="fa-solid fa-file-arrow-down"></i> Seleziona un file';
        fileLabel.classList.remove("error");
    }
});

importButton.addEventListener("click", function (event) {
    if (!isValidInput()) {
        event.preventDefault();
        fileLabel.innerHTML = '<i class="fas fa-exclamation-circle"></i> Devi prima selezionare un file CSV';
        fileLabel.classList.add("error");
    }
});

function isValidInput() {
    const files = inputFile.files;
    if (files.length > 0) {
        const fileName = inputFile.files[0].name;
        const fileExtension = fileName.split(".").pop().toLowerCase();
        return fileExtension === "csv";
    }
    return false;
}


