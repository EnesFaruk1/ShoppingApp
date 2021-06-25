package mobi.appcent.shoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import mobi.appcent.shoppingapp.R
import mobi.appcent.shoppingapp.viewmodel.MapViewModel

class MapFragment : Fragment(),OnMapReadyCallback {

    private lateinit var viewModel: MapViewModel
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val args: MapFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.let {
            it.findFragmentById(R.id.map) as SupportMapFragment
        }
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        activity?.ivBackIcon?.setOnClickListener {
            val action = MapFragmentDirections.actionMapFragmentToProductDetailFragment(Integer.parseInt(args.data.productId.toString()))
            findNavController().navigate(action)
            Navigation.findNavController(requireView()).popBackStack(
                R.id.mapFragment, true)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val lat = args.data.latitude?.toDouble()
        val lng = args.data.longitude?.toDouble()
        val zoomLevel = 15f

        val latLng =  LatLng(lat!!,lng!!)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomLevel))
        map.addMarker(MarkerOptions()
            .position(latLng)
            .title("Shop Location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))
        map.uiSettings.isZoomControlsEnabled = true

    }
}

