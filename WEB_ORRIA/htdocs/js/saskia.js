/*
  Erosketak Saskia Kudeaketa - saskia.js
  Saskia-edukia bistaratze, produktu-kantitatea, prezioa, eta errenkada-galtzea
  LocalStorage-rekin datuak sinkronizatu
*/

const produktuKontainer = document.getElementById('produktuak');
const kantitateaElement = document.getElementById('kantitatea');
const prezioaElement = document.getElementById('prezioa');
const hutsikMezua = document.getElementById('karritoa_hutsik');
const totalaKontainer = document.getElementById('totala');
const berrezarriBtn = document.getElementById('berrezarri');
const erosiBtn = document.getElementById('erosiBtn');

function sortuProduktuak() {
    produktuKontainer.innerHTML = "";
    const produktuak = JSON.parse(localStorage.getItem(keyLocalStorage));

    if (produktuak && produktuak.length > 0) {
        hutsikMezua.classList.add("izkutatu");
        totalaKontainer.classList.remove("izkutatu");

        produktuak.forEach(produktu => {
            const txartela = document.createElement("div");
            txartela.classList.add("saskia-produktua");

            txartela.innerHTML = `
                <img src="${produktu.img}" alt="${produktu.productName}" class="produktu-irudia-saskia">
                <div class="produktu-info">
                    <h3 class="produktu-izena-saskia">${produktu.productName}</h3>
                    <p class="produktu-xehetasunak">Tamaina: M | Kolorea: Beltza</p>
                </div>
                <span class="produktu-prezioa-saskia">${produktu.price}€</span>
                <div class="kantitate-kontrolak">
                    <button class="kantitate-btn kendu-btn" aria-label="Kendu">−</button>
                    <span class="kantitate-zenbakia">${produktu.kantitatea}</span>
                    <button class="kantitate-btn gehitu-btn" aria-label="Gehitu">+</button>
                </div>
                <button class="ezabatu-btn" aria-label="Ezabatu">🗑️</button>
            `;

            const kenduBtn = txartela.querySelector(".kendu-btn");
            const gehituBtn = txartela.querySelector(".gehitu-btn");
            const ezabatuBtn = txartela.querySelector(".ezabatu-btn");

            kenduBtn.addEventListener("click", () => {
                karritoariKendu(produktu);
                sortuProduktuak();
            });

            gehituBtn.addEventListener("click", () => {
                karritoraGehitu(produktu);
                sortuProduktuak();
            });

            ezabatuBtn.addEventListener("click", () => {
                // Produktua guztiz ezabatu
                let memoria = JSON.parse(localStorage.getItem(keyLocalStorage));
                const produktuIndizea = memoria.findIndex(arropa => arropa.id === produktu.id);
                if (produktuIndizea !== -1) {
                    memoria.splice(produktuIndizea, 1);
                    localStorage.setItem(keyLocalStorage, JSON.stringify(memoria));
                    eguneratuZenbakia();
                    sortuProduktuak();
                }
            });

            produktuKontainer.appendChild(txartela);
        });

        eguneratuTotalak(produktuak);
        if (erosiBtn) erosiBtn.disabled = false;

    } else {
        hutsikMezua.classList.remove("izkutatu");
        totalaKontainer.classList.add("izkutatu");
        eguneratuTotalak([]);
        if (erosiBtn) erosiBtn.disabled = true;
    }
}

function eguneratuTotalak(produktuak) {
    const kantitatea = produktuak.reduce((acc, curr) => acc + curr.kantitatea, 0);
    const prezioa = produktuak.reduce((acc, curr) => acc + (curr.price * curr.kantitatea), 0);

    kantitateaElement.innerText = kantitatea;
    prezioaElement.innerText = prezioa.toFixed(2) + '€';
}

berrezarriBtn.addEventListener('click', () => {
    if (confirm('Ziur zaude saskia hustu nahi duzula?')) {
        karritoaHustu();
        sortuProduktuak();
    }
});

if (erosiBtn) {
    erosiBtn.addEventListener('click', () => {
        alert("Erosketa ondo burutu da! Eskerrik asko.");
        karritoaHustu();
        sortuProduktuak();
    });
}

// Hasieratu
sortuProduktuak();