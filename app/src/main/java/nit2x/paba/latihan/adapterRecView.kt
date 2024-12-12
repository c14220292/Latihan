package nit2x.paba.latihan

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterRecView (private val listTask: ArrayList<task>) : RecyclerView
.Adapter<adapterRecView.ListViewHolder> () {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNamaTask)
        var _tanggalTask = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _deskripsiTask = itemView.findViewById<TextView>(R.id.tvDeskripsiTask)
        var _statusTask = itemView.findViewById<TextView>(R.id.tvStatus)
        var _btnHapus = itemView.findViewById<Button>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<Button>(R.id.btnEdit)
        var _btnStart = itemView.findViewById<Button>(R.id.btnStart)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:task)
        fun delData(pos: Int)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val task = listTask[position]

        holder._namaTask.text = task.nama
        holder._tanggalTask.text = task.tanggal
        holder._deskripsiTask.text = task.deskripsi
        holder._statusTask.text = task.status

        if (task.status == "Start") {
            holder._btnStart.text = "SELESAI"
        } else {
            holder._btnStart.text = "START"
        }

        holder._btnHapus.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Hapus Data")
                .setMessage("Apakah Anda yakin ingin menghapus data '${task.nama}'?")
                .setPositiveButton("Hapus") { _, _ ->
                    listTask.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, listTask.size)
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        holder._btnEdit.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, detTask::class.java)
            intent.putExtra("TASK_POSITION", position)
            intent.putExtra("TASK_DATA", task)
            (context as Activity).startActivityForResult(intent, MainActivity.REQUEST_EDIT)
        }

        holder._btnStart.setOnClickListener {
            if (task.status == "Start") {
                task.status = "Selesai"
                holder._statusTask.text = task.status
                holder._btnStart.text = "START"
                Toast.makeText(holder.itemView.context, "Task '${task.nama}' selesai", Toast.LENGTH_SHORT).show()
            } else {
                task.status = "Start"
                holder._statusTask.text = task.status
                holder._btnStart.text = "SELESAI"
                Toast.makeText(holder.itemView.context, "Task '${task.nama}' dimulai", Toast.LENGTH_SHORT).show()
            }
            notifyItemChanged(position)
        }
    }



    override fun getItemCount(): Int {
        return listTask.size
    }
}