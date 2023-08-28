function displayErrorMessage(message) {
    const errorMessage = document.getElementById("error-message");
    errorMessage.textContent = message;
    errorMessage.style.display = "block";
}


const form = document.querySelector(".simple-form");
const giornoInput = document.getElementById("giorno");
const inizioInput = document.getElementById("inizio");
const fineInput = document.getElementById("fine");
const responsabileHidden = document.getElementById("responsabile");
const corsoSelect = document.getElementById("corso");
const tipoSelect = document.getElementById("tipo");
const errorMessage = document.getElementById("error-message");
const selectCorsoDiv = document.getElementById("selectCorso");
const selectRicorrenza = document.getElementById("tipoRicorrenza");
const fineRicorrenza = document.getElementById("fineRicorrenza");
const ricorrenzaFineContainer = document.getElementById("ricorrenzaFineContainer");
const space = document.getElementById("spacer");
const type = document.getElementById("type");

const saveButton = document.querySelector('button[type="submit"]');
const deleteButton = document.querySelector('button[type="reset"]');

const tuttiSwitch = document.getElementById("androidSwitch");

const tuttiSwitchContainer = document.getElementById("switch");
const tuttiLabel = document.getElementById("tuttiLabel");
const oldDate = giornoInput.value;

// Controllo per mostrare o nascondere la div selectCorso
if (tipoSelect.value !== "Lezione" || tipoSelect.value !== "Esame" || tipoSelect.value !== "Parziale") {
    selectCorsoDiv.style.display = "none";
}
tipoSelect.addEventListener("change", function () {
    const selectedTipo = tipoSelect.value;
    if (selectedTipo === "Lezione" || selectedTipo === "Esame" || selectedTipo === "Parziale") {
        selectCorsoDiv.style.display = "block";
    }
});


if (ricorrenzaFineContainer) {
//Controllo per mostrare la ricorrenza
    ricorrenzaFineContainer.style.display = "none";
    space.style.display = "none";
    selectRicorrenza.addEventListener("change", function () {
        const value = selectRicorrenza.value;
        if (value === "0") {
            ricorrenzaFineContainer.style.display = "none";
            space.style.display = "none";
        } else {
            ricorrenzaFineContainer.style.display = "block";
            space.style.display = "block";
        }
    });
}


if (tuttiSwitchContainer) {
    giornoInput.addEventListener("change", function () {
        if (oldDate !== giornoInput.value) {
            tuttiSwitch.checked = false;
            tuttiSwitchContainer.style.display = "none";
            tuttiLabel.style.display = "none";
        } else {
            tuttiSwitchContainer.style.display = "block";
            tuttiLabel.style.display = "block";
        }
    });

    tuttiSwitch.addEventListener("change", function () {
        if (!tuttiSwitch.checked) {
            saveButton.textContent = "Modifica";
            deleteButton.textContent = "Elimina";
        } else {
            saveButton.textContent = "Modifica Tutti";
            deleteButton.textContent = "Elimina Tutti";
        }
    });

}

form.addEventListener("submit", function (event) {
    event.preventDefault();
    console.log("tipo 1");
    if (validateData()) {
        type.value = 1;
        form.submit();
    }
});

form.addEventListener("reset", function (event) {
    event.preventDefault();
    console.log("Tipo 2");
    if (validateData()) {
        type.value = 2;
        form.submit();
    }
});

function validateData() {
    // Validazione: ora inizio deve essere antecedente ad ora fine
    const inizio = new Date(`1970-01-01T${inizioInput.value}`);
    const fine = new Date(`1970-01-01T${fineInput.value}`);
    if (inizio >= fine) {
        displayErrorMessage("L'ora di inizio deve essere antecedente all'ora di fine.");
        return false;
    }

    // Validazione: sia ora inizio, sia ora fine devono essere multipli di 15 minuti
    const minutiInizio = inizio.getMinutes();
    const minutiFine = fine.getMinutes();
    if (minutiInizio % 15 !== 0 || minutiFine % 15 !== 0) {
        displayErrorMessage("L'ora di inizio e l'ora di fine devono essere multipli di 15 minuti.");
        return false;
    }

    // Validazione: se visibile il corso deve essere scelto
    const tipoSelected = tipoSelect.value;
    if (tipoSelected === "Lezione" || tipoSelected === "Esame" || tipoSelected === "Parziale") {
        if (corsoSelect.value === "") {
            displayErrorMessage("Devi scegliere un corso per l'evento " + tipoSelected);
            return false;
        }
    }

    // Validazione: il responsabile deve essere impostato
    if (!responsabileHidden.value) {
        displayErrorMessage("Devi prima scegliere un responsabile.");
        return false;
    }

    if (ricorrenzaFineContainer) {
        if (selectRicorrenza.value !== "0" && fineRicorrenza.value === "") {
            displayErrorMessage("Devi selezionare una data di fine ricorrenza.");
            return false;
        }

        const dataEvento = new Date(giornoInput.value);  // Sostituisci "giornoInput" con l'id corretto del campo data dell'evento
        const dataFineRicorrenza = new Date(fineRicorrenza.value);

        if (dataFineRicorrenza <= dataEvento) {
            displayErrorMessage("La data di fine della ricorrenza deve essere successiva alla data dell'evento.");
            return;
        }
    }

    return true;
}
