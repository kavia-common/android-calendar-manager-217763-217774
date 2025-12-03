package org.example.app.ui.events

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import org.example.app.R
import org.example.app.data.ApiClient
import org.example.app.data.Event
import java.time.OffsetDateTime

/**
 * PUBLIC_INTERFACE
 * Dialog for creating or editing an event.
 */
class EventEditFragment : DialogFragment() {

    companion object {
        fun newInstance(event: Event?): EventEditFragment {
            val f = EventEditFragment()
            f.arguments = Bundle().apply { putString("event_id", event?.id) }
            return f
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v: View = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_event_edit, null, false)

        val inputTitle = v.findViewById<EditText>(R.id.inputTitle)
        val inputDescription = v.findViewById<EditText>(R.id.inputDescription)
        val inputStart = v.findViewById<EditText>(R.id.inputStart)
        val inputEnd = v.findViewById<EditText>(R.id.inputEnd)
        val checkAllDay = v.findViewById<CheckBox>(R.id.checkAllDay)
        val inputLocation = v.findViewById<EditText>(R.id.inputLocation)
        val inputColor = v.findViewById<EditText>(R.id.inputColor)
        val btnSave = v.findViewById<Button>(R.id.btnSave)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_event)
            .setView(v)
            .create()

        btnSave.setOnClickListener {
            val event = Event(
                id = null,
                title = inputTitle.text?.toString()?.trim().orEmpty(),
                description = inputDescription.text?.toString()?.trim().orEmpty(),
                startTime = inputStart.text?.toString()?.trim().orEmpty(),
                endTime = inputEnd.text?.toString()?.trim().orEmpty(),
                allDay = checkAllDay.isChecked,
                location = inputLocation.text?.toString()?.trim().orEmpty(),
                colorTag = inputColor.text?.toString()?.trim().orEmpty()
            )
            // Fire and forget for now; production should handle lifecycle + coroutine scope
            ApiClient.instance.eventsService.createEvent(event)
                .enqueue(object : retrofit2.Callback<Event> {
                    override fun onResponse(call: retrofit2.Call<Event>, response: retrofit2.Response<Event>) {
                        dismissAllowingStateLoss()
                    }
                    override fun onFailure(call: retrofit2.Call<Event>, t: Throwable) {
                        dismissAllowingStateLoss()
                    }
                })
        }

        return dialog
    }
}
