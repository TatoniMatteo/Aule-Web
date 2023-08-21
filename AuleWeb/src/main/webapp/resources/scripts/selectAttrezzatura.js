const attrezzaturaInput = document.getElementById("attrezzaturaInput");
const attrezzatureContainer = document.querySelector(".attrezzature");
const attrezzatura = document.querySelectorAll(".attrezzatura");
const attrezzaturaTable = document.getElementById("attrezzaturaTable");

attrezzaturaInput.addEventListener("input", function () {
    const inputValue = attrezzaturaInput.value.toLowerCase();
    attrezzatura.forEach(option => {
        const optionText = option.textContent.toLowerCase();
        if (optionText.indexOf(inputValue) !== -1) {
            option.style.display = "block";
        } else {
            option.style.display = "none";
        }
    });
    attrezzatureContainer.style.display = "block";
});

attrezzatura.forEach(option => {
    option.addEventListener("click", function () {
        aggiungiAttrezzatura(option);
    });
});

document.addEventListener("click", function (event) {
    if (!attrezzatureContainer.contains(event.target) && event.target !== attrezzaturaInput) {
        attrezzatureContainer.style.display = "none";
    }
});

attrezzaturaTable.addEventListener("click", function (event) {
    if (event.target.classList.contains("action-button")) {
        const listItem = event.target.parentElement.parentElement;
        const attrezzaturaKey = listItem.getAttribute("data-value");
        const attrezzaturaNome = listItem.querySelector("td:first-child").textContent;
        const attrezzaturaCodice = listItem.querySelector("td:nth-child(2)").textContent;


        listItem.remove();

        const newOption = document.createElement("div");
        newOption.className = "option attrezzatura";
        newOption.setAttribute("data-value", attrezzaturaKey);
        newOption.textContent = `${attrezzaturaNome} (${attrezzaturaCodice})`;
        newOption.addEventListener("click", function () {
            aggiungiAttrezzatura(newOption);
        });

        attrezzatureContainer.appendChild(newOption);
    }
});


function parseOption(input) {
    const matches = input.match(/^(.*?) \((.*?)\)$/);
    return {
        nome: matches[1],
        codice: matches[2]
    };
}

function aggiungiAttrezzatura(option) {
    const selectedOption = parseOption(option.textContent);
    const selectedKey = option.getAttribute("data-value");
    const listItem = document.createElement("tr");
    listItem.setAttribute("data-value", selectedKey);
    listItem.innerHTML = `
        <td>${selectedOption.nome}</td>
        <td>${selectedOption.codice}</td>
        <td class="text-right"><button class="action-button bg-blue">Rimuovi</button></td>
    `;
    attrezzaturaTable.appendChild(listItem);
    option.remove();
}

        