import { getCurrentUser } from './fetchUser.js';

init();

async function init() {
    const user = await getCurrentUser();
    console.log("test");
    if (user.userrole !== "ADMIN" ){
        window.location.href = 'login.html';
    } else {
        console.log(user.userrole);
    }
}




