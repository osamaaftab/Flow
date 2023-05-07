package com.osamaaftab.imageload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.osamaaftab.flow.Flow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val array = arrayOf(
        "https://i.pinimg.com/originals/93/09/77/930977991c52b48e664c059990dea125.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo7WfE6wFfdpeFph92LdEFJFnula0ecIObiQ&usqp=CAU"
    )
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Flow.getInstance(this, 4).displayImage(array[count], image_view, R.drawable.place_holder)
        button.setOnClickListener {
            count++
            Flow.getInstance(this, 4)
                .displayImage(array[count], image_view, R.drawable.place_holder)
            if (count == array.size - 1) count = 0
        }
    }
}
