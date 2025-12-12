document.addEventListener("DOMContentLoaded", initApp);

const floorSelector = document.querySelector("#floor-selector");
let residents;
let activeFloor = -1;

async function initApp() {
    
    floorSelector.addEventListener('input', getFloorValue);
    
    await fetchResidents();
    console.log(residents);

    renderResidents();
    console.log("app initialized");
}

async function fetchResidents() {
    console.log("fetching..");
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
    console.log("residents in resident renderer:");
    console.log(residents);
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
    document.querySelector("#solid-table").innerHTML = '';

    document.querySelector("#soft-table").innerHTML = '';
    document.querySelector("#tube-table").innerHTML = '';
    renderResidents();

    console.log(event.target);
}
