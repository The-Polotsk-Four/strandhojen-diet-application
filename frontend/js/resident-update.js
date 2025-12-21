const API = "http://localhost:8080/api/residents";

async function opdaterBeboer(event) {
    event.preventDefault();

    const id = Number(document.getElementById("residentId").value);

    const resident = {
        id: id,
        foodConsisatency: document.getElementById("foodConsistency").value,
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

    const res = await fetch(`${API}/update/${id}`, {
        method: "PUT",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(resident)
    });

    if (!res.ok) return alert("Fejl ved opdatering");

    alert("Beboer opdateret!");
    document.getElementById("updateForm").reset();
}
