window.addEventListener("DOMContentLoaded", initApp);


const API = "http://localhost:8080";

function initApp() {
    loadResidents();
    loadAllergies();
}
let residents = [];
let allAllergies = [];
let selectedResident = null;
let selectedAllergies = [];




async function loadResidents() {
    const res = await fetch(`${API}/api/residents`);
    residents = await res.json();
    renderResidents(residents);
}

function renderResidents(list) {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    list.forEach(r => {

        const allergyNames = r.allergies && r.allergies.length > 0
            ? r.allergies.map(a => a.name).join(", ")
            : "Ingen";

        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${r.id}</td>
            <td>${r.name}</td>
            <td>${r.age}</td>
            <td>${r.weight}</td>
            <td>${r.height}</td>
            <td>${r.bmi}</td>
            <td>${r.floor}</td>
            <td>${r.roomNumber}</td>
            <td>${r.status ? "Aktiv" : "Inaktiv"}</td>
            <td>${allergyNames}</td>
        `;

        tr.style.cursor = "pointer";
        tr.onclick = () => openAllergyEditor(r);

        tbody.appendChild(tr);
    });
}

function openAllergyEditor(resident) {
    selectedResident = resident;
    selectedAllergies = [...resident.allergies];

    document.getElementById("allergySection").style.display = "block";
    document.getElementById("residentNameHeader").textContent =
        "Beboer: " + resident.name;

    renderTags();
}

async function loadAllergies() {
    const res = await fetch(`${API}/api/allergies`);
    allAllergies = await res.json();
}

function renderTags() {
    const tagList = document.getElementById("tagList");
    tagList.innerHTML = "";

    selectedAllergies.forEach(a => {
        const tag = document.createElement("div");
        tag.className = "tag";
        tag.innerHTML = `
            ${a.name}
            <button onclick="removeTag(${a.id})">❌</button>
        `;
        tagList.appendChild(tag);
    });
}

function removeTag(id) {
    selectedAllergies = selectedAllergies.filter(a => a.id !== id);
    renderTags();
}

document.getElementById("allergySearch").addEventListener("input", (e) => {
    const search = e.target.value.toLowerCase();
    const dropdown = document.getElementById("dropdown");

    const filtered = allAllergies.filter(a =>
        a.name.toLowerCase().includes(search) &&
        !selectedAllergies.some(s => s.id === a.id)
    );

    dropdown.innerHTML = "";
    dropdown.style.display = filtered.length ? "block" : "none";

    filtered.forEach(a => {
        const item = document.createElement("div");
        item.className = "dropdown-item";
        item.textContent = a.name;
        item.onclick = () => {
            selectedAllergies.push(a);
            renderTags();
            dropdown.style.display = "none";
            document.getElementById("allergySearch").value = "";
        };
        dropdown.appendChild(item);
    });
});

document.getElementById("saveBtn").onclick = async () => {
    if (!selectedResident) return;

    const updatedResident = {
        ...selectedResident,
        allergies: selectedAllergies
    };

    await fetch(`${API}/api/residents/update/${selectedResident.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedResident)
    });

    alert("Allergier gemt!");
    loadResidents();
};
