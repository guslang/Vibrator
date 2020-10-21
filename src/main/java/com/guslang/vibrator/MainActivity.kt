package com.guslang.vibrator

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var vibrator : Vibrator
    lateinit var vibrationEffect : VibrationEffect
    lateinit var mAdView : AdView
    lateinit var mInterstitialAd: InterstitialAd

    var clickCnt = 0  //전면 광고를 띄우기 위한 카운트
    val TAG: String =  "로그"
    var bSwitch : Boolean = false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "MainActivity - onCreate() called")

        //firebase-admob 초기화
        //MobileAds.initialize(this,getString(R.string.admob_app_id))
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        //

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        // 전면광고 설정
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.Interstitial_ad_unit_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
        //

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        ono_btn.setOnClickListener{
//            val progressVal = seekBar_speed.progress
            if (!bSwitch) {  // 진동 켜기
                Log.d(TAG, "onCreate: 진동 켜기")
                startVibrate(textView_cpattern.tag.toString(),bSwitch)
                ono_btn.playAnimation()

                // Custom animation speed or duration.
//                val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(2000)
//                animator.setRepeatCount(Animation.INFINITE)
//                animator.addUpdateListener { animation: ValueAnimator ->
//                    ono_btn.setProgress(
//                        animation.getAnimatedValue() as Float
//                    )
//                }
//                animator.start()
                vibrator?.vibrate(vibrationEffect)  // 진동 켜기
                textView_comment.text = "ON"
//                Log.d(TAG, "setOnClickListener on: repeatMode - ${animator.repeatMode}")
//                Log.d(TAG, "setOnClickListener on: repeatCount - ${animator.repeatCount}")
                bSwitch = true
            } else {  // 진동 끄기
                Log.d(TAG, "onCreate: 진동 끄기")
                ono_btn.pauseAnimation()
                vibrator?.cancel()  // 진동 끄기
                textView_comment.text = "OFF"
                bSwitch = false
            }

            loadInterstitialAd()  // 광고 호출
        }

        pattern1.setOnClickListener {
            Log.d(TAG, "onCreate: pattern1 clicked")
            changePattern(1)
            // 진동 호출
            startVibrate( textView_cpattern.tag.toString() ,bSwitch)
        }
        pattern2.setOnClickListener {
            Log.d(TAG, "onCreate: pattern2 clicked")
            changePattern(2)
            startVibrate( textView_cpattern.tag.toString() ,bSwitch)
        }
        pattern3.setOnClickListener {
            Log.d(TAG, "onCreate: pattern3 clicked")
            changePattern(3)
            startVibrate( textView_cpattern.tag.toString() ,bSwitch)
        }
        pattern4.setOnClickListener {
            Log.d(TAG, "onCreate: pattern4 clicked")
            changePattern(4)
            // 진동 호출
            startVibrate( textView_cpattern.tag.toString() ,bSwitch)
        }

        imageView_lock.setOnClickListener {
            Log.d(TAG, "MainActivity - onCreate() called : ${imageView_lock.tooltipText}")
            if (imageView_lock.tooltipText.toString().toUpperCase() == "UNLOCK") {
                imageView_lock.setImageResource(R.drawable.lock)
                imageView_lock.tooltipText = "lock"
            }
            else {
                imageView_lock.setImageResource(R.drawable.unlock)
                imageView_lock.tooltipText = "unlock"
            }
        }

        seekBar_speed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // 진동 호출
                startVibrate( textView_cpattern.tag.toString() ,bSwitch)
//                var strongValue: Int = 0
//                if (progress > 0) {
//                    strongValue = progress * 5 + 5;
//                }
//
//                Log.d(TAG, "onProgressChanged: progress[$progress], multipleVal[$strongValue]")
//                val aTimings = longArrayOf(0, 1000, 1000, 500, 500)
//                val aAmplitudes = intArrayOf(0, strongValue, 0, strongValue, 0)
//                // 진동 효과 (진동유형(시간), 진동세기, 반복(0:무한반복, -1:한번)
//                vibrationEffect = VibrationEffect.createWaveform(aTimings, aAmplitudes, 0)
//
//                if ( textView_comment.text == "ON" )
//                    vibrator?.vibrate(vibrationEffect)  // 진동 켜기
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG, "onStartTrackingTouch: called")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d(TAG, "onStopTrackingTouch: called")
            }
        })


