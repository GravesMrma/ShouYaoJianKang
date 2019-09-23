package com.wuhanzihai.rbk.ruibeikang.receiver;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;

public class MyHuaWeiPushReceiver extends PushReceiver {

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String value=extras.getString("");
        Log.e("sdadas",token);
        Log.e("sdadas",value);
    }
}
