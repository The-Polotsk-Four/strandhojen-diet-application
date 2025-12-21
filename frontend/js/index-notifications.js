async function loadIndexNotifications() {
    const content = document.getElementById("notifications-content");

    const userResponse = await fetch(
        "http://localhost:8080/api/users/current",
        { credentials: "include" }
    );

    if (!userResponse.ok) return;

    const user = await userResponse.json();

    if (user.userrole !== "SYGEPLEJERSKE") return;

    const notificationResponse = await fetch(
        "http://localhost:8080/api/notifications",
        { credentials: "include" }
    );

    if (!notificationResponse.ok) return;

    const notifications = await notificationResponse.json();

    content.innerHTML = "";

    if (notifications.length === 0) {
        content.innerHTML = `<p class="empty">Ingen notifikationer</p>`;
        return;
    }

    notifications.forEach(n => {
        const div = document.createElement("div");
        div.className = "notification-item";

        div.innerHTML = `
            <span>${n.message}</span>
            <span class="notification-close">✖</span>
        `;

        div.querySelector(".notification-close").onclick = async () => {
            await fetch(
                `http://localhost:8080/api/notifications/${n.id}/read`,
                {
                    method: "PUT",
                    credentials: "include"
                }
            );

            div.remove();

            if (content.children.length === 0) {
                content.innerHTML = `<p class="empty">Ingen notifikationer</p>`;
            }
        };

        content.appendChild(div);
    });
}

document.addEventListener("DOMContentLoaded", loadIndexNotifications);
