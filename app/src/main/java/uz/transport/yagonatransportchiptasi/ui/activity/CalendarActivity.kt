package uz.transport.yagonatransportchiptasi.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import uz.transport.yagonatransportchiptasi.R
import uz.transport.yagonatransportchiptasi.databinding.ActivityCalendarBinding
import java.time.LocalDate
import java.util.*

/**
 * in this activity, the user selects the time of departure and arrival
 */
class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(this,R.color.white)

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val locationStart = intent.getStringExtra("locationStart")
        val locationEnd = intent.getStringExtra("locationEnd")
        val type = intent.getStringExtra("type")

        binding.ivClose.setOnClickListener { finish() }

        binding.tvAddress.text = "$locationStart - $locationEnd"

        calendar()

        binding.calendarView.onDateClickListener = { date ->

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("day", "${date.dayOfMonth}")
            intent.putExtra("month", "${date.month + 1}")
            intent.putExtra("year", "${date.year}")
            intent.putExtra("type", "${type}")
            Log.d("####", "${date.dayOfMonth}, ${date.year}, ${date.month + 1}")
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    /**
     * this fun calendar control
     */
    private fun calendar() {
        val calendar = Calendar.getInstance()
        val cal = LocalDate.now()

        val month = cal.month.value-1
        val year: Int = cal.year
        val day: Int = cal.dayOfMonth

        calendar.set(year, month, day)
        val initialDate = CalendarDate(calendar.time)

        calendar.set(year, month, day)
        val minDate = CalendarDate(calendar.time)

        calendar.set(year, month + 3, day)
        val maxDate = CalendarDate(calendar.time)

        val firstDayOfWeek = java.util.Calendar.MONDAY

        Log.d("####", firstDayOfWeek.toString())

        binding.calendarView.setupCalendar(
            initialDate = initialDate,
            minDate = minDate,
            maxDate = maxDate,
            selectionMode = CalendarView.SelectionMode.NONE,
            firstDayOfWeek = firstDayOfWeek,
            showYearSelectionView = true
        )
    }
}