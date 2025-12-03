window.addEventListener("DOMContentLoaded",initApp);

const logoutUrl = "http://localhost:8080/logout";

async function initApp(){

    document.querySelector("#logout").addEventListener("click", logout);

}


async function logout(e) {
    e.preventDefault();

    const resp = await fetch(logoutUrl, {
        method: "POST",
        credentials: "include"
    });

    if (!resp.ok) {
        console.error("Logout failed");
        return;
    }


    window.location.href = "login.html";
}
