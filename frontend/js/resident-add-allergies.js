const BASE_URL = "http://localhost:8080";

let residents = [];
let allAllergies = [];
let selectedResident = null;
let selectedAllergies = [];

window.addEventListener("DOMContentLoaded", () => {
    loadResidents();
    loadAllergies();
});


async function loadResidents() {
    try {
        const res = await fetch(`${BASE_URL}/api/residents`,{
            credentials: "include"
            }
            );
        if (!res.ok) throw new Error("Failed to fetch residents");

        residents = await res.json();
        renderResidents(residents);

    } catch (e) {
        console.error("Residents fetch error:", e);
    }
}


function renderResidents(list) {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    list.forEach(r => {

        const allergyList = r.allergies && r.allergies.length
            ? `<ul>${r.allergies.map(a => `<li>${a.name}</li>`).join("")}</ul>`
            : "ingen"

                
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${r.id}</td>
            <td>${r.name}</td>
            <td>${r.age ?? ""}</td>
            <td>${r.weight ?? ""}</td>
            <td>${r.height ?? ""}</td>
            <td>${r.bmi ?? ""}</td>
            <td>${r.floor}</td>
            <td>${r.roomNumber}</td>
            <td>${r.status ? "Aktiv" : "Inaktiv"}</td>
            <td>${allergyList}</td>
        `;

        tr.style.cursor = "pointer";
        tr.onclick = () => openAllergyEditor(r);

        tbody.appendChild(tr);
    });
}


function openAllergyEditor(resident) {
    selectedResident = resident;
    selectedAllergies = [...(resident.allergies || [])];

    document.getElementById("allergySection").style.display = "block";
    document.getElementById("residentNameHeader").textContent =
        "Beboer: " + resident.name;

    renderTags();
}


async function loadAllergies() {
    try {
        const res = await fetch(`${BASE_URL}/api/allergies`);
        if (!res.ok) throw new Error("Failed to fetch allergies");

        allAllergies = await res.json();
        console.log(allAllergies)

    } catch (e) {
        console.error("Allergies fetch error:", e);
    }
}

function renderTags() {
    const tagList = document.getElementById("tagList");
    tagList.innerHTML = "";

    selectedAllergies.forEach(a => {
        const tag = document.createElement("div");
        tag.className = "tag";
        tag.innerHTML = `
            ${a.name}
            <button id= "deletebtn" onclick="removeTag(${a.id})">X</button>
        `;
        tagList.appendChild(tag);
    });
}

async function removeTag(id) {
    if (!selectedResident) return;

    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/removeAllergy/${id}`, {
        method: "DELETE"
    });

    selectedAllergies = selectedAllergies.filter(a => a.id !== id);

    renderTags();
    loadResidents();
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

document.addEventListener("click", (e) => {
    const searchInput = document.getElementById("allergySearch");
    const dropdown = document.getElementById("dropdown");

    if (!searchInput.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.style.display = "none";
    }
})


document.getElementById("saveBtn").onclick = async () => {
    if (!selectedResident) return;

    const allergy = {
        name: document.querySelector("#allergySearch").value
    }

    await fetch(`${BASE_URL}/api/residents/update/${selectedResident.id}/addAllergy`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(allergy)
    });

    alert("Allergier gemt!");
    loadResidents();
    loadAllergies();
};
