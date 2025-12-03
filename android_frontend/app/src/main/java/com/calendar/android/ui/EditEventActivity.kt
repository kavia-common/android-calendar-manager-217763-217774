package com.calendar.android.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.calendar.android.data.EventRequest
import com.calendar.android.data.EventsRepository
import com.calendar.android.databinding.ActivityEditEventBinding
import kotlinx.coroutines.launch

class EditEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEventBinding
    private val repo = EventsRepository()
    private var eventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getStringExtra("eventId")

        binding.btnSave.setOnClickListener {
            val req = EventRequest(
                title = binding.etTitle.text?.toString()?.trim().orEmpty(),
                description = binding.etDescription.text?.toString(),
                startTime = binding.etStart.text?.toString()?.trim().orEmpty(),
                endTime = binding.etEnd.text?.toString()?.trim().orEmpty(),
                location = binding.etLocation.text?.toString(),
                allDay = binding.cbAllDay.isChecked,
                recurrenceRule = binding.etRrule.text?.toString()
            )
            lifecycleScope.launch {
                val result = if (eventId == null) repo.create(req) else repo.update(eventId!!, req)
                result.onSuccess {
                    Toast.makeText(this@EditEventActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }.onFailure {
                    Toast.makeText(this@EditEventActivity, "Error saving", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            val id = eventId ?: return@setOnClickListener
            lifecycleScope.launch {
                val res = repo.delete(id)
                res.onSuccess {
                    Toast.makeText(this@EditEventActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }.onFailure {
                    Toast.makeText(this@EditEventActivity, "Error deleting", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
