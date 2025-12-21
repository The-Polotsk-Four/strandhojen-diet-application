import { logout } from "../logout.js";

class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
     <header className="header">
    <nav>
        <ul>
            <li><a href="../html/index.html">Hjem</a></li>
            <li><a href="../html/resident.create.html">Opret beboer</a></li>
            <li><a href="../html/resident-update.html">Opdater beboer </a></li>
            <li><a href="../html/resident-list.html">Søg efter beboer</a></li>
            <li><a href="../html/resident-delete.html">Slet beboer</a></li>
            <li><a href="../html/create-user.html">Opret bruger</a></li>
            <li><a href="../html/delete-user-.html">Slet bruger</a></li>
            <li><a href="../html/resident-add-allergies.html">Rediger allergier</a></li>
            <li>
                 <a href="#" id="logout">Log ud</a>
            </li>

        </ul>
    </nav>
</header>
    `;

        this.querySelector("#logout").addEventListener("click", logout);
    }
}

customElements.define("header-component", HeaderComponent);