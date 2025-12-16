export async function loadNurses() {
    const select = document.getElementById("nurse");
    const response = await fetch("http://localhost:8080/api/users/sygeplejersker");
    credentials: 'include'
    if (!response.ok) return;
    const nurse = await response.json();

    nurse.forEach(n => {
        const option = document.createElement("option");
        option.value = n.login;
        option.textContent = n.name;
        select.appendChild(option);
    });
}

document.addEventListener("DOMContentLoaded", loadNurses);
