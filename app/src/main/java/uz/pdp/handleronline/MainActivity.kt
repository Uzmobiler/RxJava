package uz.pdp.handleronline

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import uz.pdp.handleronline.adapters.UserAdapter
import uz.pdp.handleronline.databinding.ActivityMainBinding
import uz.pdp.handleronline.db.AppDatabase
import uz.pdp.handleronline.entity.User

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var appDatabase: AppDatabase
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        userAdapter = UserAdapter()

        binding.button.setOnClickListener {
            val user = User()
            user.username = binding.edit1.text.toString()
            user.password = binding.edit2.text.toString()

            Observable.fromCallable {
                appDatabase.userDao().addUser(user)
            }.subscribe {
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
            }
        }

        appDatabase.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>> {
                override fun accept(t: List<User>?) {
                    userAdapter.submitList(t)
                    binding.progressBar.visibility = View.GONE
                }
            }, object : Consumer<Throwable> {
                override fun accept(t: Throwable?) {

                }
            })
        binding.rv.adapter = userAdapter

    }
}