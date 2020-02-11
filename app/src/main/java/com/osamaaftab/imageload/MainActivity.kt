package com.osamaaftab.imageload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.osamaaftab.flow.Flow
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val array = arrayOf(
        "https://i.pinimg.com/originals/93/09/77/930977991c52b48e664c059990dea125.jpg",
        "https://homepages.cae.wisc.edu/~ece533/images/baboon.png",
        "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
        "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"
    )
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Flow.getInstance(this, 4).displayImage(array[count], image_view, R.drawable.place_holder)
        button.setOnClickListener(View.OnClickListener{
            count++
            Flow.getInstance(this, 4)
                .displayImage(array[count], image_view, R.drawable.place_holder)
            if (count == 3) count = 0
        })
    }
}
