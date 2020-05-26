package info.chirikualii.alarmworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = OneTimeWorkRequest.Builder(MyWorker::class.java).build()


        btn_worker.setOnClickListener {
            WorkManager.getInstance(this).enqueue(request)
        }
    }
}
