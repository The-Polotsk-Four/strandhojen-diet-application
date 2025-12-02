const API = "http://localhost:8080/api/residents";

async function opretBeboer(event) {
    event.preventDefault();

    const resident = {
        FoodConsisatency: document.getElementById("foodConsistency").value,
        age: document.getElementById("age").value,
        weight: Number(document.getElementById("weight").value),
        height: Number(document.getElementById("height").value),
        bmi: Number(document.getElementById("bmi").value),
        preference: [],
        diet: [],
        allergies: [],
        floor: Number(document.getElementById("floor").value),
        roomNumber: Number(document.getElementById("roomNumber").value),
        status: document.getElementById("status").value === "true",
        comment: document.getElementById("comment").value
    };

    const res = await fetch(`${API}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(resident)
    });

    if (!res.ok) return alert("Fejl ved oprettelse");

    alert("Beboer oprettet!");
    document.getElementById("createForm").reset();
}
