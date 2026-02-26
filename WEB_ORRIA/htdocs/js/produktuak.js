/*
  Produktuen Katalogoa Kudeaketa - produktuak.js
  JSON produktu-datuen karga, kategorien iragazkia, bilaketa, eta bistaraketa
  Dinamikoki kartela sorta eta interaktiboa
*/

let produktuGuztiak = [];
let hautatutakoKategoriak = new Set(['denak']);

// JSON fitxategia kargatu
async function kargaProduktuak() {
    console.log('Produktuak kargatzen...');
    try {
        const erantzuna = await fetch('js/produktuak.json');
        console.log('Fetch erantzuna:', erantzuna.status, erantzuna.statusText);

        if (!erantzuna.ok) {
            throw new Error(`HTTP errorea! status: ${erantzuna.status}`);
        }

        produktuGuztiak = await erantzuna.json();
        console.log('Produktuak kargatuta:', produktuGuztiak.length, 'produktu');

        // ID gehitu produktu bakoitzari
        produktuGuztiak = produktuGuztiak.map((produktu, index) => ({
            ...produktu,
            id: index + 1,
            productName: produktu.izena,
            price: produktu.prezioa
        }));

        console.log('Produktuak ID-ekin preparatuak');
        bistaratuProduktuak(produktuGuztiak);
    } catch (errorea) {
        console.error('Errorea produktuak kargatzean:', errorea);
    }
}

// Produktuak bistaratu
function bistaratuProduktuak(produktuak) {
    console.log('Produktuak bistaratzen...', produktuak.length, 'produktu');
    const edukiontzia = document.getElementById('shopContent');

    if (!edukiontzia) {
        console.error('Ez da "shopContent" elementua aurkitu!');
        return;
    }

    edukiontzia.innerHTML = '';

    if (produktuak.length === 0) {
        edukiontzia.innerHTML = '<p style="grid-column: 1/-1; text-align: center; color: #666;">Ez dago produkturik kategoria honetan.</p>';
        return;
    }

    produktuak.forEach((produktu, index) => {
        const txartela = document.createElement('div');
        txartela.classList.add('produktu-txartela');

        txartela.innerHTML = `
            <div class="produktu-irudia-edukiontzia">
                <img loading="lazy" src="${produktu.img}" alt="${produktu.izena}" class="produktu-irudia">
            </div>
            <div class="produktu-info">
                <h3>${produktu.izena}</h3>
                <div class="txartel-xehetasunak">
                    <div class="prezio-kaxa">
                        <p class="prezioa">${produktu.prezioa}€</p>
                    </div>
                </div>
                <button class="gehitu-saskira-btn" data-id="${produktu.id}">
                    Saskira gehitu
                </button>
            </div>
        `;

        edukiontzia.appendChild(txartela);
    });

    console.log('Produktu txartelak sortuak');

    // Event listeners gehitu botoi guztiei
    document.querySelectorAll('.gehitu-saskira-btn').forEach(botoia => {
        botoia.addEventListener('click', (e) => {
            const produktuId = parseInt(e.target.dataset.id);
            const produktu = produktuGuztiak.find(p => p.id === produktuId);

            if (produktu) {
                const kantitatea = karritoraGehitu(produktu);
                console.log('🛒 Produktua saskira gehituta:', produktu.izena);

                e.target.textContent = `Gehituta! (${kantitatea})`;
                e.target.style.backgroundColor = '#28a745';

                setTimeout(() => {
                    e.target.textContent = 'Saskira gehitu';
                    e.target.style.backgroundColor = '';
                }, 1000);
            }
        });
    });
}

// Iragazkiak kudeatu
function eguneratuIragazkiak() {
    const checkboxak = document.querySelectorAll('.iragazki-aukera input[type="checkbox"]');

    checkboxak.forEach(checkbox => {
        checkbox.addEventListener('change', (e) => {
            const kategoriaId = e.target.id.replace('Btn', '');

            if (kategoriaId === 'denak') {
                if (e.target.checked) {
                    hautatutakoKategoriak.clear();
                    hautatutakoKategoriak.add('denak');
                    checkboxak.forEach(cb => {
                        if (cb.id !== 'denakBtn') cb.checked = false;
                    });
                }
            } else {
                if (e.target.checked) {
                    hautatutakoKategoriak.delete('denak');
                    document.getElementById('denakBtn').checked = false;
                    hautatutakoKategoriak.add(kategoriaId);
                } else {
                    hautatutakoKategoriak.delete(kategoriaId);
                    if (hautatutakoKategoriak.size === 0) {
                        hautatutakoKategoriak.add('denak');
                        document.getElementById('denakBtn').checked = true;
                    }
                }
            }

            aplikatuIragazkiak();
        });
    });
}

// Iragazkiak aplikatu
function aplikatuIragazkiak() {
    let produktuIragaziak = produktuGuztiak;

    if (!hautatutakoKategoriak.has('denak')) {
        produktuIragaziak = produktuGuztiak.filter(produktu => {
            return hautatutakoKategoriak.has(produktu.kategoria.toString());
        });
    }

    bistaratuProduktuak(produktuIragaziak);
    eguneratuTitulua();
}

// Titulua eguneratu
function eguneratuTitulua() {
    const titulua = document.getElementById('categoryTitle');

    if (hautatutakoKategoriak.has('denak')) {
        titulua.textContent = 'Produktu guztiak';
    } else if (hautatutakoKategoriak.size === 1) {
        const kategoria = Array.from(hautatutakoKategoriak)[0];
        const kategoriaTituluak = {
            '1111': 'Sudaderak',
            '2222': 'Alkandorak',
            '3333': 'Kamisetak',
            '4444': 'Galtzak',
            '5555': 'Zapatillak'
        };
        titulua.textContent = kategoriaTituluak[kategoria] || 'Produktuak';
    } else {
        titulua.textContent = `Produktuak (${hautatutakoKategoriak.size} kategoria)`;
    }
}

// Hero CTA scroll smooth
function hasieratuHeroCTA() {
    const heroCTA = document.querySelector('.hero-cta');
    if (heroCTA) {
        heroCTA.addEventListener('click', () => {
            document.querySelector('.produktu-sarea').scrollIntoView({ behavior: 'smooth' });
        });
    }
}

// Hasieratu
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM kargatu da');
    kargaProduktuak();
    eguneratuIragazkiak();
    hasieratuHeroCTA();
});
