// js/main.js

// Buscamos la clase "formulario" en el documento HTML y la asignamos a la variable "formulario"
const formulario = document.querySelector(".formulario");
const cajaResultado = document.getElementById("caja-resultado");

// Escuchar el evento "submit" del formulario
formulario.addEventListener("submit", function (event) {
  // Prevenir el comportamiento por defecto del formulario (enviar datos y recargar la página)
  event.preventDefault();

  const nota1 = parseFloat(document.getElementById("nota1").value);
  const nota2 = parseFloat(document.getElementById("nota2").value);
  const nota3 = parseFloat(document.getElementById("nota3").value);

  const promedio = calcularPromedio(nota1, nota2, nota3);

  // Función para calcular el promedio de las notas
  function calcularPromedio(nota1, nota2, nota3) {
    return (nota1 + nota2 + nota3) / 3;
  }

  // Logica de evaluacion de notas:

  let mensaje = `Promedio de las notas: ${promedio.toFixed(2)}\n`;
  let suspensos = 0;

  if (nota1 < 5) {
    mensaje += "Nota 1: Suspenso\n";
    suspensos++;
  } else {
    mensaje += "Nota 1: Aprobado\n";
  }

  if (nota2 < 5) {
    mensaje += "Nota 2: Suspenso\n";
    suspensos++;
  } else {
    mensaje += "Nota 2: Aprobado\n";
  }

  if (nota3 < 5) {
    mensaje += "Nota 3: Suspenso\n";
    suspensos++;
  } else {
    mensaje += "Nota 3: Aprobado\n";
  }

  // Logica de calificacion final:
  if (promedio === 10) {
    mensaje += "Calificación final: Matrícula de Honor";
  } else if (promedio >= 9) {
    mensaje += "Calificación final: Sobresaliente";
  } else if (promedio >= 7) {
    mensaje += "Calificación final: Notable";
  } else if (promedio >= 5) {
    mensaje += "Calificación final: Aprobado";
  } else {
    mensaje += "Calificación final: Suspenso";
  }

  // Mostrar el número de suspensos
  mensaje += `\nNúmero de suspensos: ${suspensos}`;

  if (suspensos === 0) {
    mensaje += "\n✅ Puede iniciar su período de prácticas.";
  } else if (suspensos === 1) {
    mensaje += "\n⚠️ Estudiante con asignatura pendiente (bloquea prácticas).";
  } else {
    mensaje += "\n❌ El alumno debe repetir el curso.";
  }

  // Mostrar el mensaje con los resultados
  cajaResultado.textContent = mensaje;
  cajaResultado.className = "visible";
});
