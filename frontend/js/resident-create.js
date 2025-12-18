import { loadNurses } from "./loadNurses.js";

document.addEventListener("DOMContentLoaded", async () => {
    await loadNurses();

    document
        .getElementById("createForm")
        .addEventListener("submit", opretBeboer);
});

async function opretBeboer(event) {
    event.preventDefault();

    // ✅ DEFINERES HER
    const nurseEmail = document.getElementById("nurse").value;

    if (!nurseEmail) {
        alert("Vælg venligst en sygeplejerske.");
        return;
    }

    const residentData = {
        id: null,
        name: document.getElementById("name").value,
        foodConsistency: document.getElementById("foodConsistency").value,
        age: document.getElementById("age").value,
        weight: parseFloat(document.getElementById("weight").value) || 0,
        height: parseFloat(document.getElementById("height").value) || 0,
        bmi: 0,
        preference: document.getElementById("preferences").value
            .split(",")
            .map(p => ({ name: p.trim() }))
            .filter(p => p.name),
        diet: document.getElementById("diets").value
            .split(",")
            .map(d => ({ name: d.trim() }))
            .filter(d => d.name),
        allergies: document.getElementById("allergies").value
            .split(",")
            .map(a => ({ name: a.trim() }))
            .filter(a => a.name),
        floor: parseInt(document.getElementById("floor").value),
        roomNumber: parseInt(document.getElementById("roomNumber").value),
        status: true,
        comment: document.getElementById("comment").value
    };

    const response = await fetch(
        `http://localhost:8080/api/residents/create?nurseEmail=${encodeURIComponent(nurseEmail)}`,
        {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(residentData)
        }
    );

    if (response.ok) {
        alert("Beboer oprettet og email sendt");
        window.location.href = "resident-admin.html";
    } else {
        const errorText = await response.text();
        alert("Fejl: " + errorText);
    }
}
