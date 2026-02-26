/*
  Footer eta Newsletter Kudeaketa - footer.js
  Newsletter formularen bidalketa, email-aren balidazioa, eta mezua
  Footer-aren orokorraren funtzionaltasuna
*/

function hasieratuNewsletter() {
    const newsletterForm = document.querySelector('.footer-newsletter-inprimakia');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', (e) => {
            e.preventDefault();
            alert('Eskerrik asko harpideratzeagatik!');
        });
    }
}

// Hasieratu newsletter
document.addEventListener('DOMContentLoaded', () => {
    hasieratuNewsletter();
});
