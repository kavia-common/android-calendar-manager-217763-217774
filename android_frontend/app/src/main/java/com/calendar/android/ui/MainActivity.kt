package com.calendar.android.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.calendar.android.R
import com.calendar.android.data.EventResponse
import com.calendar.android.data.EventsRepository
import com.calendar.android.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repo = EventsRepository()
    private var selectedDate: LocalDate = LocalDate.now()
    private val adapter = EventsAdapter { event ->
        val intent = Intent(this, EditEventActivity::class.java)
        intent.putExtra("eventId", event.id)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eventList.layoutManager = LinearLayoutManager(this)
        binding.eventList.adapter = adapter

        setupTabs()
        setupMonthGrid()
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditEventActivity::class.java)
            startActivity(intent)
        }

        loadEventsForSelectedDay()
    }

    private fun setupTabs() {
        val tabs = binding.tabLayout
        tabs.addTab(tabs.newTab().setText("Month"))
        tabs.addTab(tabs.newTab().setText("Week"))
        tabs.addTab(tabs.newTab().setText("Day"))
        tabs.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                // For baseline, keep month view visible for all
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
        })
    }

    private fun setupMonthGrid() {
        val grid = findViewById<GridLayout>(R.id.gridCalendar)
        val tvMonth = findViewById<TextView>(R.id.tvMonthTitle)
        renderMonth(grid, tvMonth, selectedDate)
    }

    private fun renderMonth(grid: GridLayout, tvMonth: TextView, baseDate: LocalDate) {
        grid.removeAllViews()
        tvMonth.text = baseDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))

        // Weekday headers
        val weekdays = listOf("Sun","Mon","Tue","Wed","Thu","Fri","Sat")
        for (i in 0 until 7) {
            val tv = TextView(this)
            tv.text = weekdays[i]
            tv.gravity = Gravity.CENTER
            tv.setPadding(4,4,4,4)
            grid.addView(tv)
        }

        val firstOfMonth = baseDate.withDayOfMonth(1)
        val firstDayOfGrid = firstOfMonth.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY))
        val lastOfMonth = baseDate.with(TemporalAdjusters.lastDayOfMonth())
        val lastDayOfGrid = lastOfMonth.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SATURDAY))

        var cur = firstDayOfGrid
        while (!cur.isAfter(lastDayOfGrid)) {
            val tv = TextView(this)
            tv.text = cur.dayOfMonth.toString()
            tv.gravity = Gravity.CENTER
            tv.setPadding(12, 16, 12, 16)
            tv.setOnClickListener {
                selectedDate = cur
                loadEventsForSelectedDay()
            }
            if (cur.month != baseDate.month) {
                tv.alpha = 0.5f
            }
            grid.addView(tv)
            cur = cur.plusDays(1)
        }
    }

    private fun loadEventsForSelectedDay() {
        // Build ISO range for the selected day
        val start = selectedDate.atStartOfDay().atOffset(ZoneOffset.UTC)
        val end = selectedDate.plusDays(1).atStartOfDay().minusNanos(1).atOffset(ZoneOffset.UTC)

        val isoFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val startIso = start.format(isoFormatter)
        val endIso = end.format(isoFormatter)

        // Show loading
        binding.eventList.visibility = View.INVISIBLE

        lifecycleScope.launch {
            val result = repo.listInRange(startIso, endIso)
            result.onSuccess { list ->
                adapter.submit(list)
            }.onFailure {
                adapter.submit(emptyList())
            }
            binding.eventList.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        loadEventsForSelectedDay()
    }
}

class EventsAdapter(private val onClick: (EventResponse) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<EventsAdapter.VH>() {

    private val items = mutableListOf<EventResponse>()
    fun submit(list: List<EventResponse>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val time: TextView = view.findViewById(R.id.tvTime)
        val location: TextView = view.findViewById(R.id.tvLocation)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): VH {
        val v = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ev = items[position]
        holder.title.text = ev.title
        holder.time.text = "${ev.startTime} - ${ev.endTime}"
        holder.location.text = ev.location ?: ""
        holder.itemView.setOnClickListener { onClick(ev) }
    }

    override fun getItemCount(): Int = items.size
}
