package nit2x.paba.latihan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _rvTask = findViewById<RecyclerView>(R.id.rvDaftarTask)
        SiapkanData()

        TambahData()
        TampilkanData()


        val btnTambahTask = findViewById<Button>(R.id.btnAddTask)
        btnTambahTask.setOnClickListener {
            val intent = Intent(this, detTask::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_ADD -> {
                    val newTask = task(
                        data.getStringExtra("nama") ?: "",
                        data.getStringExtra("tanggal") ?: "",
                        data.getStringExtra("deskripsi") ?: "",
                        "Pending"
                    )
                    arTask.add(newTask)
                    adapterTask.notifyItemInserted(arTask.size - 1)
                }
                REQUEST_EDIT -> {
                    val position = data.getIntExtra("TASK_POSITION", -1)
                    val updatedTask = task(
                        data.getStringExtra("nama") ?: "",
                        data.getStringExtra("tanggal") ?: "",
                        data.getStringExtra("deskripsi") ?: "",
                        "Pending"
                    )

                    if (position != -1) {
                        arTask[position] = updatedTask
                        adapterTask.notifyItemChanged(position)
                    }
                }
            }
        }
    }

    private lateinit var _nama : MutableList<String>
    private lateinit var _tanggal : MutableList<String>
    private lateinit var _deskripsi : MutableList<String>
    private lateinit var _status : MutableList<String>

    private var arTask = arrayListOf<task>()
    private lateinit var _rvTask : RecyclerView
    private lateinit var adapterTask: adapterRecView

    fun SiapkanData(){
        _nama = resources.getStringArray(R.array.namaTask).toMutableList()
        _deskripsi = resources.getStringArray(R.array.deskripsiTask).toMutableList()
        _tanggal = resources.getStringArray(R.array.tanggalTask).toMutableList()
        _status = resources.getStringArray(R.array.statusTask).toMutableList()
    }

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }



    fun TambahData(){
        arTask.clear()
        for(position in _nama.indices){
            val data = task(
                _nama[position],
                _tanggal[position],
                _deskripsi[position],
                _status[position]
            )
            arTask.add(data)
        }
    }

    fun TampilkanData(){
        _rvTask.layoutManager = LinearLayoutManager(this)

        adapterTask = adapterRecView(arTask)
        _rvTask.adapter = adapterTask

        adapterTask.setOnItemClickCallback(object : adapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: task) {
                Toast.makeText(this@MainActivity, "Clicked: ${data.nama}", Toast.LENGTH_LONG).show()
                val intent = Intent(this@MainActivity, detTask::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                _nama.removeAt(pos)
                _tanggal.removeAt(pos)
                _deskripsi.removeAt(pos)
                _status.removeAt(pos)
            }


        })
    }

}