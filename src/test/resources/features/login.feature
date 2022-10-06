@login
Feature: Verificacion de login exitoso y fallido

  @NegativeTest
  Scenario Outline: Verificar que no se pueda ingresar con usuario o contrasenia incorrecto
    Given Voy a abrir la aplicacion de ventas en un dispositivo "ios"
    When Me logeo con usuario <usuario> y contrasenia <contrasenia>
    Then No deberia logearse y debe mostrar el siguiente mensaje "<mensaje>"

    Examples: datos para ingresar a la aplicacion de ventas
      | usuario | contrasenia | mensaje                                      |
      | fflores | 123456      | Acceso denegado. Usuario o clave incorrecta. |
      | fflores |             | Acceso denegado. Usuario o clave incorrecta. |
      |         | 123456      | Acceso denegado. Usuario o clave incorrecta. |
      |         |             | Acceso denegado. Usuario o clave incorrecta. |
