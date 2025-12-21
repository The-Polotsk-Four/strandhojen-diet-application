const API = "http://localhost:8080/api/residents";

async function findBeboer(event) {
    event.preventDefault();
    const id = Number(document.getElementById("searchId").value);
    if (!id) return alert("Indtast et ID");

    const res = await fetch(`${API}/${id}`,{
        credentials: "include",
    });
    if (!res.ok) return alert("Beboer ikke fundet");

    const resident = await res.json();
    const resultDiv = document.getElementById("result");
    console.log(resultDiv.innerHTML);
    resultDiv.innerHTML = `
        <p>ID: ${resident.id}</p>
        <p>Navn: ${resident.name}</p>
        <p>Fødevarens konsistens: ${resident.foodConsisatency}</p>
        <p>Alder: ${resident.age}</p>
        <p>Vægt: ${resident.weight}</p>
        <p>Højde: ${resident.height}</p>
        <p>BMI: ${resident.bmi}</p>
        <p>Etage: ${resident.floor}</p>
        <p>Værelsesnummer: ${resident.roomNumber}</p>
        <p>Status: ${resident.status ? 'Aktiv' : 'Inaktiv'}</p>
        <p>Kommentar: ${resident.comment}</p>
    `;
}
