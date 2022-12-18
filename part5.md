
For `AndroidManifest.xml` file, I removed the following calls for location privacy reasons 
```
<!-- Part 5: Removing for privacy reasons <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION"/> -->
```

For `UserInfo.kt`, I commented out the following privacy invasive measures: 
```
interface UserInfo {
  //  PART 5 - removing for privacy reason, don't wanna log my token in metrics POST call. @POST("/api/metrics")
   // fun postInfo(@Body info: UserInfoContainer, @Header("Authorization") token: String?) : Call<User>
}
```

For `CardScrollingActivity.kt` file, I commented out the following privacy invasive funcions which relate to location and sensor details sharing. Kevin also confirmed that you may not need sensors in his lecture. Accordingly the updated file looks as follows: 
``` 
class CardScrollingActivity : AppCompatActivity(), SensorEventListener, LocationListener {
    private var loggedInUser : User? = null
    private lateinit var sensorManager: SensorManager
    private var mAccel: Sensor? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationPermissionCode = 2
        // Part 5 - privacy invasive    var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        // Part 5 - privacy invasive    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        loggedInUser = intent.getParcelableExtra<User>("User")
        var button : Button = findViewById<Button>(R.id.view_cards_button)
        button.text = "View Products"
        button.setOnClickListener {
            val intent = Intent(this, ProductScrollingActivity::class.java).apply{
                putExtra("User", loggedInUser)
            }
            startActivity(intent)
        }
        var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("http://appsecclass.report").addConverterFactory(
            GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        var client: CardInterface = retrofit.create(CardInterface::class.java)
        val outerContext = this
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val token = "Token ${loggedInUser?.token}"
        recyclerView.layoutManager = manager
        client.getCards(token)?.enqueue(object :
            Callback<List<Card?>?> {
            override fun onFailure(call: Call<List<Card?>?>, t: Throwable) {
                Log.d("Product Failure", "Product Failure in onFailure")
                Log.d("Product Failure", t.message.toString())

            }

            override fun onResponse(call: Call<List<Card?>?>, response: Response<List<Card?>?>) {
                if (!response.isSuccessful) {
                    Log.d("Product Failure", "Product failure. Yay.")
                }
                var cardListInternal = response.body()
                Log.d("Product Success", "Product Success. Boo.")
                if (cardListInternal == null) {
                    Log.d("Product Failure", "Parsed null product list")
                    Log.d("Product Failure", response.toString())
                } else {
                    recyclerView.adapter = CardRecyclerViewAdapter(outerContext, cardListInternal, loggedInUser)
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    // Part 5 - privacy invasive  override fun onLocationChanged(location: Location) {
    //    var userInfoContainer = UserInfoContainer(location, null, loggedInUser?.token)
    //   var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("http://appsecclass.report").addConverterFactory(
    //        GsonConverterFactory.create())
    //    var retrofit: Retrofit = builder.build()
    //    var client: UserInfo = retrofit.create(UserInfo::class.java)
    //    client.postInfo(userInfoContainer, loggedInUser?.token)?.enqueue(object: Callback<User?> {
    //        override fun onFailure(call: Call<User?>, t: Throwable) {
    //           Log.d("Metric Failure", "Metric Failure in onFailure")
    //           Log.d("Metric Failure", t.message.toString())

    //         }

    //         override fun onResponse(call: Call<User?>, response: Response<User?>) {
    //            if (!response.isSuccessful) {
    //                Log.d("Metric Failure", "Metric failure. Yay.")
    //             } else {
    //                 Log.d("Metric Success", "Metric success. Boo.")
    //                 Log.d("Metric Success", "Token:${userInfoContainer.token}")
    //             }
    //          }
    //      })
    //   }

    // Part 5 - privacy invasive override fun onSensorChanged(event: SensorEvent?) {
    // if (event != null) {
    //       var userInfoContainer = UserInfoContainer(null, event.values[0].toString(), loggedInUser?.token)
    //       var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("http://appsecclass.report").addConverterFactory(
    //           GsonConverterFactory.create())
    //      var retrofit: Retrofit = builder.build()
    //      var client: UserInfo = retrofit.create(UserInfo::class.java)
    //      client.postInfo(userInfoContainer, loggedInUser?.token)?.enqueue(object: Callback<User?> {
    //          override fun onFailure(call: Call<User?>, t: Throwable) {
    //              Log.d("Metric Failure", "Metric Failure in onFailure")
    //              Log.d("Metric Failure", t.message.toString())

    //           }

    //           override fun onResponse(call: Call<User?>, response: Response<User?>) {
    //               if (!response.isSuccessful) {
    //                   Log.d("Metric Failure", "Metric failure. Yay.")
    //               } else {
    //                   Log.d("Metric Success", "Metric success. Boo.")
    //                   Log.d("Metric Success", "Token:${userInfoContainer.token}")
    //                }
    //            }
    //        })
    //     }
    // }

    // Part 5 - privacy invasive - override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    //    return
    //   }

// override fun onResume() {
        //Part 5 - privacy invasive super.onResume()
        //mAccel?.also { accel ->
        //     sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL)
        }
//  }

// override fun onPause() {
      //Part 5 - privacy invasive  super.onPause()
      //  sensorManager.unregisterListener(this)
//  }


}
```


