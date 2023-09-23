window.onload = inicializar();

let cafes = [];

function inicializar() {

  let url = "api/cafes/getAll";

  getCafes(url)
    .then(data => {
      cafes = data;

      crearCards();
    })
    .catch(error => {
      console.error("Error al obtener los datos:", error.message);
    })

  document.getElementById("thead").style.display = "none";

  document.getElementById("resumen").style.display = "none";

  document.getElementById("verPaquete").style.display = "none";
  document.getElementById("verGll").style.display = "none";

  let usuario = (localStorage.getItem("usuario") != "" ? JSON.parse(localStorage.getItem("usuario")) : "" );
  

  document.getElementById("nombreUser").textContent = usuario.nombre;

}

async function getCafes(url) {
  try {
    const response = await fetch(url);

    if (!response.ok) {
      throw new Error(`Error de red: ${response.status}`);
    }

    const data = await response.json();

    data.forEach(it => {
      it.cantidad = 0;
    });

    return data;
  } catch (error) {
    console.error(`Error al obtener los datos: ${error.message}`);
    throw error;
  }
}

function crearCards() {

  let cards = "";

  cafes.forEach(item => {

    cards += `
              <div class="crd shadow">
                  <div class="black"></div>
                    <div class="card-overlay">
                      <h4 class="text-uppercase">${item.nombre}</h4>
                      <i class="fa-solid fa-mug-saucer"></i>
                      <p><b>Tamaño: </b><span>${item.tam}</span></p>
                      <p><b>Precio: </b><span>$${item.precio}</span></p>
                      <button type="button" class="btn btn-success btnAgregar" onclick="agregarProducto(${cafes.indexOf(item)})"><i class="fa-solid fa-plus"></i>Agregar</button>
                    </div>
              </div>`;
  });

  document.getElementById("productos").innerHTML = cards;

}

let proSel = {};

function agregarProducto(index) {

  if ([0, 1, 2].includes(index)) {
    document.getElementById("verGll").style.display = "block";
  } else {
    document.getElementById("verPaquete").style.display = "block";
  }

  document.getElementById("resumen").style.display = "block";
  document.getElementById("thead").style.display = "table-header-group";
  document.getElementById("tablaProductos").style.display = "table-header-group";
  // Comprobar si el producto ya está en la lista
  if (proSel[cafes[index].nombre]) {

    proSel[cafes[index].nombre].cantidad++;
    proSel[cafes[index].nombre].galletas += cafes[index].promo.cantGalletas;

  } else {
    proSel[cafes[index].nombre] = {
      precio: cafes[index].precio,
      cantidad: 1,
      tamanio: cafes[index].tam,
      galletas: cafes[index].promo.cantGalletas,
      promo: cafes[index].promo.cantGalletas,
      cafe: cafes[index]
    }
  }

  // Actualiza la tabla
  actualizarTabla();

}

function actualizarTabla() {
  const tblPro = document.getElementById("tablaProductos");
  tblPro.innerHTML = "";

  if (JSON.stringify(proSel) != "{}") {
    // Recorre el registro de productos y agrega filas a la tabla
    for (const prod in proSel) {

      const producto = proSel[prod];

      const fila = document.createElement("tr");

      // Mostrar el producto, precio, cantidad y total
      const cldProducto = document.createElement("td");
      cldProducto.textContent = prod;

      const cldPrecio = document.createElement("td");
      cldPrecio.textContent = `$${producto.precio}`;

      const cldCantidad = document.createElement("td");

      // Crea un campo de entrada (input) para la cantidad
      const cantidadInput = document.createElement("input");
      cantidadInput.type = "number";
      cantidadInput.value = producto.cantidad;
      cantidadInput.classList.add("form-control");
      cantidadInput.min = 0;
      cantidadInput.id = "input" + prod;

      cantidadInput.addEventListener("change", (e) => {
        // Obtiene la cantidad actual
        const nuevaCantidad = parseInt(cantidadInput.value, 10);

        // Calcula la diferencia entre la cantidad anterior y la nueva cantidad
        const diferencia = producto.cantidad - nuevaCantidad;

        producto.cantidad = nuevaCantidad;

        if (diferencia > 0) {
          // La cantidad disminuyó, resta las galletas correspondientes
          if (producto.cantidad >= 0) {
            producto.galletas -= diferencia * producto.promo;
          }
        } else if (diferencia < 0) {
          // La cantidad aumentó, suma las galletas correspondientes
          producto.galletas += Math.abs(diferencia) * producto.promo;
        }

        // Actualiza la cantidad anterior del producto con la nueva cantidad actual
        cantidadAnterior = nuevaCantidad;

        actualizarTabla();

      });

      cldCantidad.appendChild(cantidadInput);

      const celdaTotal = document.createElement("td");
      celdaTotal.textContent = `$${producto.precio * producto.cantidad}`;
      celdaTotal.classList.add("total");

      const celdaAcciones = document.createElement("td");

      // Crea un botón para eliminar el producto
      const botonEliminar = document.createElement("button");
      botonEliminar.textContent = "Eliminar";
      botonEliminar.classList.add("btn", "btn-outline-danger")
      botonEliminar.addEventListener("click", () => {
        // Elimina el producto del registro
        delete proSel[prod];
        actualizarTabla();

        // if (prod == "Café Cappuccino") {
        //   document.getElementById("verPaquete").style.display = "none";
        // }
      });

      celdaAcciones.appendChild(botonEliminar);

      // Agrega las celdas a la fila
      fila.appendChild(cldProducto);
      fila.appendChild(cldPrecio);
      fila.appendChild(cldCantidad);
      fila.appendChild(celdaTotal);
      fila.appendChild(celdaAcciones);

      // Agrega la fila a la tabla
      tblPro.appendChild(fila);
    }

    caclularTotal();
    calcularGalletas();

  } else {

    caclularTotal();
    ocultar();
    
  }
}

