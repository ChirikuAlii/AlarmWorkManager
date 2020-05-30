package info.chirikualii.alarmworkmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

    }

    private fun setupView() {
        val request = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        val calendar = Calendar.getInstance()
        val dateFormat = "MM/dd/yy"
        val sdf = SimpleDateFormat(dateFormat)

        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Toast.makeText(this,"date :${sdf.format(calendar.time)}",Toast.LENGTH_SHORT).show()
        }

        val time = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        }

        val datePicker = DatePickerDialog(this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

        btn_date.setOnClickListener {
            datePicker.show()
        }

        btn_time.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val hour = currentTime.get(Calendar.HOUR_OF_DAY)
            val minute = currentTime.get(Calendar.MINUTE)
            TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    var hours =""
                    var minutes = ""
                    if(hourOfDay<=9 ){
                        hours= "0${hourOfDay}"

                    }else{
                        hours = hourOfDay.toString()

                    }

                    if(minute<=9){
                        minutes = "0${minute}"
                    }else{

                        minutes = minute.toString()
                    }


                    Toast.makeText(this@MainActivity,"date :${sdf.format(calendar.time)} ,time: ${hours}:${minutes}",Toast.LENGTH_SHORT).show()
                }
                ,hour,minute,true).show()

        }
        btn_worker.setOnClickListener {
            WorkManager.getInstance(this).enqueue(request)
        }
    }
}
