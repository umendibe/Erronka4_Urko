/*
  Estadistiken Kargatze Zerbitzua - estadistikak-kargatze.js
  Estadistikak.json fitxategian datuak kargatzen dituen eta HTML orrialdean eguneratzen ditu
  Dinamikoki CSS kartak eta taulak betetzen ditu JSON datuekin
*/

/**
 * Kardatzeek JSON fitxategia kargatzen du eta datuak bistaratzen ditu
 */
async function kargatuEstatistikak() {
    try {
        // JSON fitxategia kargatu
        const erantzuna = await fetch('js/estadistikak.json');
        
        if (!erantzuna.ok) {
            console.error('Ezin izan da estadistikak fitxategia kargatu:', erantzuna.status);
            return;
        }
        
        const datuak = await erantzuna.json();
        
        // Irabazi guztiak eguneratu
        const irabaziaElement = document.querySelector('.irabaziak-totala .datua-handia');
        if (irabaziaElement && datuak.irabaziak_totala) {
            irabaziaElement.textContent = datuak.irabaziak_totala;
        }
        
        // Stock baxua zerrenda eguneratu
        const stockLista = document.querySelector('.stock-baxua .lista-items');
        if (stockLista && datuak.stock_baxua) {
            stockLista.innerHTML = '';
            datuak.stock_baxua.forEach(item => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <span class="item-izena">${item.izena}</span>
                    <span class="item-balioa alerta">${item.kantitatea} ale</span>
                `;
                stockLista.appendChild(li);
            });
        }
        
        // Gehien saldutakoak taula eguneratu
        const gehienTaula = document.querySelector('.top-produktuak tbody');
        if (gehienTaula && datuak.gehien_saldutakoak) {
            gehienTaula.innerHTML = '';
            datuak.gehien_saldutakoak.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${item.produktua}</td>
                    <td>${item.salmentak}</td>
                `;
                gehienTaula.appendChild(tr);
            });
        }
        
        // Bezero onenak taula eguneratu
        const bezeroTaula = document.querySelector('.top-bezeroak tbody');
        if (bezeroTaula && datuak.bezero_onenak) {
            bezeroTaula.innerHTML = '';
            datuak.bezero_onenak.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${item.bezeroa}</td>
                    <td>${item.eskaerak}</td>
                `;
                bezeroTaula.appendChild(tr);
            });
        }
        
        // Hileko irabaziak taula eguneratu
        const hiilekoTaula = document.querySelector('.hileko-irabaziak tbody');
        if (hiilekoTaula && datuak.hileko_irabaziak) {
            hiilekoTaula.innerHTML = '';
            datuak.hileko_irabaziak.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${item.urtea}</td>
                    <td>${item.hila}</td>
                    <td>${item.irabazia}</td>
                `;
                hiilekoTaula.appendChild(tr);
            });
        }
        
        // Inoiz erosi gabeak zerrenda eguneratu
        const inoyizLista = document.querySelector('.inoiz-erosi-gabeak .lista-items');
        if (inoyizLista && datuak.inoiz_erosi_gabeak) {
            inoyizLista.innerHTML = '';
            datuak.inoiz_erosi_gabeak.forEach(item => {
                const li = document.createElement('li');
                li.textContent = item;
                inoyizLista.appendChild(li);
            });
        }
        
        // Balio handikoak zerrenda eguneratu
        const baliozLista = document.querySelector('.balio-handikoak .lista-items');
        if (baliozLista && datuak.balio_handikoak) {
            baliozLista.innerHTML = '';
            datuak.balio_handikoak.forEach(item => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <span class="item-izena">${item.izena}</span>
                    <span class="item-balioa">${item.balioa}</span>
                `;
                baliozLista.appendChild(li);
            });
        }
        
    } catch (errore) {
        console.error('Errore ao kargatzen estatistikak:', errore);
    }
}

// Dokumentua kargaturik egon bezain laster, estatistikak kargatu
document.addEventListener('DOMContentLoaded', () => {
    kargatuEstatistikak();
});
