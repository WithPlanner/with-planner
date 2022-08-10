package com.shop.withplanner.activity_community

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityAuthenticateLocationBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.shop.withplanner.map.coordToAddress.CoordToAddressApi
import com.shop.withplanner.map.coordToAddress.KakaoApiRetrofitClient
import com.shop.withplanner.map.coordToAddress.DtoCoordToAddress
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Response
import com.shop.withplanner.activity_etc.MainActivity


class CommunityAuthenticateLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityAuthenticateLocationBinding
    private val ACCESS_FINE_LOCATION = 1000
    private lateinit var locationManager: LocationManager
    //현재 위치를 가져오기 위한 변수
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    // 위치 값을 저장한 변수
    lateinit var mLastLocation: Location
    // 위치 정보 요청의 매개변수를 저장하는 변수
    internal lateinit var mLocationRequest: LocationRequest
    // retrofit 구성 관련 변수.
    private val coordToLocApi = KakaoApiRetrofitClient.apiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_community_authenticate_location
        )

        mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        //위치 추적 버튼
        binding.findLocBtn.setOnClickListener {
            // 현재위치: 위치 가져와서 표시하기
            if (checkLocationService()) {
                //GPS가 켜져 있을 경우
                permissionCheck()
            } else {
                //GPS가 꺼져 있을 경우
                Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.joinBtn.setOnClickListener {
            // 고정목적지: 서버에서 주소 받아오기(GET)
            binding.destination.text = "고정 목적지"
            // 습관이름 받아오기
            val habitName = "습관이름"
            // 고정위치와 현재위치가 같으면 실행
            // 인증확인 다이얼로그
            intent = Intent(this, MainActivity::class.java)
            val builder = AlertDialog.Builder(this).setTitle(habitName)
                .setMessage("오늘 ${habitName} 인증을 완료했습니다.")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    startActivity(intent)
                    finish()
                }).show()
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }


    //마커 띄우기
    fun setMarker(x: Double, y: Double) {
        println("marker 찍힘.")
        val marker = MapPOIItem()
        val point = MapPoint.mapPointWithGeoCoord(x, y)
        binding.mapView.setMapCenterPoint(point,true)
        marker.apply {
            itemName = "default marker"
            tag = 0
            mapPoint =  point // 좌표
            markerType = MapPOIItem.MarkerType.BluePin        // 마커 모양
            customImageResourceId = R.drawable.marker              // 커스텀 마커 이미지
            selectedMarkerType = MapPOIItem.MarkerType.BluePin  // 클릭 시 마커 모양
        }
        binding.mapView.addPOIItem(marker)
    }

    //권한 요청 후 바로 실행되는 메소드 (승인 or 거절)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                getLocation()

            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }

    //위치 추적 권한 확인
    private fun permissionCheck() {
        val preference = getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한이 없는 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                //사용자가 권한 요청을 명시적으로 거절 한 경우(이미 한번 거절) -> true
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    //최초로 권한 요청 하는 경우
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_FINE_LOCATION
                    )
                } else {
                    //다시 묻지 않음 클릭시
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치 정보를 확인하시려면 설정에서 앱의 위치 권한을 허용해주세요.")
                    //설정으로 이동 클릭시
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:$packageName")
                        )
                        startActivity(intent)
                    }
                    //취소 클릭시 이 전 페이지로 이동 -> 확인 필요.
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            //권한이 있는 경우
            //위치 추적 시작
            System.out.println("권한있어서 바로 실행")
            getLocation()
        }
    }


    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // 사용자 현재 위치 받아오기 - 위도, 경도( fusedLocationProviderClient 이용)
    private fun getLocation() {

        //mFusedLocationProviderClient의 인스턴스를 생성
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        println(mFusedLocationProviderClient.toString())

        //권한 확인 - 필수
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
            mFusedLocationProviderClient?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallBack,
                Looper.getMainLooper()
            )
        }

    }

    //시스템으로 부터 위치 정보를 callback
    private var mLocationCallBack = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
                locationResult.lastLocation
                var latitude = locationResult.lastLocation.latitude
                var longitude = locationResult.lastLocation.longitude
                println(latitude+ longitude)
                setMarker(latitude, longitude)
                callCoordToLoc(longitude.toString(),latitude.toString())
            }
        }

    //뒤로가기
    override fun onBackPressed() {
        super.onBackPressed()
    }

    //경도,위도 -> 주소
    fun callCoordToLoc(
        longitude : String,
        latitude : String
    ){
        val kakaoList = MutableLiveData<DtoCoordToAddress>()

        coordToLocApi.getAddreess(CoordToAddressApi.API_KEY,longitude,latitude)
            .enqueue(object : retrofit2.Callback<DtoCoordToAddress>{
                override fun onResponse(
                    call: Call<DtoCoordToAddress>,
                    response: Response<DtoCoordToAddress>
                ) {
                    kakaoList.value = response.body()
                    Log.d("주소", kakaoList.value!!.documents[0].toString())
                    setLocation(kakaoList.value!!.documents[0].road_address.address_name)

                }

                override fun onFailure(call: Call<DtoCoordToAddress>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
    //currentLoc에 값 넣어주는 함수
    fun setLocation(addressName:String){
        binding.currentLoc.text = addressName
    }
    }



