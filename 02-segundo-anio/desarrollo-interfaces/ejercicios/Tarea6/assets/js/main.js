document.addEventListener("DOMContentLoaded", () => {
  const contenedorPeliculas = document.getElementById("catalogo-container");
  const modal = document.getElementById("modal-pelicula");
  const modalInfo = document.getElementById("modal-info");
  const btnCerrar = document.querySelector(".cerrar-modal");

  // 1. Configuramos el botón de cerrar UNA SOLA VEZ, aquí fuera del bucle
  btnCerrar.addEventListener('click', () => {
      modal.classList.remove('mostrar');
  });

  async function cargarPeliculas() {
    try {
      const respuesta = await fetch("assets/json/movies.json");
      const datosPeliculas = await respuesta.json();

      // 2. Le pasamos a la función las herramientas que necesita
      pintarPeliculas(datosPeliculas, contenedorPeliculas, modal, modalInfo);
    } catch (error) {
      console.log(error);
    }
  }

  cargarPeliculas();
});

// 3. Recibimos las herramientas en la función
function pintarPeliculas(peliculas, contenedor, modal, modalInfo) {
  peliculas.forEach((pelicula) => {
    const { Titulo, Anio, Direccion, Actores, Sinopsis, Imagen } = pelicula;

    const tarjeta = document.createElement("div");
    tarjeta.classList.add('pelicula-card');
    
    const tituloElemento = document.createElement('h3');
    tituloElemento.textContent = Titulo;

    const imagenElemento = document.createElement("img");
    imagenElemento.src = Imagen;
    imagenElemento.alt = Titulo;
    
    tarjeta.appendChild(imagenElemento);  
    tarjeta.appendChild(tituloElemento);

    // 4. La magia del clic usando las herramientas que recibimos
    imagenElemento.addEventListener('click', () => {
      modalInfo.innerHTML = `
          <h2>${Titulo}</h2>
          <div class="modal-body">
            <img src="${Imagen}" alt="${Titulo}" class="modal-imagen">
            <div class="modal-texto">
              <p><strong>Año:</strong> ${Anio}</p>
              <p><strong>Director:</strong> ${Direccion}</p>
              <p><strong>Actores:</strong> ${Actores}</p>
              <p><strong>Sinopsis:</strong> ${Sinopsis}</p>
            </div>
          </div>
      `;
      modal.classList.add('mostrar');
    });

    contenedor.appendChild(tarjeta);
  });
}