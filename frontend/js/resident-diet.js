window.addEventListener("DOMContentLoaded", initApp);

const BASE_URL = "http://localhost:8080";

let residents = [];
let diets = [];
let selectedResident = null;
let selectedDiets = [];

// --------------------------------------------------
// INIT
// --------------------------------------------------
async function initApp() {
    await loadResidents();
    await loadDiets();
    setupEventListeners();
    renderAllDiets();
}

function setupEventListeners() {
    document.getElementById("dietSearch")
        .addEventListener("input", onDietSearch);

    document.getElementById("saveBtn")
        .addEventListener("click", saveNewDiet);

    document.addEventListener("click", closeDropdown);
}

// --------------------------------------------------
// LOADERS
// --------------------------------------------------
async function loadResidents() {
    try {
        const res = await fetch(`${BASE_URL}/api/residents`, { credentials: "include" });
        residents = await res.json();
        renderResidents(residents);
    } catch {
        console.error("Kunne ikke hente beboere");
    }
}

async function loadDiets() {
    try {
        const res = await fetch(`${BASE_URL}/api/diets`);
        diets = await res.json();
    } catch {
        console.error("Kunne ikke hente diæter");
    }
}

// --------------------------------------------------
// RESIDENT TABLE
// --------------------------------------------------
function renderResidents(list) {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    list.forEach(resident => {
        const tr = document.createElement("tr");
        tr.style.cursor = "pointer";

        const dietHtml = resident.diet?.length
            ? resident.diet.map(a => `<li>${a.name}</li>`).join("")
            : "Ingen";


        tr.innerHTML = `
            <td>${resident.name}</td>
            <td>${resident.age ?? ""}</td>
            <td>${resident.floor}</td>
            <td>${resident.roomNumber}</td>
            <td>${resident.status ? "Aktiv" : "Inaktiv"}</td>
            <td><ul>${dietHtml}</ul></td>
        `;

        tr.addEventListener("click", () => openDietEditor(resident));
        tbody.appendChild(tr);
    });
}

// --------------------------------------------------
// EDITOR OPEN
// --------------------------------------------------
function openDietEditor(resident) {
    selectedResident = resident;
    selectedDiets = Array.isArray(resident.diets) ? [...resident.diets] : [];

    document.getElementById("dietSection").style.display = "block";
    document.getElementById("residentNameHeader").textContent =
        `Beboer: ${resident.name}`;

    renderTags();
    showAllTags();
}

// --------------------------------------------------
// TAG RENDERING
// --------------------------------------------------
function renderTags() {
    const tagList = document.getElementById("tagList");
    tagList.innerHTML = "";

    selectedDiets.forEach(d => {
        const tag = document.createElement("div");
        tag.className = "tag";

        tag.innerHTML = `
            ${d.name}
            <button onclick="removeTag(${d.id})">X</button>
        `;

        tagList.appendChild(tag);
    });
}

// --------------------------------------------------
// REMOVE DIET
// --------------------------------------------------
async function removeTag(id) {
    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/removeDiet/${id}`, {
        method: "DELETE"
    });

    selectedDiets = selectedDiets.filter(d => d.id !== id);

    renderTags();
    loadResidents();
}

// --------------------------------------------------
// SEARCH + DROPDOWN
// --------------------------------------------------
function onDietSearch(e) {
    const dropdown = document.getElementById("dropdown");
    const search = e.target.value.toLowerCase();

    const matches = diets.filter(d =>
        d.name.toLowerCase().includes(search) &&
        !selectedDiets.some(sel => sel.id === d.id)
    );

    dropdown.innerHTML = "";
    dropdown.style.display = matches.length ? "block" : "none";

    matches.forEach(d => {
        const item = document.createElement("div");
        item.className = "dropdown-item";
        item.textContent = d.name;

        item.addEventListener("click", async () => {
            await addDietToResident(d);

            selectedDiets.push(d);
            renderTags();
            dropdown.style.display = "none";
            e.target.value = "";
        });

        dropdown.appendChild(item);
    });
}

function closeDropdown(e) {
    const searchInput = document.getElementById("dietSearch");
    const dropdown = document.getElementById("dropdown");

    if (!searchInput.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.style.display = "none";
    }
}

// --------------------------------------------------
// ADD DIET
// --------------------------------------------------
async function addDietToResident(diet) {
    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/addDiet`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ name: diet.name })
    });

    await loadResidents();
    await loadDiets();
}

// Save new diet by typing text instead of selecting
async function saveNewDiet() {
    if (!selectedResident) return;

    const name = document.getElementById("dietSearch").value.trim();
    if (!name) return alert("Skriv en diæt først");

    await addDietToResident({ name });

    selectedDiets.push({ name });
    renderTags();
}

// --------------------------------------------------
// SHOW ALL DIETS PANEL
// --------------------------------------------------
function showAllTags() {
    const dropdown = document.getElementById("dropdown");
    dropdown.innerHTML = "";

    const available = diets.filter(d =>
        !selectedDiets.some(sel => sel.id === d.id)
    );

    if (available.length === 0) {
        dropdown.style.display = "none";
        return;
    }

    dropdown.style.display = "block";

    available.forEach(d => {
        const item = document.createElement("div");
        item.className = "dropdown-item";
        item.textContent = d.name;

        item.addEventListener("click", async () => {
            await addDietToResident(d);
            selectedDiets.push(d);
            renderTags();
            dropdown.style.display = "none";
        });

        dropdown.appendChild(item);
    });
}

// --------------------------------------------------
// “All diets” grid panel
// --------------------------------------------------
async function chooseDiet(diet) {
    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/addDiet`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ name: diet.name })
    });

    selectedDiets.push(diet);
    loadResidents();
}

function renderAllDiets() {
    const panel = document.getElementById("allAllergiesPanel");
    if (!panel) return;
    panel.innerHTML = "";

    diets.forEach(d => {
        const item = document.createElement("div");
        item.className = "allergy-item";
        item.textContent = d.name;
        item.addEventListener("click", () => chooseDiet(d));

        panel.appendChild(item);
    });
}
