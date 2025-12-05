import { getCurrentUser} from "./fetchUser.js";

window.addEventListener("DOMContentLoaded", initApp);


// const API_BASE = 'http://localhost:8080';

async function initApp() {
    const user = await getCurrentUser();

    if (!user) {
        window.location.href = 'login.html';
    } else {
        console.log('Logged in as:', user.login);
    }
}



// export async function getCurrentUser() {
//     try {
//         const response = await fetch(`${API_BASE}/api/users/current`, {
//             method: 'GET',
//             credentials: 'include',
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         });
//
//         if (response.ok) {
//             const user = await response.json();
//             return user;
//         } else if (response.status === 401 || response.status === 403) {
//             return null;
//         } else {
//             console.error('Failed to get current user:', response.status);
//             return null;
//         }
//     } catch (error) {
//         console.error('Error getting current user:', error);
//         return null;
//     }
// }



