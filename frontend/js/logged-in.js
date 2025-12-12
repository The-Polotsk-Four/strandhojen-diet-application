import { getCurrentUser} from "./fetchUser.js";

window.addEventListener("DOMContentLoaded", initApp);



async function initApp() {
    const user = await getCurrentUser();

    if (!user) {
        window.location.href = 'login.html';
    } else {
        console.log('Logged in as:', user.login);
    }
}




