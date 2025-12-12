class Footer extends HTMLElement{
    constructor() {
        super();
    }

    connectedCallback(){
        this.innerHTML =`

<footer>
    <p>&copy; 2025 Diakonhjem Strandhøjen</p>
    <p> Ved Engen 2, Nyborg 5800 </p>
</footer>
        `;
    }
}

customElements.define('footer-component', Footer)