function calcularGalletas() {

  let sumaG = 0;
  let sumaP = 0;

  for (const c in proSel) {
    if (c != "Café Cappuccino") {
      sumaG += proSel[c].galletas;
    } else {
      sumaP += proSel[c].galletas;
    }
  }

  if (sumaG == 0) {
    document.getElementById("verGll").style.display = "none";
  }

  if (sumaP == 0) {
    document.getElementById("verPaquete").style.display = "none";
  }

  if (sumaP == 0 && sumaG == 0) {
    document.getElementById("promo").style.display = "none";
  }

  document.getElementById("galletas").innerHTML = sumaG;
  document.getElementById("paquetes").innerHTML = sumaP;

  return [sumaG, sumaP]
}

function caclularTotal() {

  const canTotales = Array.from(document.getElementsByClassName("total"));

  let sumaTotal = 0;

  canTotales.forEach((el) => {

    const texto = el.textContent.replace("$", "");

    sumaTotal += parseFloat(texto);

  });

  document.getElementById("spnTotal").innerHTML = "$" + sumaTotal.toFixed(2)

  return sumaTotal;
}

document.getElementById("btnComprar").addEventListener('click', e => {
  validarCantidades();
});

function validarCantidades() {

  let input = document.querySelectorAll("input");
  let borrar = [];

  input.forEach(ip => {
    if (ip.value == 0) {
      borrar.push(ip.id.replace("input", ""));
    }
  })

  if (borrar != 0) {
    Swal.fire({
      title: 'ERROR',
      text: "Elimina los productos que tienen como cantidad 0." + "\n ¿Borrar automaticamente?",
      icon: 'error',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, borrar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {

        borrar.forEach(it => { delete proSel[it]; });
        actualizarTabla();

        Swal.fire(
          'Preceso realizado',
          'Eliminado con éxito',
          'success'
        );
      }
    });
  } else {
    realizarVenta();
  }

}

function realizarVenta() {
  
  let url = "api/save/venta";

  let productos = [];
  let usuario = localStorage.getItem("usuario");

  for (const it in proSel) {

    const element = proSel[it];

    let promo = {
      id: element.cafe.promo.id,
      tam: element.cafe.promo.tam,
      cantGalletas: element.cafe.promo.cantGalletas
    };

    let cafe = {
      id: element.cafe.id,
      nombre: element.cafe.nombre,
      tam: element.cafe.tam,
      precio: element.cafe.precio,
      promo: promo
    };

    let VentaCafe = {
      cafe: cafe,
      cantidad: element.cantidad,
      precioUnitario: element.precio
    };

    productos.push(VentaCafe);

  }

  let galletas = calcularGalletas();
  let total = caclularTotal();

  let venta = {
    id: 0,
    usuario:  JSON.parse(usuario),
    fecha: "",
    galletas: galletas[0],
    paquetesG: galletas[1],
    total: total
  };

  let datos = {
    venta: venta,
    vc: productos
  };

  let info = {"datos": JSON.stringify(datos)};

  let params = new URLSearchParams(info);

  enviarDatos(url, params)
  .then(res => {
    
    if (res.respuesta) {
      ocultar();
      Swal.fire(
        'ÉXITO',
        res.respuesta,
        'success'
      );
    } else {
      Swal.fire(
        'ERROR',
        res.error,
        'error'
      );
    }

  }).catch(error => {
    console.error(error);
  });

}

async function enviarDatos(url, datos) {
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: datos
    });

    if (!response.ok) {
      throw new Error(`Error de red: ${response.status}`);
    }

    const respuesta = await response.json();

    return respuesta;
  } catch (error) {
    console.error(`Error al enviar datos por POST: ${error.message}`);
    throw error;
  }
}

document.getElementById("btnCerrar").addEventListener('click', cerrar);

function cerrar() {

  let url = "api/acceso/cerrar";

  let usuario = localStorage.getItem("usuario");

  let info = { user: usuario };

  params = new URLSearchParams(info);

  cerrarSesión(url, params)
  .then(res => {
    if (!res.hasOwnProperty("error")) {

      localStorage.setItem("usuario","");
      window.location = "index.html";

    } else {

      Swal.fire(
        'ERROR',
        'Error al cerrar sesión',
        'error'
      );

    }
  }).catch(error=>console.error(error));

}

async function cerrarSesión(url, datos) {
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: datos
    });

    if (!response.ok) {
      throw new Error(`Error de red: ${response.status}`);
    }

    const respuesta = await response.json();


    return respuesta;

  } catch (error) {
    console.error(`Error al enviar datos por POST: ${error.message}`);
    throw error;
  }
}

function ocultar(){
  document.getElementById("thead").style.display = "none";
  document.getElementById("resumen").style.display = "none";

  document.getElementById("verPaquete").style.display = "none";
  document.getElementById("verGll").style.display = "none";
  document.getElementById("tablaProductos").style.display = "none";

  proSel = {};
}


