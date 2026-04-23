// --- ALGORITMO 1: Fuerza Bruta (de 1 en 1) ---
function algoritmoResto(limite) {
    let multiplos = [];
    for (let i = 1; i <= limite; i++) {
        // Comprobamos si el resto de dividir entre 11 es 0
        if (i % 11 === 0) {
            multiplos.push(i);
        }
    }
    return multiplos;
}

// --- ALGORITMO 2: Saltos (de 11 en 11) ---
function algoritmoSaltos(limite) {
    let multiplos = [];
    // Empezamos en 11 y sumamos 11 en cada vuelta. 
    for (let i = 11; i <= limite; i += 11) {
        multiplos.push(i);
    }
    return multiplos;
}

// --- CONTROLADOR Y BENCHMARK ---
function compararMultiplos11() {
    const limiteInput = document.getElementById("numero").value;
    const limite = parseInt(limiteInput);
    
    const divTiempoResto = document.getElementById("tiempoResto");
    const divTiempoSaltos = document.getElementById("tiempoSaltos");
    const divTotal = document.getElementById("totalMultiplos");
    const seccionResultados = document.getElementById("seccionResultados");
    const divListaMultiplos = document.getElementById("listaMultiplos");

    if (isNaN(limite) || limite < 11) {
        alert("Por favor, introduce un número válido mayor o igual a 11.");
        return;
    }

    // Límite de seguridad
    const LIMITE_SEGURO = 10000000; // 10 millones
    if (limite > LIMITE_SEGURO) {
        alert("⚠️ ATENCIÓN: Superado el límite de seguridad. Reduciendo la petición a 10.000.000.");
        document.getElementById("numero").value = LIMITE_SEGURO;
        return;
    }

    // --- TEST 1: Bucle con Resto ---
    let t0 = performance.now();
    let multiplosResto = algoritmoResto(limite);
    let t1 = performance.now();
    let tiempoResto = (t1 - t0).toFixed(2);

    // --- TEST 2: Bucle con Saltos ---
    let t2 = performance.now();
    let multiplosSaltos = algoritmoSaltos(limite);
    let t3 = performance.now();
    let tiempoSaltos = (t3 - t2).toFixed(2);

    // --- ACTUALIZAR INTERFAZ ---
    divTiempoResto.textContent = `${tiempoResto} ms`;
    divTiempoSaltos.textContent = `${tiempoSaltos} ms`;
    divTotal.textContent = multiplosSaltos.length.toLocaleString();

    // --- RENDERIZADO SEGURO DE NÚMEROS ---
    seccionResultados.style.display = "block";
    const LIMITE_VISUAL = 1000;

    if (multiplosSaltos.length > LIMITE_VISUAL) {
        let muestra = multiplosSaltos.slice(0, LIMITE_VISUAL).join(", ");
        let numerosOcultos = (multiplosSaltos.length - LIMITE_VISUAL).toLocaleString();
        divListaMultiplos.textContent = `${muestra} ... [ RENDIMIENTO: Se han ocultado ${numerosOcultos} múltiplos adicionales para no bloquear tu pantalla. ]`;
    } else {
        divListaMultiplos.textContent = multiplosSaltos.join(", ");
    }
}

function limpiarResultado() {
    document.getElementById("numero").value = "";
    document.getElementById("tiempoResto").textContent = "0.00 ms";
    document.getElementById("tiempoSaltos").textContent = "0.00 ms";
    document.getElementById("totalMultiplos").textContent = "0";
    document.getElementById("seccionResultados").style.display = "none";
    document.getElementById("listaMultiplos").textContent = ""; 
}