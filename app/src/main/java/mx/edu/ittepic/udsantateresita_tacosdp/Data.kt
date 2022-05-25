package mx.edu.ittepic.udsantateresita_tacosdp

import com.google.firebase.firestore.GeoPoint

class Data {
    var nombre : String = ""
    var descripcion : String = ""
    var posicion1 : GeoPoint = GeoPoint(0.0,0.0)
    var posicion2: GeoPoint = GeoPoint(0.0,0.0)

    override fun toString(): String {
        return nombre+"\n"+posicion1.latitude+","+posicion1.longitude+"\n"+
                posicion2.latitude+","+posicion2.longitude+""
    }
}