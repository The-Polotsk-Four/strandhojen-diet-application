const api = "http://localhost:8080/api/residents/create";

async function opretBeboer (event) {
    event.preventDefault();
}

const resident = {
    FoodConsisatency: document.querySelector("#food-consisatency").value