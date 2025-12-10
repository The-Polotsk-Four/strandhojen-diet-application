document.addEventListener("DOMContentLoaded", initApp);

// gets the resident id from url. Used to fetch information for the correct resident
const residentId = new URLSearchParams(window.location.search).get("id");
console.log(residentId);

const getResidentUrl = `http://localhost:8080/api/residents/${residentId}`;


async function initApp() {
    console.log("js loaded");
    residentNotSelected();

    await loadResident()


}


async function loadResident() {

    const resp = await fetch(getResidentUrl);

    if (!resp.ok) {
        console.error("Cant find resident");
    }

    const data = await resp.json();

    renderResident(data);
}

function renderResident(resident) {
    console.log(resident);


    document.getElementById("name").innerHTML =
        `<span class="label">Navn:</span> ${resident.name}`;

    document.getElementById("age").innerHTML =
        `<span class="label">Alder:</span> ${resident.age}`;

    document.getElementById("bmi").innerHTML =
        `<span class="label">BMI:</span> ${resident.bmi.toFixed(2)}`;

    document.getElementById("height").innerHTML =
        `<span class="label">Højde:</span> ${resident.height} cm`;

    document.getElementById("weight").innerHTML =
        `<span class="label">Vægt:</span> ${resident.weight} kg`;

    let consistency;
    if (resident.FoodConsistency === "SOLID") {
        consistency = "Fast føde";
    }
    if (resident.FoodConsistency === "TUBEFEEDING") {
        consistency = "Sondemad"
    }
    if (resident.FoodConsistency === "SOFTFOOD") {
        consistency = "Blød kost"
    }

    document.getElementById("food").innerHTML =
        `<span class="label">Mad konsistens:</span> ${consistency}`;


    document.getElementById("room").innerHTML =
        `<span class="label">Værelses nummer:</span> ${resident.roomNumber}`;

    document.getElementById("floor").innerHTML =
        `<span class="label">Etage:</span> ${resident.floor}`;

    document.getElementById("status").innerHTML =
        `<span class="label">Status:</span> ${resident.status ? "Aktiv" : "Inaktiv"}`;

    document.getElementById("allergies").innerHTML =
        `<span class="label">Allergier:</span> ${resident.allergies.length ? resident.allergies.join(", ") : "Ingen allergier"}`;

    document.getElementById("diet").innerHTML =
        `<span class="label">Diæt:</span> ${resident.diet.length ? resident.diet.join(", ") : "Intet at tage højde for"}`;

    document.getElementById("preference").innerHTML =
        `<span class="label">Præferencer:</span> ${resident.preference.length ? resident.preference.join(", ") : "Ingen preferencer"}`;
}


function residentNotSelected() {
    if (residentId < 1) {
        window.location.href = "resident-list.html";
    }
}