document.addEventListener("DOMContentLoaded", () => {
  const contenedorPeliculas = document.getElementById("catalogo-container");

  async function cargarPeliculas() {
    try {
      const respuesta = await fetch("assets/json/movies.json");
      const datosPeliculas = await respuesta.json();

      pintarPeliculas(datosPeliculas, contenedorPeliculas);
    } catch (error) {
      console.log(error);
    }
  }

  cargarPeliculas();
});

function pintarPeliculas(peliculas, contenedor) {
  peliculas.forEach((pelicula) => {
    const {
      ID,
      Titulo,
      Anio,
      Direccion,
      Duracion,
      Nacionalidad,
      Actores,
      Genero,
      Sinopsis,
      Imagen,
    } = pelicula;
  });
}