For `ProductScrollingActivity.kt` file, I removed the following as it pertains to sensor, logging, and location privacy. 
The following is the modified file: 
```
package com.example.giftcardsite

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giftcardsite.api.model.*
import com.example.giftcardsite.api.service.ProductInterface
import com.example.giftcardsite.api.service.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductScrollingActivity : AppCompatActivity(), SensorEventListener, LocationListener {
    var loggedInUser: User? = null
    private lateinit var sensorManager: SensorManager
    private var mAccel : Sensor? = null
    private var lastEvent : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Part 5 - privacy invasive val locationPermissionCode = 2
        // Part 5 - privacy invasive  var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Part 5 - privacy invasive     if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
        // Part 5 - privacy invasive         ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        // Part 5 - privacy invasive     }
        // Part 5 - privacy invasive   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        // Part 5 - privacy invasive   sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // Part 5 - privacy invasive   mAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        loggedInUser = intent.getParcelableExtra<User>("User")
        findViewById<Button>(R.id.view_cards_button).setOnClickListener{
            val intent = Intent(this, CardScrollingActivity::class.java).apply {
                putExtra("User", loggedInUser)
            }
            startActivity(intent)
        }
        //var productList: List<Product?>? = null                 // Part 3 Fix adding HTTPs
        var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(
                GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        var client: ProductInterface = retrofit.create(ProductInterface::class.java)
        val outerContext = this
        var manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = manager
        client.getAllProducts()?.enqueue(object :
                Callback<List<Product?>?> {
            override fun onFailure(call: Call<List<Product?>?>, t: Throwable) {
                Log.d("Product Failure", "Product Failure in onFailure")
                Log.d("Product Failure", t.message.toString())

            }

            override fun onResponse(call: Call<List<Product?>?>, response: Response<List<Product?>?>) {
                if (!response.isSuccessful) {
                    Log.d("Product Failure", "Product failure. Yay.")
                }
                var productListInternal = response.body()
                Log.d("Product Success", "Product Success. Boo.")
                if (productListInternal == null) {
                    Log.d("Product Failure", "Parsed null product list")
                    Log.d("Product Failure", response.toString())
                } else {
                    recyclerView.adapter = RecyclerViewAdapter(outerContext, productListInternal, loggedInUser)
                }
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onLocationChanged(location: Location) {
        // Part 5 - privacy invasive  var userInfoContainer = UserInfoContainer(location, null, loggedInUser?.token) // Part 3 Fix adding HTTPs
        var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(
            GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        var client: UserInfo = retrofit.create(UserInfo::class.java)

        client.postInfo(userInfoContainer, loggedInUser?.token)?.enqueue(object: Callback<User?> {
            override fun onFailure(call: Call<User?>, t: Throwable) {
                // Part 5 - privacy invasive         Log.d("Metric Failure", "Metric Failure in onFailure")
                // Part 5 - privacy invasive       Log.d("Metric Failure", t.message.toString())

            }

            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (!response.isSuccessful) {
                    // Part 5 - privacy invasive        Log.d("Metric Failure", "Metric failure. Yay.")
                } else {
                    // Part 5 - privacy invasive        Log.d("Metric Success", "Metric success. Boo.")
                    // Part 5 - privacy invasive        Log.d("Metric Success", "Token:${userInfoContainer.token}")
                }
            }
        })
    }

    // Part 5 - privacy invasive   override fun onSensorChanged(event: SensorEvent?) {
    // Part 5 - privacy invasive      if (event != null) {
    // Part 5 - privacy invasive         var userInfoContainer = UserInfoContainer(null, event.values[0].toString(), loggedInUser?.token)
                                                                        // Part 3 Fix adding HTTPs
    // Part 5 - privacy invasive         var builder: Retrofit.Builder = Retrofit.Builder().baseUrl("https://appsecclass.report").addConverterFactory(
    // Part 5 - privacy invasive             GsonConverterFactory.create())
    // Part 5 - privacy invasive         var retrofit: Retrofit = builder.build()
    // Part 5 - privacy invasive         var client: UserInfo = retrofit.create(UserInfo::class.java)
    // Part 5 - privacy invasive         if (lastEvent == null) {
    // Part 5 - privacy invasive             lastEvent = event.values[0].toString()
    // Part 5 - privacy invasive         } else if (lastEvent == event.values[0].toString()) {
    // Part 5 - privacy invasive        return
    // Part 5 - privacy invasive    }
    // Part 5 - privacy invasive     client.postInfo(userInfoContainer, loggedInUser?.token)?.enqueue(object: Callback<User?> {
    // Part 5 - privacy invasive          override fun onFailure(call: Call<User?>, t: Throwable) {
    // Part 5 - privacy invasive              Log.d("Metric Failure", "Metric Failure in onFailure")
    // Part 5 - privacy invasive              Log.d("Metric Failure", t.message.toString())

    // Part 5 - privacy invasive          }

    // Part 5 - privacy invasive          override fun onResponse(call: Call<User?>, response: Response<User?>) {
    // Part 5 - privacy invasive                 if (!response.isSuccessful) {
    // Part 5 - privacy invasive                    Log.d("Metric Failure", "Metric failure. Yay.")
    // Part 5 - privacy invasive                } else {
    // Part 5 - privacy invasive                    Log.d("Metric Success", "Metric success. Boo.")
    // Part 5 - privacy invasive                   Log.d("Metric Success", "Token:${userInfoContainer.token}")
    // Part 5 - privacy invasive               }
    // Part 5 - privacy invasive           }
    // Part 5 - privacy invasive       })
    // Part 5 - privacy invasive    }
    }

// Part 5 - privacy invasive  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
// Part 5 - privacy invasive      return
// Part 5 - privacy invasive  }

// Part 5 - privacy invasive override fun onResume() {
        // Part 5 - privacy invasive  super.onResume()
        // Part 5 - privacy invasive mAccel?.also { accel ->
            // Part 5 - privacy invasive  sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL)
// Part 5 - privacy invasive     }
// Part 5 - privacy invasive }

// Part 5 - privacy invasive override fun onPause() {
// Part 5 - privacy invasive     super.onPause()
// Part 5 - privacy invasive     sensorManager.unregisterListener(this)
// Part 5 - privacy invasive  }

}
```
