/*
  Deslizariaren Funtzionaltasuna - index.js
  Orri nagusiaren deslizaria kudeaketa, slide-ak aurrera/atzera, puntuak
  Automatikoa eta erabiltzaile-interaktiboa
*/

let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const dotsContainer = document.querySelector('.slider-dots');

slides.forEach((_, index) => {
    const dot = document.createElement('div');
    dot.classList.add('dot');
    if (index === 0) dot.classList.add('active');
    dot.addEventListener('click', () => goToSlide(index));
    dotsContainer.appendChild(dot);
});

const dots = document.querySelectorAll('.dot');

function showSlide(n) {
    slides.forEach(slide => slide.classList.remove('active'));
    dots.forEach(dot => dot.classList.remove('active'));

    currentSlide = (n + slides.length) % slides.length;

    slides[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

function goToSlide(n) {
    showSlide(n);
}

document.querySelector('.slider-btn.next').addEventListener('click', nextSlide);
document.querySelector('.slider-btn.prev').addEventListener('click', prevSlide);

async function cargarProduktuNabarmendua() {
    try {
        const response = await fetch('js/produktuak.json');
        if (!response.ok) {
            throw new Error('Errorea produktuak kargatzean');
        }

        const produktuak = await response.json();

        const nabarmendua = produktuak.slice(0, 4).map((produktu, index) => ({
            ...produktu,
            id: index + 1,
            productName: produktu.izena,
            price: produktu.prezioa
        }));

        bistaratuProduktuNabarmendua(nabarmendua);
    } catch (error) {
        console.error('Errorea:', error);
    }
}

function bistaratuProduktuNabarmendua(produktuak) {
    const grid = document.getElementById('produktu-grid');

    produktuak.forEach(produktu => {
        const card = document.createElement('div');
        card.classList.add('produktu-txartela');
        card.onclick = () => window.location.href = 'produktuak.html';

        card.innerHTML = `
            <div class="produktu-irudia-edukiontzia">
                <img loading="lazy" src="${produktu.img}" alt="${produktu.izena}" class="produktu-irudia">
            </div>
            <div class="produktu-info">
                <h3>${produktu.izena}</h3>
                <p class="prezioa">${produktu.prezioa}€</p>
            </div>
        `;

        grid.appendChild(card);
    });
}

const newsletterForm = document.getElementById('newsletterForm');
if (newsletterForm) {
    newsletterForm.addEventListener('submit', (e) => {
        e.preventDefault();
        alert('Eskerrik asko harpideratzeagatik! Laster jasoko duzu gure azken berriak.');
        newsletterForm.reset();
    });
}

document.addEventListener('DOMContentLoaded', () => {
    cargarProduktuNabarmendua();
});
