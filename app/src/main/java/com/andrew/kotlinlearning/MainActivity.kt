package com.andrew.kotlinlearning

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnClick {
    override fun isClick(pos: Int) {
        toast("No : " + pos)
    }

    val image: IntArray =
        intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5)
    val name = arrayOf("Chaeyoung", "Chaeyoung", "Chaeyoung", "Chaeyoung", "Chaeyoung")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVar()
    }

    private fun initVar() {
        val api = ApiClient.getApiClient()
        val list = ArrayList<Model>()
        val recycler: RecyclerView = findViewById(R.id.recyclerview)

        for (i in 0..image.size - 1) {
            list.add(Model(decodeSampleFromSource(resources, image.get(i), 100, 100), name.get(i) + i, ""))
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = RecyclerAdapter(this, list, this)

        val name: EditText = findViewById(R.id.edittext_username)
        val password: EditText = findViewById(R.id.edittext_password)
        val register: Button = findViewById(R.id.btn_register)
        val login: Button = findViewById(R.id.btn_login)

        register.setOnClickListener {
            if (name.text.isEmpty()) {
                toast("Dont empty this field")
            } else if (password.text.isEmpty()) {
                toast("Dont empty this field")
            } else {
                val call = api.sendData("register", name.text.toString(), password.text.toString())
                call.enqueue(object : Callback<Model> {
                    override fun onFailure(call: Call<Model>, t: Throwable) {
                        toast("Failed1")
                    }

                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        val model: Model = response.body()!!
                        if (model.response.equals("success")) {
                            toast("Success")
                            name.text.clear()
                            password.text.clear()
                        } else {
                            toast("Failed2")
                        }
                    }
                })
            }
        }

        login.setOnClickListener {
            if (name.text.isEmpty()) {
                toast("Dont empty this field")
            } else if (password.text.isEmpty()) {
                toast("Dont empty this field")
            } else {
                val call = api.verifyEmail("verify", name.text.toString(), password.text.toString())
                call.enqueue(object : Callback<Model> {
                    override fun onFailure(call: Call<Model>, t: Throwable) {
                        toast("failed")
                    }
                    override fun onResponse(call: Call<Model>, response: Response<Model>) {
                        val model: Model = response.body()!!
                        if (model.response.equals("verify")) {
                            toast("Account is match")
                        } else {
                            toast("Password / Username is wrong")
                        }
                    }
                })
            }
        }
    }

    fun decodeSampleFromSource(res: Resources, image: Int, w: Int, h: Int): Bitmap {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

//        options.inJustDecodeBounds = true

        BitmapFactory.decodeResource(resources, image, options)
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inSampleSize = calculateInSampleSize(options, w, h)

        options.inJustDecodeBounds = false

        return BitmapFactory.decodeResource(res, image, options)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
