package ar.com.unq.eis.trainup.controller.Exceptions

class UsuarioDuplicadoException(username: String) : RuntimeException("Ya existe un usuario con username: $username")