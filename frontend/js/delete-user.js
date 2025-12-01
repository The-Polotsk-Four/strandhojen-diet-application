document.addEventListener("DOMContentLoaded", initApp);

const BASE_USER_URL = "http://localhost:8080/api/users";
let users = [];


async function initApp() {
    console.log("App started");

    users = await fetchUsers();
    renderUsers(users);

    document.getElementById("searchBtn").addEventListener("click", searchUsers);

    console.log(users)
}

async function fetchUsers() {
    const resp = await fetch(BASE_USER_URL);

    if (!resp.ok) {
        console.error("Fetch error:", resp.status);
        return [];
    }
    return await resp.json();
}

async function searchUsers() {
    const text = document.getElementById("searchInput").value.trim();

    if (text === "") {
        renderUsers(users);
        return;
    }

    try {
        const resp = await fetch(`${BASE_USER_URL}?login=${text}`);

        if (!resp.ok) throw new Error();

        const results = await resp.json();
        renderUsers(results);
    } catch {
        document.getElementById("userTableBody").innerHTML =
            "<tr><td colspan='5'>Ingen brugere fundet.</td></tr>";
    }
}

function renderUsers(users) {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = "";

    users.forEach(user => renderUser(user));
}

function renderUser(user) {
    const row = document.createElement("tr");

    row.appendChild(renderUserElement(user.id));
    row.appendChild(renderUserElement(user.login));
    row.appendChild(renderUserElement(user.name));
    row.appendChild(renderUserElement(user.userrole));

    const deleteCell = document.createElement("td");
    const btn = document.createElement("button");
    btn.textContent = "Slet";
    btn.addEventListener("click", () => deleteUser(user.id));
    deleteCell.appendChild(btn);
    row.appendChild(deleteCell);

    document.getElementById("userTableBody").append(row);
}

function renderUserElement(value) {
    const cell = document.createElement("td");
    cell.textContent = value;
    return cell;
}

async function deleteUser(id) {
    if (!confirm("Er du sikker på at du vil slette denne bruger?"))
        return;

    const resp = await fetch(`${BASE_USER_URL}/${id}`, {
        method: "DELETE"
    });

    if (resp.status === 204) {
        users = users.filter(u => u.id !== id);
        renderUsers(users);
    } else {
        alert("Fejl: kunne ikke slette bruger");
    }
}
