document.addEventListener("DOMContentLoaded", initApp);

const floorSelector = document.querySelector("#floor-selector");
const allergySelector = document.querySelector("#allergy-selector");
let activeFloor = -1;
let allergies
let residents;

async function initApp() {
    
    floorSelector.addEventListener('input', getFloorValue);
    allergySelector.addEventListener('input', getAllergyValue);
    
    await fetchResidents();
    console.log(residents);
    
    await fetchAllergies();
    console.log(allergies);

    renderResidents();
    renderAllergies();
    console.log("app initialized");
}

async function fetchAllergies() {
    console.log("fetching allergies..");
    const response = await fetch("http://localhost:8080/api/allergies", {
        credentials: "include",
    });
    if (!response.ok) {
        console.log("Error: " + response.status);
        return;
    }
    allergies = await response.json()
}

function renderAllergies() {
    allergies.forEach(allergy => {
        renderAllergy(allergy)
    });
}

function renderAllergy(allergy) {
    const option = document.createElement("option");
    option.value = allergy.id;
    option.innerHTML = allergy.name;
    allergySelector.append(option);
}

function getAllergyValue() {
    console.log(allergySelector.selectedOptions);
    for (let option in allergySelector.selectedOptions) { 
        console.log(allergySelector.selectedOptions[option].value);
    }
}

async function fetchResidents() {
    console.log("fetching residents..");
    const response = await fetch("http://localhost:8080/api/residents", {
        credentials: "include",
    });
    if (!response.ok) {
        console.log("Error: " + response.status);
        return;
    }
    residents = await response.json()
}

function renderResidents() {
    // console.log("residents in resident renderer:");
    // console.log(residents);
    residents.forEach(resident => {
        renderResident(resident)
    });
}

function renderResident(resident) {
    const row = document.createElement("tr");
    row.appendChild(renderResidentCell(resident.name));
    row.appendChild(renderResidentCell(resident.FoodConsistency));
    row.appendChild(renderResidentCell(resident.floor));
    
    if (activeFloor === -1 || resident.floor === activeFloor) {
        switch (resident.FoodConsistency) {
            case "SOLID":
                document.querySelector("#solid-table").append(row);
                break;
            case "SOFTFOOD":
                document.querySelector("#soft-table").append(row);
                break;
            case "TUBEFEEDING":
                document.querySelector("#tube-table").append(row);
        }
    }
}

function renderResidentCell(value) {
    const cell = document.createElement("td");
    cell.textContent = value;
    return cell;
}

function getFloorValue(event) {
    activeFloor = Number(floorSelector.value);
    console.log(activeFloor);
    document.querySelector("#floor-selector").selectedIndex = 0;
    document.querySelector("#solid-table").innerHTML = '';
    document.querySelector("#soft-table").innerHTML = '';
    document.querySelector("#tube-table").innerHTML = '';
    renderResidents();
    

    console.log(event.target);
}
