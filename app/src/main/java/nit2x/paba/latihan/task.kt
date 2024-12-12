package nit2x.paba.latihan

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class task(
    var nama : String,
    var tanggal : String,
    var deskripsi : String,
    var status : String
) : Parcelable