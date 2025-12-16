async function loadIndexNotifications() {
    const userResponse = await fetch("/api/users/current");
    if (!userResponse.ok) return;

    const user = await userResponse.json();

    if (user.userrole !== "SYGEPLEJERSKE") return;

    const notificationResponse = await fetch("/api/notifications");
    const notifications = await notificationResponse.json();

    if (notifications.length === 0) return;

    const banner = document.getElementById("notification-banner");
    const text = document.getElementById("notification-text");

    text.textContent = `Der er ${notifications.length} nye ændringer i systemet`;

    document.getElementById("close-notification").onclick = async (e) => {
        e.stopPropagation();

        for (const n of notifications) {
            await fetch(`/api/notifications/${n.id}/read`, {
                method: "PUT"
            });
        }

        banner.classList.add("hidden");
    };
}

document.addEventListener("DOMContentLoaded", loadIndexNotifications);
