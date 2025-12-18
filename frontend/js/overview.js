document.addEventListener("DOMContentLoaded", initApp);

const floorSelector = document.querySelector("#floor-selector");
const allergySelector = document.querySelector("#allergy-selector");
let activeFloor = -1;
let allergies;
let selectedAllergies;
let residents;

async function initApp() {
    
    floorSelector.addEventListener('input', getFloorValue);
    allergySelector.addEventListener('input', getAllergyValue);
    
    await fetchResidents();
    console.log(residents);
    
    await fetchAllergies();
    console.log(allergies);

    // renderResidents();
    renderSelectableAllergies();
    renderAllergies();
    document.querySelector("#floor-number").innerText = activeFloor;
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
    allergies = await response.json();
    selectedAllergies = allergies;
}

function renderSelectableAllergies() {
    allergies.forEach(allergy => {
        renderSelectableAllergy(allergy)
    });
}

function renderSelectableAllergy(allergy) {
    const option = document.createElement("option");
    option.value = allergy.id;
    option.innerHTML = allergy.name;
    allergySelector.append(option);
}

function getAllergyValue() {
    selectedAllergies = [];
    console.log(allergySelector.selectedOptions.length);    
    if (allergySelector.selectedOptions.length > 0) {
        for (let option in allergySelector.selectedOptions) {
            const value = Number(allergySelector.selectedOptions[option].value);
            if (value) {
                allergies.forEach(allergy => {
                    if (allergy.id === value) {
                        selectedAllergies.push(allergy);
                    }
                });
            }
        }
    } else {
        selectedAllergies = allergies;
    }
    
    console.log(selectedAllergies);
    document.querySelector("#solid-table").innerHTML = '';
    document.querySelector("#soft-table").innerHTML = '';
    document.querySelector("#tube-table").innerHTML = '';
    renderAllergies();
}

function getAllergies(resident) {
    // console.log(allergies);
    // console.log(resident.allergies);
    let listOfIds = [];
    resident.allergies.forEach(allergy => {
        listOfIds.push(allergy.id);
    });
    // console.log(listOfIds);
    const hasAllergies = allergies.some(allergy => listOfIds.includes(allergy.id));
    // console.log(resident.name);
    // console.log('has allergies');
    // console.log(hasAllergies);
}

function renderAllergies() {
    let solidFoodAllergies = [];
    let softFoodAllergies = [];
    let tubeCounter = 0;
    let residentCounter = 0;
    residents.forEach(resident => {
        if (activeFloor === -1 || resident.floor === activeFloor) {
            const allergyNameArray = separateAllergyNamesIntoArray(resident);
            // console.log(resident.name);
            // console.log(allergyNameArray);

            // arrayOfSortedAllergies = arrayOfSortedAllergies.concat(allergyNameArray);

            if (allergyNameArray.length > 0) {
                // arrayOfSortedAllergies.push(allergyNameArray);
                // const row = document.createElement("tr");
                // allergyNameArray.forEach(allergy => row.appendChild(renderAllergyCell(allergy)));
                // appendRowToRightFloor(resident, row);
                switch (resident.FoodConsistency) {
                    case "SOLID":
                        solidFoodAllergies.push(allergyNameArray);
                        break;
                    case "SOFTFOOD":
                        softFoodAllergies.push(allergyNameArray);
                        break;
                }
            }
            
            if (resident.FoodConsistency === "TUBEFEEDING") {
                tubeCounter++;
            }
            residentCounter++;
        }
    });

    console.log('allergies');
    console.log(solidFoodAllergies);
    console.log(softFoodAllergies);
    
    countAllergies(solidFoodAllergies)
        .sort((a, b) => a.allergy.localeCompare(b.allergy))
        .forEach(allergy => {
            const row = document.createElement("tr");
            row.appendChild(renderCell(allergy.allergy));
            row.appendChild(renderCell(allergy.count));

            document.querySelector("#solid-table").append(row);
        });
    countAllergies(softFoodAllergies)
        .sort((a, b) => a.allergy.localeCompare(b.allergy))
        .forEach(allergy => {
            const row = document.createElement("tr");
            row.appendChild(renderCell(allergy.allergy));
            row.appendChild(renderCell(allergy.count));

            document.querySelector("#soft-table").append(row);
        });

    const row = document.createElement("tr");
    row.appendChild(renderCell(tubeCounter));
    document.querySelector("#tube-table").append(row);
    
    document.querySelector("#resident-counter").innerText = residentCounter;
}

function renderAllergyCell(allergy) {
    const cell = document.createElement("td");
    cell.textContent = allergy;
    return cell;
}

function separateAllergyNamesIntoArray(resident) {
    let allergyNameArray = [];
    resident.allergies.forEach(allergy => {
        selectedAllergies.forEach(selectedAllergy =>
            (allergy.id === selectedAllergy.id) ? allergyNameArray.push(allergy.name) : 'nothing');
    });
    // console.log(allergyNameArray);
    return allergyNameArray;
}

function countAllergies(allergiesArray) {
    const count = allergiesArray.reduce((accumulator, current) => {
        accumulator[current] = (accumulator[current] || 0) + 1;
        return accumulator;
    }, {});

    // console.log('count');
    // console.log(count);
    
    const toArray = Object.entries(count)
        // .filter(([key, value]) => value > 1)
        .map(([key, value]) => ({ allergy: key, count: value}));

    console.log('duplicate');
    console.log(toArray);
    
    return toArray;
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
    row.appendChild(renderCell(resident.name));
    row.appendChild(renderCell(resident.FoodConsistency));
    row.appendChild(renderCell(resident.floor));
    row.appendChild(renderCell(resident.allergies));
    
    getAllergies(resident);
    
    // appendRowToRightFloor(resident, row);
}

function renderCell(value) {
    const cell = document.createElement("td");
    cell.textContent = value;
    return cell;
}

function appendRowToRightFloor(resident, row) {
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

function getFloorValue(event) {
    activeFloor = Number(floorSelector.value);
    console.log(activeFloor);
    document.querySelector("#floor-selector").selectedIndex = 0;
    document.querySelector("#solid-table").innerHTML = '';
    document.querySelector("#soft-table").innerHTML = '';
    document.querySelector("#tube-table").innerHTML = '';
    document.querySelector("#floor-number").innerText = activeFloor;
    renderAllergies();
    renderResidents();
    
    console.log(event.target);
}
