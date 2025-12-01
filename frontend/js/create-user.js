document.addEventListener("DOMContentLoaded", initapp);

async function initapp(){
    console.log("test");

    document.querySelector("#create-user").addEventListener("submit", createUser);

}

 async function createUser(e){
    e.preventDefault();
    console.log("clicked");

    const form = e.target;

    const user ={
        userrole: form.role.value,
        login: form.login.value,
        password: form.password.value,
        name: form.name.value
    }


     const resp = await fetch("http://localhost:8080/api/users/create", {
         method: "POST",
         headers: {
             "Content-Type": "application/json"
         },
         body: JSON.stringify(user)
     });


     if (!resp.ok){
        console.error("Error creating user");
    }

    const data = await resp.json();

    console.log(data);
    form.reset();
    return data;
}