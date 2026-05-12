const boton1 = document.getElementById("btn1");
const boton2 = document.getElementById("btn2");
const boton3 = document.getElementById("btn3");
const boton4 = document.getElementById("btn4");
const boton5 = document.getElementById("btn5");
const boton6 = document.getElementById("btn6");
const boton7 = document.getElementById("btn7");
const boton8 = document.getElementById("btn8");

boton1.addEventListener("click", function () {
  alert(
    "Respuesta: JavaScript es un lenguaje de programación interpretado que añade interactividad, lógica y dinamismo a las páginas web.",
  );
});

boton2.addEventListener("click", function () {
  alert(
    "Respuesta: var tiene alcance de función y permite el hoisting (más propenso a errores). let tiene alcance de bloque {} y es la forma moderna y segura de declarar variables.",
  );
});

boton3.addEventListener("click", function () {
  alert(
    "Respuesta: El DOM (Document Object Model) es la representación en árbol de tu HTML. JS interactúa con él para leer, crear o modificar elementos de la página en tiempo real.",
  );
});

boton4.addEventListener("click", function () {
  alert(
    "Respuesta: String (texto), 2. Number (números), 3. Boolean (true/false), 4. Undefined (sin asignar), 5. Null (vacío intencional).",
  );
});

boton5.addEventListener("click", function () {
  alert(
    "Respuesta: Es un bloque de código reutilizable diseñado para realizar una tarea específica. Se utiliza para no repetir código y mantener la lógica organizada.",
  );
});

boton6.addEventListener("click", function () {
  alert(
    "Respuesta: Tradicionalmente usando la etiqueta <script src='ruta_archivo.js'></script> en el HTML, o usando la palabra clave 'import' en JS moderno.",
  );
});

boton7.addEventListener("click", function () {
  alert(
    "Respuesta: Se enlaza al final para asegurar que todo el HTML (el DOM) ya está renderizado. Si se pone en el head, JS intentaría buscar elementos que aún no existen y daría error",
  );
});

boton8.addEventListener("click", function () {
  alert(
    "Respuesta: async descarga y ejecuta el script inmediatamente en segundo plano. defer descarga en segundo plano pero ESPERA a que el HTML cargue por completo para ejecutarse.",
  );
});
