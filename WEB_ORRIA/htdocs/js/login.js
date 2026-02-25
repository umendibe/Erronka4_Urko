const bidaliLogin = document.getElementById('bidali_login');
const bidaliErregistratu = document.getElementById('bidali_erregistratu');

function erregistratu() {
    const izena = document.getElementById('izena_input').value;
    const email = document.getElementById('reg_email').value;
    const pasahitza = document.getElementById('reg_kontraseña').value;
    const pasahitzaErrepikatu = document.getElementById('reg_kontraseña_errepikatu').value;

    if (!izena || !email || !pasahitza) {
        window.alert('MESEDEZ, BETE DATU GUZTIAK');
        return;
    }

    if (pasahitza === pasahitzaErrepikatu) {
        window.alert('KONTUA SORTU DUZU');
        localStorage.setItem('isLoggedIn', 'true');
        window.location.href = 'index.html';
    } else {
        window.alert('PASAHITZAK EZ DIRA BERDINAK');
    }
}

function login() {
    const email = document.getElementById('email').value;
    const pasahitza = document.getElementById('kontraseña').value;

    if (!email || !pasahitza) {
        window.alert('MESEDEZ, SARTU EMAIL-A ETA PASAHITZA');
        return;
    }

    window.alert('LOGINA EGIN DUZU');
    localStorage.setItem('isLoggedIn', 'true');
    window.location.href = 'index.html';
}

if (bidaliLogin) bidaliLogin.addEventListener('click', login);
if (bidaliErregistratu) bidaliErregistratu.addEventListener('click', erregistratu);
