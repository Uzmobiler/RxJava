package uz.pdp.handleronline.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import uz.pdp.handleronline.entity.User

@Dao
interface UserDao {

    @Insert
    fun addUser(user: User)

    @Query("select * from users")
    fun getAllUsers(): Flowable<List<User>>
}