/*
 * Copyright (c) 2020 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.kotlincoroutinesfundamentals

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainLooper = mainLooper // or Looper.getMainLooper()
        // creating a thread to move the work to background and passing in a runnable
        // a runnable is simply an interface which defines a piece of code which you can run and it usually specific to threqds
        // the Global coroutine lives as long as your app does
        Log.d("TaskThread:", Thread.currentThread().name)
        GlobalScope.launch(context = Dispatchers.IO) {
            Log.d("TaskThread:", Thread.currentThread().name)
            // a constant that holds the link to image of the awl to be displayed
            val imageUrl = URL("https://wallpaperplay.com/walls/full/1/c/7/38027.jpg")
            // opening a connection to the link an casting it to an Http connection
            val connection = imageUrl.openConnection() as HttpURLConnection
            // this creates an input connection for recieving the data
            connection.doInput = true
            connection.connect()
            // we transfer the data from the link to my app by using an input stream, decoding it into a bitmap
            val inputStream = connection.inputStream
            // taking the input stream and storing them in a value and then decoding them using the bitmap factory
            val bitmap = BitmapFactory.decodeStream(inputStream)
            // the bit map is then display
            /*to display the image we need to post it to the UI thread using "runOnUiThread" which is only accessible by the activities and cannot be called from anywhere
             second way is to use a handler and a main thread looper; Loopers loop through messages any thread receives and processes those messages
             the main thread looper handles messages for the main thread
             handlers are used to send messages to the looper*/
            // in other to post this call to the ui you have to use a nested coroutine

            launch(Dispatchers.Main) {
                Log.d("TaskThread:", Thread.currentThread().name)
                image.setImageBitmap(bitmap) }
        }

//        Thread(
//            Runnable {
//                // a constant that holds the link to image of the awl to be displayed
//                val imageUrl = URL("https://wallpaperplay.com/walls/full/1/c/7/38027.jpg")
//                // opening a connection to the link an casting it to an Http connection
//                val connection = imageUrl.openConnection() as HttpURLConnection
//                // this creates an input connection for recieving the data
//                connection.doInput = true
//                connection.connect()
//                // we transfer the data from the link to my app by using an input stream, decoding it into a bitmap
//                val inputStream = connection.inputStream
//                // taking the input stream and storing them in a value and then decoding them using the bitmap factory
//                val bitmap = BitmapFactory.decodeStream(inputStream)
//                // the bit map is then display
//                /*to display the image we need to post it to the UI thread using "runOnUiThread" which is only accessible by the activities and cannot be called from anywhere
//                 second way is to use a handler and a main thread looper; Loopers loop through messages any thread receives and processes those messages
//                 the main thread looper handles messages for the main thread
//                 handlers are used to send messages to the looper*/
//                Handler(mainLooper).post { image.setImageBitmap(bitmap) }
//                // runOnUiThread { image.setImageBitmap(bitmap) } // if all the above is done in the main thread it will cause the main thread to have problem
//            }
//        ).start()
//    }
    }
}
