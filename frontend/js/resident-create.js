import { loadNurses } from "./loadNurses.js";
async function opretBeboer(event) {
    event.preventDefault();

    const nurse = await loadNurses();


    const nurseEmail = document.getElementById("nurse").value;

    if (!nurseEmail) {
        alert("Vælg venligst en sygeplejerske.");
        return;
    }

    const name = document.getElementById("name").value;
    const age = document.getElementById("age").value;
    const weight = parseFloat(document.getElementById("weight").value) || 0;
    const height = parseFloat(document.getElementById("height").value) || 0;
    const floor = parseInt(document.getElementById("floor").value);
    const roomNumber = parseInt(document.getElementById("roomNumber").value);
    const foodConsistency = document.getElementById("foodConsistency").value;
    const comment = document.getElementById("comment").value;

    const preferences = document.getElementById("preferences").value
        .split(',')
        .map(p => ({ name: p.trim() }))
        .filter(p => p.name);

    const diets = document.getElementById("diets").value
        .split(',')
        .map(d => ({ name: d.trim() }))
        .filter(d => d.name);

    const allergies = document.getElementById("allergies").value
        .split(',')
        .map(a => ({ name: a.trim() }))
        .filter(a => a.name);

    const residentData = {
        id: null,
        name: name,
        foodConsistency: foodConsistency,
        age: age,
        weight: weight,
        height: height,
        bmi: 0,
        preference: preferences,
        diet: diets,
        allergies: allergies,
        floor: floor,
        roomNumber: roomNumber,
        status: true,
        comment: comment
    };

    try {
        const response = await fetch(
            `/api/residents/create?nurseEmail=${encodeURIComponent(nurseEmail)}`,
            {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(residentData)
            }
        );

        if (response.ok) {
            alert("Beboer oprettet og email sendt til sygeplejersken");
            window.location.href = "resident-admin.html";
        } else {
            const errorData = await response.json();
            alert("Fejl: " + (errorData.message || response.statusText));
        }
    } catch (error) {
        console.error(error);
        alert("Noget gik galt ved oprettelse af beboer.");
    }
}

