import {getCurrentUser} from './fetchUser.js';

document.addEventListener("DOMContentLoaded", initApp);
let editMode = false;


// gets the resident id from url. Used to fetch information for the correct resident
const residentId = new URLSearchParams(window.location.search).get("id");
console.log(residentId);

const getResidentUrl = `http://localhost:8080/api/residents/${residentId}`;


async function initApp() {
    console.log("js loaded");
    residentNotSelected();
    const user = await getCurrentUser();
    console.log(user);

    await loadResident(user)


}


async function loadResident(user) {

    const resp = await fetch(getResidentUrl, {
        credentials: "include"
    });

    if (!resp.ok) {
        console.error("Cant find resident");
    }

    const data = await resp.json();

    renderResident(data, user);
}

function renderResident(resident, user) {
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
    if (resident.foodConsistency === "SOLID") {
        consistency = "Fast føde";
    }
    if (resident.foodConsistency === "TUBEFEEDING") {
        consistency = "Sondemad"
    }
    if (resident.foodConsistency === "SOFTFOOD") {
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

    if (user && (user.userrole === "SYGEPLEJERSKE" || user.userrole === "ADMIN")) {
        document.getElementById("allergies").innerHTML =
            `<span class="label">Allergier:</span>
     ${resident.allergies.length ? resident.allergies.map(a => a.name).join(", ") : "Ingen allergier"}
     <br>
     <a href="resident-add-allergies.html" class="btn">Tilføj flere allergi</a>`;
    } else {
        document.getElementById("allergies").innerHTML =
            `<span class="label">Allergier:</span>
     ${resident.allergies.length ? resident.allergies.map(a => a.name).join(", ") : "Ingen allergier"}`
    }

    if (user && (user.userrole === "SYGEPLEJERSKE" || user.userrole === "ADMIN")) {
        document.getElementById("diet").innerHTML =
            `<span class="label">Diæt:</span>${resident.diet.length ? resident.diet.map(d => d.name).join(", ") : "Intet at tage højde for"}
            <br>
            <a href="resident-diet.html" class="btn">Tilføj diæt</a>`;
    } else {
        document.getElementById("diet").innerHTML =
            `<span class="label">Diæt:</span> ${resident.diet.length ? resident.diet.map(diet => diet.name).join(", ") : "Intet at tage højde for"}`;
    }

    // Commented preference for now because we're not gonna use it going forward
    // document.getElementById("preference").innerHTML =
    //     `<span class="label">Præferencer:</span> ${resident.preference.length ? resident.preference.map(preference => preference.name).join(", ") : "Ingen preferencer"}`;

    if (resident.comment !== null) {
        document.getElementById("comment").innerHTML =
            `<span class="label">Kommentar:</span> ${resident.comment}`;
    } else {
        document.getElementById("comment").innerHTML =
            `<span class="label">Kommentar: </span> Ingen kommentarer`;
    }

    if (user && (user.userrole === "SYGEPLEJERSKE" || user.userrole === "ADMIN")) {
        document.getElementById("resident-container").insertAdjacentHTML(
            "beforeend",
            `<button id="editBtn" class="btn">Rediger</button>`
        );

        document.getElementById("editBtn")
            .addEventListener("click", () => enableEditMode(resident));
    }
}

function enableEditMode(resident) {
    editMode = true;

    document.getElementById("editBtn").remove();

    document.getElementById("weight").innerHTML = `
        <span class="label">Vægt:</span>
        <input type="number" id="editWeight" value="${resident.weight}">
    `;

    document.getElementById("room").innerHTML = `
        <span class="label">Værelsesnummer:</span>
        <input type="number" id="editRoom" value="${resident.roomNumber}">
    `;

    document.getElementById("status").innerHTML = `
        <span class="label">Status:</span>
        <select id="editStatus">
            <option value="true" ${resident.status ? "selected" : ""}>Aktiv</option>
            <option value="false" ${!resident.status ? "selected" : ""}>Inaktiv</option>
        </select>
`;

    document.getElementById("food").innerHTML = `
    <span class="label">Mad Konsistens:</span>
    <select id="editFoodConsistency">
        <option value="SOLID" ${resident.foodConsistency === "SOLID" ? "selected" : ""}>Fast føde</option>
        <option value="TUBEFEEDING" ${resident.foodConsistency === "TUBEFEEDING" ? "selected" : ""}>Sondemad</option>
        <option value="SOFTFOOD" ${resident.foodConsistency === "SOFTFOOD" ? "selected" : ""}>Blød kost</option>
    </select>
`;

    document.getElementById("floor").innerHTML = `
    <span class="label">Etage:</span>
    <select id="editFloor">
        <option value="0" ${resident.floor === 0 ? "selected" : ""}>0</option>
        <option value="1" ${resident.floor === 1 ? "selected" : ""}>1</option>
        <option value="2" ${resident.floor === 2 ? "selected" : ""}>2</option>
        <option value="3" ${resident.floor === 3 ? "selected" : ""}>3</option>
    </select>
`;

    document.getElementById("comment").innerHTML = `
        <span class="label">Kommentar:</span><br>
        <textarea id="editComment">${resident.comment ?? ""}</textarea>
    `;
    if (document.getElementById("editComment").value === "" ){
        document.getElementById("editComment").innerHTML = "Ingen kommentar";
    }

    document.getElementById("comment").insertAdjacentHTML(
        "afterend",
        `<button id="saveBtn" class="btn">Gem</button>
         <button id="cancelBtn" class="btn">Annuller</button>`
    );

    document.getElementById("saveBtn")
        .addEventListener("click", () => saveResident(resident));

    document.getElementById("cancelBtn")
        .addEventListener("click", () => refreshPage());
}

async function saveResident(resident) {

    // gets the new weight
    const weight = document.getElementById("editWeight").value;
    // calculates bmi with weight/height^2 divided by 100 to get meter instead of cm
    const bmi = weight/((resident.height/100) ** 2);

    const updatedResident = {
        ...resident,
        weight: Number(document.getElementById("editWeight").value),
        roomNumber: Number(document.getElementById("editRoom").value),
        floor: Number(document.getElementById("editFloor").value),
        comment: document.getElementById("editComment").value,
        status: document.getElementById("editStatus").value,
        foodConsistency: document.getElementById("editFoodConsistency").value,
        bmi: bmi
    };

    const resp = await fetch(`http://localhost:8080/api/residents/update/${residentId}`, {
        method: "PUT",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedResident)
    });

    if (!resp.ok) {
        alert("Kunne ikke gemme ændringer");
        return;
    }

    editMode = false;
    const updated = await resp.json();
    renderResident(updated, await getCurrentUser());
    refreshPage();
}

// refreshes the window
function refreshPage(){
    editMode=false;
    window.location.reload();
}

// If theres not a resident selected in the url it sends you back to the resident list html
function residentNotSelected() {
    if (residentId < 1) {
        window.location.href = "resident-list.html";
    }
}