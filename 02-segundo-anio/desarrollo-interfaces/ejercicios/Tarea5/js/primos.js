// Algoritmo 1
function algoritmoFuerzaBruta(limite) {
    function esPrimo(num) {
        if (num < 2) return false;
        for (let i = 2; i <= Math.sqrt(num); i++) {
            if (num % i === 0) return false;
        }
        return true;
    }

    let primos = [];
    for (let i = 2; i <= limite; i++) {
        if (esPrimo(i)) {
            primos.push(i);
        }
    }
    return primos;
}

// ---Algoritmo eratostenes ---
function algoritmoCriba(limite) {
    // Creamos un array de booleanos, todos 'true' inicialmente
    let criba = new Array(limite + 1).fill(true);
    criba[0] = false;
    criba[1] = false;

    // Tachamos los múltiplos
    for (let p = 2; p * p <= limite; p++) {
        if (criba[p] === true) {
            for (let i = p * p; i <= limite; i += p) {
                criba[i] = false; // No es primo
            }
        }
    }

    // Recolectamos los que quedaron como 'true'
    let primos = [];
    for (let i = 2; i <= limite; i++) {
        if (criba[i]) {
            primos.push(i);
        }
    }
    return primos;
}

// --- Rendimiento benchmark 
function compararAlgoritmos() {
    const limiteInput = document.getElementById("numero").value;
    const limite = parseInt(limiteInput);
    
    // Elementos del DOM
    const divTiempoFuerza = document.getElementById("tiempoFuerza");
    const divTiempoCriba = document.getElementById("tiempoCriba");
    const divTotal = document.getElementById("totalPrimos");

    // Validaciones básicas
    if (isNaN(limite) || limite < 2) {
        alert("Por favor, introduce un número válido mayor o igual a 2.");
        return;
    }

    // Limite seguro
    const LIMITE_SEGURO = 5000000;
    
    if (limite > LIMITE_SEGURO) {
        alert("⚠️ ATENCIÓN: Este equipo no tiene la suficiente potencia para procesar su petición de manera segura usando métodos tradicionales. Intente con números más pequeños (Máximo recomendado: 5.000.000).");
        document.getElementById("numero").value = LIMITE_SEGURO; 
        return; 
    }

    // --- test 1
    let t0 = performance.now(); // Empezamos cronómetro
    let primosFuerza = algoritmoFuerzaBruta(limite);
    let t1 = performance.now(); // Paramos cronómetro
    let tiempoFuerza = (t1 - t0).toFixed(2); // Calculamos diferencia

    // --- test 2
    let t2 = performance.now();
    let primosCriba = algoritmoCriba(limite);
    let t3 = performance.now();
    let tiempoCriba = (t3 - t2).toFixed(2);

    // --- actualizamos
    divTiempoFuerza.textContent = `${tiempoFuerza} ms`;
    divTiempoCriba.textContent = `${tiempoCriba} ms`;
    divTotal.textContent = primosCriba.length.toLocaleString(); 

    const seccionResultados = document.getElementById("seccionResultados");
    const divListaPrimos = document.getElementById("listaPrimos");
    const LIMITE_VISUAL = 1000;

    seccionResultados.style.display = "block";
    
    if (primosCriba.length > LIMITE_VISUAL) {
        let muestra = primosCriba.slice(0, LIMITE_VISUAL).join(", ");
        let numerosOcultos = (primosCriba.length - LIMITE_VISUAL).toLocaleString();
        divListaPrimos.textContent = `${muestra} ... [ ¡PELIGRO DE RENDIMIENTO! Se han ocultado ${numerosOcultos} números adicionales para evitar el colapso del navegador. ]`;
    } else {
        divListaPrimos.textContent = primosCriba.join(", ");
    }
}


function limpiarResultado() {
    document.getElementById("numero").value = "";
    document.getElementById("tiempoFuerza").textContent = "0.00 ms";
    document.getElementById("tiempoCriba").textContent = "0.00 ms";
    document.getElementById("totalPrimos").textContent = "0";
    document.getElementById("seccionResultados").style.display = "none";
    document.getElementById("listaPrimos").textContent = "";
}