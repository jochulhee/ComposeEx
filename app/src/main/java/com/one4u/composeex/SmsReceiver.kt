package com.one4u.composeex

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.one4u.composeex.network.APIClient
import com.one4u.composeex.network.APIInterface
import com.one4u.composeex.network.APIResponse
import com.one4u.composeex.network.MsgData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmsReceiver : BroadcastReceiver() {
    private val mTAG = "SmsReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.d(mTAG, "onReceive()")

            val bundle = intent?.extras
            bundle?.let {
                val messages = parseSmsMessage(it)
                messages[0]?.let { msg ->
                    val sender = msg.originatingAddress.toString()
                    val contents = msg.messageBody.toString()
                    Log.d(mTAG, "sender :$sender")
                    Log.d(mTAG, "contents : $contents")
                    requestTargetNumbers(sender, contents)
                }
            }
        }
    }

    private fun parseSmsMessage(bundle: Bundle): Array<SmsMessage?> {
        val objs = bundle.get("pdus") as Array<*>
        val messages: Array<SmsMessage?> = arrayOfNulls(objs.size)

        for (i in objs.indices) {
            val format = bundle.getString("format");
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray?, format);
        }

        return messages
    }

    // API 연계
    private fun requestTargetNumbers(sender: String, contents: String) {
        val call = APIClient.getClient("https://gbmessage.gbphone.co.kr:1004/").create(APIInterface::class.java).getSenders()
        call.enqueue(object: Callback<APIResponse<Array<String>>> {
            override fun onResponse(
                call: Call<APIResponse<Array<String>>>,
                response: Response<APIResponse<Array<String>>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.validity == "true") {
                        val list = response.body()?.data
                        list?.let {
                            if (it.contains(sender)) {
                                requestSendMsg(sender, contents)
                            } else {
                                Log.w("TAG", "msg from anonymous")
                            }
                        }
                        Log.d("TAG", "onResponse, target checked")
                        return
                    }
                }
                Log.w("TAG", "onError")
            }

            override fun onFailure(call: Call<APIResponse<Array<String>>>, t: Throwable) {
                call.cancel()
                Log.e("TAG", "onFailure")
            }
        })
    }

    private fun requestSendMsg(sender: String, contents: String) {
        val obj = MsgData().apply {
            this.sender = sender
            this.contents = contents
        }

        val call = APIClient.getClient("https://gbmessage.gbphone.co.kr:1004/").create(APIInterface::class.java).sendMsgData(obj)
        call.enqueue(object: Callback<APIResponse<String>> {
            override fun onResponse(call: Call<APIResponse<String>>, response: Response<APIResponse<String>>) {
                if (response.isSuccessful) {
                    if (response.body()?.validity == "true") {
                        Log.d("TAG", "onResponse, msg send ok")
                        return
                    }
                }
                Log.w("TAG", "onError")
            }

            override fun onFailure(call: Call<APIResponse<String>>, t: Throwable) {
                call.cancel()
                Log.e("TAG", "onFailure")
            }

        })
    }
}