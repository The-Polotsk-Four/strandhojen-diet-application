class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
     <header className="header">
    <nav>
        <ul>
            <li><a href="/html/index.html">Hjem</a></li>
            <li><a href="/html/resident.create.html">Opret beboer</a></li>
            <li><a href="/html/resident-update.html">Opdater beboer </a></li>
            <li><a href="/html/resident-find.html">Søg efter beboer</a></li>
            <li><a href="/html/resident-delete.html">Slet beboer</a></li>
            <li><a href="/html/create-user.html">Opret bruger</a></li>
            <li><a href="/html/delete-user-.html">Slet bruger</a></li>
            <li><a href="/html/resident-add-allergies.html">Rediger allergier</a></li>

        </ul>
    </nav>
</header>
    `;
    }
}

customElements.define("header-component", HeaderComponent);