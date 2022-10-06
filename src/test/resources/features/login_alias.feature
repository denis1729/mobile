@loginAlias
Feature: Verificacion de login exitoso y fallido con alias

  @loginAlias
  Scenario: Verificar que no se pueda ingresar con usuario con alias
    Given Voy a abrir la aplicacion de ventas en un dispositivo "android"
    When Me logeo con un usuario como "funcionario"
    Then No deberia logearse y debe mostrar el siguiente mensaje "Acceso denegado. Usuario o clave incorrecta."
