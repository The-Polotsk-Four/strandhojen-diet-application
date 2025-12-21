window.addEventListener("DOMContentLoaded", initApp);

const BASE_URL = "http://localhost:8080";

let residents = [];
let allergies = [];
let selectedResident = null;
let selectedAllergies = [];

async function initApp() {
    await loadResidents();
    await loadAllergies();
    setupEventListeners();
    renderAllAllergies();
}

function setupEventListeners() {
    document.getElementById("allergySearch")
        .addEventListener("input", onAllergySearch);

    document.getElementById("saveBtn")
        .addEventListener("click", saveNewAllergy);

    document.addEventListener("click", closeDropdown);
}

async function loadResidents() {
    try {
        const res = await fetch(`${BASE_URL}/api/residents`, {credentials: "include"});
        residents = await res.json();
        renderResidents(residents);
    } catch {
        console.error("Kunne ikke hente beboere");
    }
}

async function loadAllergies() {
    try {
        const res = await fetch(`${BASE_URL}/api/allergies`, {
            credentials: "include"
        });
    } catch {
        console.error("Kunne ikke hente allergier");
    }
}

function renderResidents(list) {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    list.forEach(resident => {
        const tr = document.createElement("tr");
        tr.style.cursor = "pointer";

        const allergiesHTML = resident.allergies?.length
            ? resident.allergies.map(a => `<li>${a.name}</li>`).join("")
            : "Ingen";

        tr.innerHTML = `
            <td>${resident.id}</td>
            <td>${resident.name}</td>
            <td>${resident.age ?? ""}</td>
            <td>${resident.weight ?? ""}</td>
            <td>${resident.height ?? ""}</td>
            <td>${resident.bmi ?? ""}</td>
            <td>${resident.floor}</td>
            <td>${resident.roomNumber}</td>
            <td>${resident.status ? "Aktiv" : "Inaktiv"}</td>
            <td><ul>${allergiesHTML}</ul></td>
        `;

        tr.addEventListener("click", () => openAllergyEditor(resident));

        tbody.appendChild(tr);
    });
}

function openAllergyEditor(resident) {


    selectedResident = resident;
    selectedAllergies = Array.isArray(resident.allergies) ? [...resident.allergies] : [];
    // selectedAllergies = [...resident.allergies];

    document.getElementById("allergySection").style.display = "block";
    document.getElementById("residentNameHeader").textContent =
        `Beboer: ${resident.name}`;


    renderTags();
    showAllTags();
}

function renderTags() {
    const tagList = document.getElementById("tagList");
    tagList.innerHTML = "";

    selectedAllergies.forEach(a => {
        const tag = document.createElement("div");
        tag.className = "tag";

        tag.innerHTML = `
            ${a.name}
            <button onclick="removeTag(${a.id})">X</button>
        `;

        tagList.appendChild(tag);
    });
}

async function removeTag(id) {
    await fetch(
        `${BASE_URL}/api/residents/update/${selectedResident.id}/removeAllergy/${id}`,
        {
            method: "DELETE",
            credentials: "include"   // ⭐ VIGTIG
        }
    );

    selectedAllergies = selectedAllergies.filter(a => a.id !== id);

    renderTags();
    loadResidents();
}

function onAllergySearch(e) {
    const dropdown = document.getElementById("dropdown");
    const search = e.target.value.toLowerCase();

    const matches = allergies.filter(a =>
        a.name.toLowerCase().includes(search) &&
        !selectedAllergies.some(sel => sel.id === a.id)
    );

    dropdown.innerHTML = "";
    dropdown.style.display = matches.length ? "block" : "none";

    matches.forEach(a => {
        const item = document.createElement("div");
        item.className = "dropdown-item";
        item.textContent = a.name;

        item.addEventListener("click", async () => {
            await addAllergyToResident(a);

            selectedAllergies.push(a);
            renderTags();

            dropdown.style.display = "none";
            e.target.value = "";
        });

        dropdown.appendChild(item);
    });
}

function closeDropdown(e) {
    const searchInput = document.getElementById("allergySearch");
    const dropdown = document.getElementById("dropdown");

    if (!searchInput.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.style.display = "none";
    }
}

async function addAllergyToResident(allergy) {
    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/addAllergy`, {
        method: "PUT",
        credentials: "include",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({name: allergy.name})
    });

    await loadResidents();
    await loadAllergies()
}

async function saveNewAllergy() {
    if (!selectedResident) return;

    const name = document.getElementById("allergySearch").value.trim();
    if (!name) return alert("Skriv en allergi først");

    await addAllergyToResident({name});

    selectedAllergies.push({name})
    renderTags();

}

function showAllTags() {
    const dropdown = document.getElementById("dropdown");
    dropdown.innerHTML = "";

    const available = allergies.filter(a =>
        !selectedAllergies.some(sel => sel.id === a.id)
    );

    if (available.length === 0) {
        dropdown.style.display = "none";
        return;
    }

    dropdown.style.display = "block"

    available.forEach(a => {
        const item = document.createElement("div");
        item.className = "dropdown-item";
        item.textContent = a.name;

        item.addEventListener("click", async () => {
            await addAllergyToResident(a);
            selectedAllergies.push(a);
            renderTags();
            dropdown.style.display = "none";
        });

        dropdown.appendChild(item);
    });
}

async function chooseAllergy(allergy) {
    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/addAllergy`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({name: allergy.name})
    });

    selectedAllergies.push(allergy);
    loadResidents();
}

function renderAllAllergies() {
    const panel = document.getElementById("a");
    if (!panel) return;

    panel.innerHTML = "";

    allergies.forEach(a => {
        const item = document.createElement("div");
        item.className = "allergy-item";
        item.textContent = a.name;

        item.addEventListener("click", () => chooseAllergy(a));

        panel.appendChild(item);
    })

}

