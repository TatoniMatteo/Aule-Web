// Ottieni il riferimento al modulo
const form = document.querySelector('.simple-form');
// Aggiungi un ascoltatore per l'evento di invio del modulo
form.addEventListener('submit', function (event) {
    event.preventDefault(); // Evita l'invio del modulo automatico

    // Recupero dei dati dal form
    let aulaKey = parseInt(form.getAttribute('data-key'), 10);
    let aulaVersion = parseInt(form.getAttribute('data-version'), 10);
    const nomeAula = form.querySelector('#nome').value;
    const luogo = form.querySelector('#luogo').value;
    const edificio = form.querySelector('#edificio').value;
    const piano = parseInt(form.querySelector('#piano').value, 10);
    const capienza = parseInt(form.querySelector('#capienza').value, 10);
    const preseElettriche = parseInt(form.querySelector('#prese_elettriche').value, 10);
    const preseRete = parseInt(form.querySelector('#prese_rete').value, 10);
    const note = form.querySelector('#note').value;
    const responsabileKey = parseInt(form.querySelector('#responsabile').value, 10);
    const attrezzatureKeys = Array
            .from(form.querySelector('#attrezzaturaTable').querySelectorAll('tbody tr'))
            .map(row => parseInt(row.getAttribute('data-value'), 10));
    const gruppiKeys = Array
            .from(form.querySelectorAll('.checkbox-container input[type="checkbox"]:checked'))
            .map(checkbox => parseInt(checkbox.value, 10));
    // Validazione dei dati
    if (!nomeAula || !luogo || !edificio || isNaN(piano) || isNaN(capienza) || isNaN(preseElettriche) ||
            isNaN(preseRete) || isNaN(responsabileKey) || attrezzatureKeys.includes(NaN) || gruppiKeys.includes(NaN)) {
        console.error('Tutti i campi obbligatori devono essere compilati con valori validi.');
        return; // Non procedere con l'invio dei dati
    }

    if (piano < 0 || capienza <= 0 || preseElettriche < 0 || preseRete < 0 || responsabileKey <= 0 ||
            attrezzatureKeys.some(key => key <= 0) || gruppiKeys.some(key => key <= 0)) {
        console.error('I valori numerici devono essere maggiori di 0 o rispettare le condizioni specificate.');
        return; // Non procedere con l'invio dei dati
    }

    if (new Set(attrezzatureKeys).size !== attrezzatureKeys.length || new Set(gruppiKeys).size !== gruppiKeys.length) {
        console.error('Le liste non possono contenere valori duplicati.');
        return; // Non procedere con l'invio dei dati
    }

    if (!Number.isInteger(aulaKey) || aulaKey <= 0) {
        aulaKey = -1;
    }

    if (!Number.isInteger(aulaVersion) || aulaVersion < 0) {
        aulaVersion = 0;
    }

    // Creazione dell'oggetto FormData
    const formData = new FormData();
    formData.append('id', aulaKey);
    formData.append('versione', aulaVersion);
    formData.append('nome', nomeAula);
    formData.append('luogo', luogo);
    formData.append('edificio', edificio);
    formData.append('piano', piano);
    formData.append('capienza', capienza);
    formData.append('preseElettriche', preseElettriche);
    formData.append('preseRete', preseRete);
    formData.append('note', note);
    formData.append('responsabile', responsabileKey);
    formData.append('attrezzature', attrezzatureKeys.join(','));
    formData.append('gruppi', gruppiKeys.join(','));

    const xhr = new XMLHttpRequest();
    xhr.open('post', 'managedata?type=1&object=1', true);

    xhr.addEventListener('load', function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseHtml = xhr.responseText;

            // Sostituisci l'intero contenuto del DOM con l'HTML ricevuto
            document.documentElement.innerHTML = responseHtml;
        } else {
            // Errore di rete o del server
            console.error('Errore di rete o del server:', xhr.statusText);
        }
    });

    xhr.send(formData);
});



