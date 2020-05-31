package info.chirikualii.alarmworkmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

    }

    private fun setupView() {

        val calendar = Calendar.getInstance()
        var timemillisWork : Long = 0



        val dateFormatStr = "MM/dd/yy"
        val sdfDate = SimpleDateFormat(dateFormatStr)

        val fullDateFormatStr = "MM/dd/yy, hh:mm"
        val sdfFullDate = SimpleDateFormat(fullDateFormatStr)

        val date = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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
                    var convertHour =""
                    var convertMinute = ""

                    convertHour = if(hourOfDay<=9){
                        "0${hourOfDay}"
                    } else{
                        hourOfDay.toString()
                    }

                    convertMinute = if(minute<=9){
                        "0${minute}"
                    }else{
                        minute.toString()
                    }

                    val fullDate = "${sdfDate.format(calendar.time)}, ${convertHour}:${convertMinute}"
                    timemillisWork = sdfFullDate.parse(fullDate).time

                    Log.d(MainActivity::class.java.simpleName,"full date :${fullDate}")
                    Log.d(MainActivity::class.java.simpleName,"timemillis :${timemillisWork}")
                }
                ,hour,minute,true).show()

        }
        btn_worker.setOnClickListener {
            val delay = timemillisWork - System.currentTimeMillis()
            val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
                .setInitialDelay(delay,TimeUnit.MILLISECONDS)
                .build()
            WorkManager.getInstance(this)
                .beginUniqueWork("HeyWork",ExistingWorkPolicy.REPLACE,request)
                .enqueue()
            Toast.makeText(this,"worker successfully",Toast.LENGTH_SHORT).show()
        }
    }
}
