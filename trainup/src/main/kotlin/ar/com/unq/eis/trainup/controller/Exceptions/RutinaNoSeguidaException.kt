package ar.com.unq.eis.trainup.controller.Exceptions

class RutinaNoSeguidaException(rutinaID: String, username: String) : RuntimeException("El usuario: $username no sigue a rutina $rutinaID") {
}