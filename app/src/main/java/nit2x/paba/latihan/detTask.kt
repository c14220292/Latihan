package nit2x.paba.latihan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detTask : AppCompatActivity() {

    private var editMode = false
    private var taskPosition: Int = -1
    private var taskData: task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_det_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (intent.hasExtra("TASK_DATA")) {
            editMode = true
            taskPosition = intent.getIntExtra("TASK_POSITION", -1)
            taskData = intent.getSerializableExtra("TASK_DATA") as? task

            findViewById<EditText>(R.id.etNama).setText(taskData?.nama)
            findViewById<EditText>(R.id.etTanggal).setText(taskData?.tanggal)
            findViewById<EditText>(R.id.etLongInputDeskripsi).setText(taskData?.deskripsi)
        }

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        btnSimpan.setOnClickListener {
            val nama = findViewById<EditText>(R.id.etNama).text.toString()
            val tanggal = findViewById<EditText>(R.id.etTanggal).text.toString()
            val deskripsi = findViewById<EditText>(R.id.etLongInputDeskripsi).text.toString()

            if (nama.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra("nama", nama)
                intent.putExtra("tanggal", tanggal)
                intent.putExtra("deskripsi", deskripsi)

                if (editMode) {
                    intent.putExtra("TASK_POSITION", taskPosition)
                }

                setResult(RESULT_OK, intent)
                finish()
            }
        }


    }
}