//        imageView_Vibration.setOnClickListener {
//            var vibratorStatus : String = ""
//            if (it.tag == 1) {
//                vibrator?.cancel()  // 진동 끄기
//                it.tag = 0
//                imageView_Vibration.setImageResource(R.drawable.ic_love)
//                onoff.setImageResource(R.drawable.ic_onoff2)
//                textView_comment.text = "OFF"
//            }
//            else {
//                vibrator?.vibrate(vibrationEffect)  // 진동 켜기
//                it.tag = 1
//                imageView_Vibration.setImageResource(R.drawable.ic_love2)
//                onoff.setImageResource(R.drawable.ic_onoff3)
//                textView_comment.text = "ON"
//            }
//        }
//        ivVibrator.setOnClickListener {
//            var vibratorStatus : String = ""
//            if (it.tag == 1) {
//                vibrator?.cancel()  // 진동 끄기
//                it.tag = 0
//                btnVibratorStart.text = "진동 켜기"
//                tvComment.text = "진동을 끕니다."
//            }
//            else {
//                vibrator?.vibrate(vibrationEffect)  // 진동 켜기
//                it.tag = 1
//                btnVibratorStart.text = "진동 꺼기"
//                tvComment.text = "진동이 느껴지세요?"
//
//            }
//        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun changePattern(iPattern : Int){
        shortVibrate()
        when (iPattern) {
            1 -> {
                pattern1.setTextColor(Color.WHITE)
                pattern2.setTextColor(Color.parseColor("#ffbb33"))
                pattern3.setTextColor(Color.parseColor("#ffbb33"))
                pattern4.setTextColor(Color.parseColor("#ffbb33"))

                pattern1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28.0f)
                pattern2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)

                textView_cpattern.text = pattern1.text.toString()
                textView_cpattern.tag = 1
                textView_cpattern.setTextColor(Color.RED)
                textView_cpattern.setBackgroundResource(R.drawable.rounded_corner_white)
            }
            2 -> {
                pattern1.setTextColor(Color.parseColor("#ffbb33"))
                pattern2.setTextColor(Color.WHITE)
                pattern3.setTextColor(Color.parseColor("#ffbb33"))
                pattern4.setTextColor(Color.parseColor("#ffbb33"))

                pattern1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28.0f)
                pattern3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)

                textView_cpattern.text = pattern2.text.toString()
                textView_cpattern.tag = 2
                textView_cpattern.setTextColor(Color.BLUE)
                textView_cpattern.setBackgroundResource(R.drawable.rounded_corner_red)
            }
            3 -> {
                pattern1.setTextColor(Color.parseColor("#ffbb33"))
                pattern2.setTextColor(Color.parseColor("#ffbb33"))
                pattern3.setTextColor(Color.WHITE)
                pattern4.setTextColor(Color.parseColor("#ffbb33"))

                pattern1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28.0f)
                pattern4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)

                textView_cpattern.text = pattern3.text.toString()
                textView_cpattern.tag = 3
                textView_cpattern.setTextColor(Color.DKGRAY)
                textView_cpattern.setBackgroundResource(R.drawable.rounded_corner_blue)
            }
            4 -> {
                pattern1.setTextColor(Color.parseColor("#ffbb33"))
                pattern2.setTextColor(Color.parseColor("#ffbb33"))
                pattern3.setTextColor(Color.parseColor("#ffbb33"))
                pattern4.setTextColor(Color.WHITE)

                pattern1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23.0f)
                pattern4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28.0f)

                textView_cpattern.text = pattern4.text.toString()
                textView_cpattern.tag = 4
                textView_cpattern.setTextColor(Color.MAGENTA)
                textView_cpattern.setBackgroundResource(R.drawable.rounded_corner_orange)
            }
        }

        // 전면 광고 로드
        loadInterstitialAd()

    }
    private fun loadInterstitialAd(){
        clickCnt += 1

        if ( !bSwitch &&  clickCnt > 10) {
            // admob 전면 광고
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d(TAG, "changePattern: The interstitial wasn't loaded yet.")
            }
            clickCnt = 0
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun shortVibrate() {
        Log.d(TAG, "MainActivity - shortVibrate() called")
        // 진동이 시작되는 타이밍
        var aTimings = longArrayOf()
        // 진동강도 설정
        var aAmplitudes = intArrayOf()

        aTimings = longArrayOf(0, 50)
        aAmplitudes = intArrayOf(0, 50)

        // 진동 효과 (진동유형(시간), 진동세기, 반복(0:무한반복, -1:한번)
        vibrationEffect = VibrationEffect.createWaveform(aTimings, aAmplitudes, -1)

        if ( !bSwitch ) {
            vibrator?.vibrate(vibrationEffect)  // 진동 켜기
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startVibrate(v_pattern : String, on_flag : Boolean) {
        // 진동이 시작되는 타이밍
        var aTimings = longArrayOf()
        // 진동강도 설정
        var aAmplitudes = intArrayOf()

        var speed = seekBar_speed.progress
        speed = (1000 - (speed * 10)) / 5

        Log.d(TAG, "startVibrate: v_pattern[$v_pattern], speed[$speed]")

        // 진동 패턴에 따른 효과 설정
        when (v_pattern) {
           "1" -> { //1.Constant
                aTimings = longArrayOf(0, 5000)
                aAmplitudes = intArrayOf(0, 255)
//               seekBar_speed.isEnabled = false
            }
            "2" -> { //2.Light
                aTimings = longArrayOf(0, 100, speed.toLong())
                aAmplitudes = intArrayOf(0, 150, 0)
//                seekBar_speed.isEnabled = true
            }
            "3" -> { //3.Medium
                aTimings = longArrayOf(0, 300, speed.toLong())
//                aTimings = longArrayOf(0, 700, 300)
                aAmplitudes = intArrayOf(0, 200, 0)
//                seekBar_speed.isEnabled = true
            }
            "4" -> { //4.Deep
                aTimings = longArrayOf(0, 700, speed.toLong())
//                aTimings = longArrayOf(0, 700, 500)
                aAmplitudes = intArrayOf(0, 255, 0)
//                seekBar_speed.isEnabled = true
            }
            else -> { // Note the block
                Log.d(TAG, "startVibrate: no pattern")
//                seekBar_speed.isEnabled = true
            }
        }
        // 진동 효과 (진동유형(시간), 진동세기, 반복(0:무한반복, -1:한번)
        vibrationEffect = VibrationEffect.createWaveform(aTimings, aAmplitudes, 0)

        if ( on_flag ) {
            vibrator?.vibrate(vibrationEffect)  // 진동 켜기
        }
    }

    override fun onStart() {        
        super.onStart()
        Log.d(TAG, "MainActivity - onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() called")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        if (imageView_lock.tooltipText.toString().toUpperCase() == "UNLOCK")
            vibrator?.cancel()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy() called")
    }
}