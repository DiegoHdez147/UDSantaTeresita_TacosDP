package mx.edu.ittepic.udsantateresita_tacosdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.ittepic.udsantateresita_tacosdp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var listaIDs = ArrayList<String>()

    var baseRemota = FirebaseFirestore.getInstance()
    var posicion = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        FirebaseFirestore.getInstance()
            .collection("udsantateresita")
            .addSnapshotListener { query, error ->
                //query tiene muchos documento que es equivalente al cursor del sqlite
                if (error != null) {
                    //SI UBO ERROR!
                    AlertDialog.Builder(this)
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }

                val arreglo = ArrayList<String>()
                listaIDs.clear()
                //plataforma como servicio
                for (documento in query!!) {
                    var cadena = "nombre : ${documento.getString("nombre")}\n" +
                            "descripcion : ${documento.getString("descripcion")}\n" +
                            "posicion1 : ${documento.getGeoPoint("posicion1")}\n" +
                            "posicion2 : ${documento.getGeoPoint("posicion2")}"
                    arreglo.add(cadena)
                    listaIDs.add(documento.id)
                }
                binding.informacion.adapter =
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo)

            }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val estadiOlimpico = LatLng(21.526083, -104.901167)
        mMap.addMarker(MarkerOptions().position(estadiOlimpico).title("ESTADIO OLIMPICO"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(estadiOlimpico))
        mMap.uiSettings.isZoomControlsEnabled=true
        val v = 16.5
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(estadiOlimpico,v.toFloat()))
    }
}