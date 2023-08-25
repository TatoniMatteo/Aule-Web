const inputFile = document.getElementById("input-file");
const fileLabel = document.getElementById("file-label");

inputFile.addEventListener("change", function () {
    if (inputFile.files.length > 0) {
        const fileName = inputFile.files[0].name;
        const fileExtension = fileName.split(".").pop().toLowerCase();

        if (fileExtension === "csv") {
            fileLabel.innerHTML = `<i class="fas fa-file-csv"></i> ${fileName}`;
        } else {
            fileLabel.innerHTML = '<i class="fas fa-exclamation-circle"></i> Seleziona un file CSV';
        }
    } else {
        fileLabel.innerHTML = '<i class="fas fa-upload"></i> Seleziona un file';
    }
});

