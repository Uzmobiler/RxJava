package uz.pdp.handleronline.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User {

    @PrimaryKey
    var id: Int? = null
    var username: String? = null
    var password: String? = null

}
