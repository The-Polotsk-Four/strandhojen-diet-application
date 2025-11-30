document.getElementById("deleteBtn").addEventListener("click", deleteUser);

function deleteUser() {
    const userId = document.getElementById("userId").value;
    const resultDiv = document.getElementById("result");

    if (!userId) {
        resultDiv.textContent = "Indtast venligst et ID.";
        return;
    }

    const url = `http://localhost:8080/api/users/${userId}`;

    fetch(url, {
        method: "DELETE"
    })
        .then(response => {
            if (response.status === 204) {
                resultDiv.textContent = "Brugeren blev slettet.";
            } else if (response.status === 404) {
                resultDiv.textContent = "Brugeren blev ikke fundet.";
            } else {
                resultDiv.textContent = "Der opstod en fejl.";
            }
        })
        .catch(() => {
            resultDiv.textContent = "Netværksfejl — kunne ikke kontakte serveren.";
        });
}
