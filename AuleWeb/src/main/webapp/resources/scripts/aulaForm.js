// Ottieni il riferimento al modulo
const form = document.querySelector('.simple-form');

// Aggiungi un ascoltatore per l'evento di invio del modulo
form.addEventListener('submit', function (event) {
    event.preventDefault(); // Evita l'invio del modulo automatico

    // Recupera la chiave dell'aula se è una modifica
    let aulaKey = parseInt(form.getAttribute('data-key'), 10);
    let aulaVersion = parseInt(form.getAttribute('data-version'), 10);

    // Creazione dell'oggetto FormData
    const formData = new FormData(form);

    // Validazione dei dati
    if (
            !formData.get('nome') ||
            !formData.get('luogo') ||
            !formData.get('edificio') ||
            isNaN(parseInt(formData.get('piano'), 10)) ||
            isNaN(parseInt(formData.get('capienza'), 10)) ||
            isNaN(parseInt(formData.get('prese_elettriche'), 10)) ||
            isNaN(parseInt(formData.get('prese_rete'), 10)) ||
            isNaN(parseInt(formData.get('responsabile'), 10)) ||
            formData.get('attrezzaturaTable[]').some(val => isNaN(parseInt(val, 10))) ||
            formData.get('gruppi[]').some(val => isNaN(parseInt(val, 10)))
            ) {
        console.error('Tutti i campi obbligatori devono essere compilati con valori validi.');
        return; // Non procedere con l'invio dei dati
    }

    if (
            parseInt(formData.get('piano'), 10) < 0 ||
            parseInt(formData.get('capienza'), 10) <= 0 ||
            parseInt(formData.get('prese_elettriche'), 10) < 0 ||
            parseInt(formData.get('prese_rete'), 10) < 0 ||
            parseInt(formData.get('responsabile'), 10) <= 0 ||
            formData.get('attrezzaturaTable[]').some(val => parseInt(val, 10) <= 0) ||
            formData.get('gruppi[]').some(val => parseInt(val, 10) <= 0)
            ) {
        console.error('I valori numerici devono essere maggiori di 0 o rispettare le condizioni specificate.');
        return; // Non procedere con l'invio dei dati
    }

    if (new Set(formData.getAll('attrezzaturaTable[]')).size !== formData.getAll('attrezzaturaTable[]').length ||
            new Set(formData.getAll('gruppi[]')).size !== formData.getAll('gruppi[]').length) {
        console.error('Le liste non possono contenere valori duplicati.');
        return; // Non procedere con l'invio dei dati
    }

    if (!Number.isInteger(aulaKey) || aulaKey <= 0) {
        aulaKey = -1;
    }

    if (!Number.isInteger(aulaVersion) || aulaVersion < 0) {
        aulaVersion = 0;
    }

    // Aggiungi la chiave dell'aula all'oggetto FormData
    formData.set('id', aulaKey.toString());
    formData.set('versione', aulaVersion.toString());

    // Crea una nuova richiesta XMLHttpRequest
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'managedata?type=1&object=1', true);

    // Aggiungi un gestore per l'evento di completamento della richiesta
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            console.log('Risposta dalla servlet:', xhr.responseText);
        } else {
            console.error('Errore durante la richiesta:', xhr.statusText);
        }
    };

    // Invia la richiesta con i dati FormData
    xhr.send(formData);
});
