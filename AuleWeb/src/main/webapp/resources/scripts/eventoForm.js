function displayErrorMessage(message) {
    const errorMessage = document.getElementById("error-message");
    errorMessage.textContent = message;
    errorMessage.style.display = "block";
}


const form = document.querySelector(".simple-form");
const inizioInput = document.getElementById("inizio");
const fineInput = document.getElementById("fine");
const responsabileHidden = document.getElementById("responsabile");
const corsoSelect = document.getElementById("corso");
const tipoSelect = document.getElementById("tipo");
const errorMessage = document.getElementById("error-message");
const selectCorsoDiv = document.getElementById("selectCorso");
const selectRicorrenza = document.getElementById("tipoRicorrenza");
const ricorrenzaFineContainer = document.getElementById("ricorrenzaFineContainer");
const space = document.getElementById("spacer");

console.log(selectRicorrenza);

// Controllo per mostrare o nascondere la div selectCorso
if (tipoSelect.value !== "Lezione") {
    selectCorsoDiv.style.display = "none";
}
tipoSelect.addEventListener("change", function () {
    const selectedTipo = tipoSelect.value;
    if (selectedTipo === "Lezione") {
        selectCorsoDiv.style.display = "block";
    } else {
        selectCorsoDiv.style.display = "none";
    }
});


//Controllo per mostrare la ricorrenza
ricorrenzaFineContainer.style.display = "none";
space.style.display = "none";
selectRicorrenza.addEventListener("change", function () {
    const value = selectRicorrenza.value;
    console.log(value);
    if (value === "0") {
        ricorrenzaFineContainer.style.display = "none";
        space.style.display = "none";
    } else {
        ricorrenzaFineContainer.style.display = "block";
        space.style.display = "block";
    }
});


form.addEventListener("submit", function (event) {
    // Resetta il messaggio di errore
    errorMessage.textContent = "";
    errorMessage.style.display = "none";
    // Validazione: ora inizio deve essere antecedente ad ora fine
    const inizio = new Date(`1970-01-01T${inizioInput.value}`);
    const fine = new Date(`1970-01-01T${fineInput.value}`);
    if (inizio >= fine) {
        event.preventDefault();
        displayErrorMessage("L'ora di inizio deve essere antecedente all'ora di fine.");
        return;
    }

    // Validazione: sia ora inizio, sia ora fine devono essere multipli di 15 minuti
    const minutiInizio = inizio.getMinutes();
    const minutiFine = fine.getMinutes();
    if (minutiInizio % 15 !== 0 || minutiFine % 15 !== 0) {
        event.preventDefault();
        displayErrorMessage("L'ora di inizio e l'ora di fine devono essere multipli di 15 minuti.");
        return;
    }

    // Validazione: il responsabile deve essere impostato
    if (!responsabileHidden.value) {
        event.preventDefault();
        displayErrorMessage("Il campo responsabile deve essere impostato.");
        return;
    }
});



