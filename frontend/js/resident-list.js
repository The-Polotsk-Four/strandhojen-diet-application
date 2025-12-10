document.addEventListener("DOMContentLoaded", initApp);

const RESIDENT_URL = "http://localhost:8080/api/residents";

async function initApp() {
    console.log("time for resident list");

    const residents = await fetchResident();
    console.log(residents);

    renderResidents(residents);

    document.querySelector("#resident-form").addEventListener("submit", fetchSpecificResident);
}

async function fetchSpecificResident(e) {
    e.preventDefault();

    const form = e.target;
    const residentName = form.querySelector("#name").value;

    if (residentName === "") {
        const residents = await fetchResident();
        renderResidents(residents);
        return;
    }
    const capitalized = residentName.charAt(0).toUpperCase() + residentName.slice(1);

    const resp = await fetch(
        `http://localhost:8080/api/residents/getresident?residentName=${capitalized}`,
        {credentials: "include"}
    );

    if (!resp.ok) {
        console.error("Cant find user with name: " + capitalized);
        return;
    }

    const data = await resp.json();
    console.log(data);

    form.reset();

    renderResidents(data);
}


async function fetchResident() {
    const resp = await fetch(RESIDENT_URL, {
        credentials: "include"
    });

    if (!resp.ok) {
        console.error("Cant retrieve list of residents!!");
        return;
    }

    return await resp.json();
}

function renderResidents(residents) {
    const tableBody = document.querySelector("#residentTable");
    tableBody.textContent = "";

    residents.forEach(resident => renderResident(resident));
}

function renderResident(resident) {
    const row = document.createElement("tr");

    row.appendChild(renderCell(resident.name));
    row.appendChild(renderCell(resident.age));
    row.appendChild(renderCell(resident.floor));
    row.appendChild(renderCell(resident.roomNumber));
    row.appendChild(renderCell(resident.status));

    const editBtn = document.createElement("button");
    editBtn.textContent = "Beboer profil";
    editBtn.addEventListener("click", () => {
        window.location.href = `resident-page.html?id=${resident.id}`;
    });
    row.appendChild(editBtn);

    document.querySelector("#residentTable").appendChild(row);
}

function renderCell(content) {
    const cell = document.createElement("td");
    cell.textContent = content;
    return cell;
}
