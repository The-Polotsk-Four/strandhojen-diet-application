window.addEventListener("DOMContentLoaded", initApp);

const loginUrl = "http://localhost:8080/api/login"

async function initApp(){

    console.log("login.html");

    document.querySelector("#login-form").addEventListener("submit",login);


}


async function login(e) {
    e.preventDefault();

    const form = e.target;

    const resp = await fetch(loginUrl, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: form.login.value,
            password: form.password.value
        })
    });
    if (!resp.ok){
        console.error("Cant find user");
        alert("Invalid username or password");
        return
    }


    const data = await resp.json();
    console.log(data);




    form.reset();
}
