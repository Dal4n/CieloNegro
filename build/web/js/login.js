function mostrarContrasena() {
    const passwordInput = document.getElementById('password');
    const eye = document.getElementById("eye");
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        eye.classList.remove("fa-solid", "fa-eye");
        eye.classList.add("fa-solid", "fa-eye-slash");
    } else {
        passwordInput.type = 'password';
        eye.classList.remove("fa-solid", "fa-eye-slash");
        eye.classList.add("fa-solid", "fa-eye");
    }
}

document.getElementById("btnIngresar").addEventListener('click', login);


function login() {

    let user = document.getElementById("user").value;
    let pass = document.getElementById("password").value;
    let params;

    let error = {
        user: false,
        pass: false
    };

    error["user"] = (user == "");
    error["pass"] = (pass == "");
    
    if (error["user"] || error["pass"]) {
        if (error["user"] && error["pass"]) {
            Swal.fire(
                'Faltan Datos',
                'Ingresa usuario y contraseña',
                'error'
            );
        } else {
            if (error["user"]) {
                Swal.fire(
                    'Faltan Datos',
                    'Ingresa un usuario',
                    'error'
                );
            } else if (error["pass"]) {
                Swal.fire(
                    'Faltan Datos',
                    'Ingresa una contraseña',
                    'error'
                );
            }
        }

    } else {

        let datos = {
            user: user,
            pass: pass
        };
        
        let info = {datos: JSON.stringify(datos)};
        
        let url = "api/acceso/login";
        
        params = new URLSearchParams(info);

        enviarDatos(url, params).then(res =>{
            if (!res.hasOwnProperty("acceso")){
                localStorage.setItem("usuario",JSON.stringify(res));
                window.location = "dashboard.html";
            } else {
                Swal.fire(
                    'ERROR DE CREDENCIALES',
                    res.acceso,
                    'error'
                );
            }
        });
    }


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
