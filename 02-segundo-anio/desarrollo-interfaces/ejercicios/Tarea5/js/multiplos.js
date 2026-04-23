// --- Fuerza bruta
function algoritmoResto(limite) {
    let multiplos = [];
    for (let i = 1; i <= limite; i++) {
        if (i % 3 === 0) {
            multiplos.push(i);
        }
    }
    return multiplos;
}

// --- Saltos
function algoritmoSaltos(limite) {
    let multiplos = [];
    for (let i = 3; i <= limite; i += 3) {
        multiplos.push(i);
    }
    return multiplos;
}

// --- Benchmark
function compararMultiplos() {
    const limiteInput = document.getElementById("numero").value;
    const limite = parseInt(limiteInput);
    
    const divTiempoResto = document.getElementById("tiempoResto");
    const divTiempoSaltos = document.getElementById("tiempoSaltos");
    const divTotal = document.getElementById("totalMultiplos");
    const seccionResultados = document.getElementById("seccionResultados");
    const divListaMultiplos = document.getElementById("listaMultiplos");

    if (isNaN(limite) || limite < 3) {
        alert("Por favor, introduce un número válido mayor o igual a 3.");
        return;
    }

    // Safe Guard (Generar múltiplos es más rápido que primos, podemos subir el límite)
    const LIMITE_SEGURO = 10000000;
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