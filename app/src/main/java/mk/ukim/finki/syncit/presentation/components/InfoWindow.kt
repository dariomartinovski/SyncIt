import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import mk.ukim.finki.syncit.R
import mk.ukim.finki.syncit.data.model.Event
import mk.ukim.finki.syncit.data.model.Venue
import mk.ukim.finki.syncit.utils.toSimpleFormat

class CustomInfoWindow(
    mapView: MapView,
    private val event: Event,
    private val venue: Venue,
    private val navController: NavController
) : InfoWindow(R.layout.custom_info_window, mapView) {

    override fun onOpen(item: Any?) {
        val marker = item as Marker
        val view = this.mView

        val titleView: TextView = view.findViewById(R.id.eventTitle)
        val startTimeView: TextView = view.findViewById(R.id.eventStartTime)
        val venueTitleView: TextView = view.findViewById(R.id.venueTitle)

        titleView.text = event.title
        startTimeView.text = "Start Time: ${event.startDateTimeParsed.toSimpleFormat()}"
        venueTitleView.text = "Venue: ${venue.title}"

        val buyTicketsButton: Button = view.findViewById(R.id.buyTicketsButton)
        val viewDetailsButton: Button = view.findViewById(R.id.viewDetailsButton)

        buyTicketsButton.setOnClickListener {
            navController.navigate("buyTickets/${event.id}")
        }

        viewDetailsButton.setOnClickListener {
            navController.navigate("eventDetails/${event.id}")
        }
    }


    override fun onClose() {
    }
}