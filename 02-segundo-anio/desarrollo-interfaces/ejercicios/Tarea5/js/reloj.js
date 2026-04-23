document.addEventListener("DOMContentLoaded", () => {
  const saludoElement = document.getElementById("saludo");
  const relojDisplay = document.getElementById("relojDisplay");

  // Botones principales
  const btnConfig = document.getElementById("btnConfig");
  const btnZona = document.getElementById("btnZona"); // <- NUEVO BOTÓN

  // Elementos del Modal
  const customModal = document.getElementById("customModal");
  const btn12h = document.getElementById("btn12h");
  const btn24h = document.getElementById("btn24h");

  // --- ESTADOS DE LA APLICACIÓN ---
  let formato12h = false;
  let esCanarias = true; // Arrancamos por defecto en Canarias

  // --- Lógica de tiempo Dinámica ---
  function obtenerHora() {
    let tiempoLocal = new Date();

    if (esCanarias) {
      // Si estamos en Canarias, restamos 1 hora
      return new Date(tiempoLocal.getTime() - 60 * 60 * 1000);
    } else {
      // Si estamos en Península, devolvemos la hora tal cual
      return tiempoLocal;
    }
  }

  // --- Actualización constante del Reloj y el Saludo ---
  function renderizarReloj() {
    let tiempo = obtenerHora(); // Usamos la nueva función
    let horaReal = tiempo.getHours();

    // 1. GESTIÓN DEL SALUDO Y DARK MODE
    let mensaje = "";
    if (horaReal >= 6 && horaReal < 12) {
      mensaje = "Buenos días";
      desactivarDarkMode();
    } else if (horaReal >= 12 && horaReal < 20) {
      mensaje = "Buenas tardes";
      desactivarDarkMode();
    } else {
      mensaje = "Buenas noches";
      activarDarkMode();
    }

    // Cambiamos el texto dependiendo de la zona
    let zonaTexto = esCanarias ? "Canarias" : "Península";
    saludoElement.textContent = `${mensaje} desde ${zonaTexto}`;

    // 2. FORMATEO DE LA HORA (12h vs 24h)
    let horasRender = horaReal;
    let minutos = tiempo.getMinutes().toString().padStart(2, "0");
    let segundos = tiempo.getSeconds().toString().padStart(2, "0");
    let sufijo = "";

    if (formato12h) {
      sufijo = horasRender >= 12 ? " PM" : " AM";
      horasRender = horasRender % 12;
      horasRender = horasRender ? horasRender : 12;
    } else {
      horasRender = horasRender.toString().padStart(2, "0");
    }

    // Pintamos la hora en el HTML
    relojDisplay.textContent = `${horasRender}:${minutos}:${segundos}${sufijo}`;
  }

  // --- Funciones de Dark Mode ---
  function activarDarkMode() {
    document.documentElement.style.setProperty("--background-color", "#0f172a");
    document.documentElement.style.setProperty("--text-color", "#f8fafc");
    document.documentElement.style.setProperty("--card-bg", "#1e293b");
  }

  function desactivarDarkMode() {
    document.documentElement.style.setProperty("--background-color", "#f4f4f4");
    document.documentElement.style.setProperty("--text-color", "#333");
    document.documentElement.style.setProperty("--card-bg", "#ffffff");
  }

  // --- Lógica de Botones (Event Listeners) ---

  // NUEVO: Evento para cambiar de Zona Horaria
  btnZona.addEventListener("click", () => {
    // Invertimos el estado (si era true pasa a false, y viceversa)
    esCanarias = !esCanarias;

    // Cambiamos el texto del botón para indicar la ACCIÓN CONTRARIA
    if (esCanarias) {
      btnZona.innerHTML = "🌍 Cambiar a Península";
    } else {
      btnZona.innerHTML = "🏝️ Cambiar a Canarias";
    }

    // Forzamos la actualización visual inmediatamente
    renderizarReloj();
  });

  // Abrir modal
  btnConfig.addEventListener("click", () => {
    customModal.classList.add("active");
  });

  // Si elige 12h
  btn12h.addEventListener("click", () => {
    formato12h = true;
    customModal.classList.remove("active");
    renderizarReloj();
  });

  // Si elige 24h
  btn24h.addEventListener("click", () => {
    formato12h = false;
    customModal.classList.remove("active");
    renderizarReloj();
  });

  // Iniciar el reloj
  renderizarReloj();
  setInterval(renderizarReloj, 1000);
});
