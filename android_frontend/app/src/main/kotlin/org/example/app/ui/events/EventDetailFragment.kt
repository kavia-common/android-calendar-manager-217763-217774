package org.example.app.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * Shows details for a single event.
 */
class EventDetailFragment : Fragment() {

    companion object {
        fun newInstance(eventId: String): EventDetailFragment {
            val f = EventDetailFragment()
            f.arguments = Bundle().apply { putString("event_id", eventId) }
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }
}
