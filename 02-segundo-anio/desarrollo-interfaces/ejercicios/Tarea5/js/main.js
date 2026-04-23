const menuToggleBtn = document.getElementById("menu-toggle");
const navList = document.getElementById("nav-list");

menuToggleBtn.addEventListener("click", () => {
  navList.classList.toggle("open");
});
