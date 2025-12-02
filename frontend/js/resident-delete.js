const API = "http://localhost:8080/api/residents";

async function sletBeboer(event) {
    event.preventDefault();
    const id = Number(document.getElementById("deleteId").value);
    if (!id) return alert("Indtast et ID");

    if (!confirm(`Er du sikker på du vil slette beboer med ID ${id}?`)) return;

    const res = await fetch(`${API}/delete/${id}`, { method: "DELETE" });
    if (!res.ok) return alert("Fejl ved sletning");

    alert("Beboer slettet!");
    document.getElementById("deleteForm").reset();
}
