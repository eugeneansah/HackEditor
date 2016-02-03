package com.hackathon.hackmsit.networks;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hackathon.hackmsit.activities.MyApplication;

/**
 * Created by jarvis on 2/3/16.
 */
public class VolleySingelton {


    public static VolleySingelton mInstane;
    public static RequestQueue mRequest;


    public VolleySingelton() {
        mRequest = Volley.newRequestQueue(MyApplication.getContext());
        /*newRequestQ takes context as a parameter but not a simple one create a new class MyApplication
         in this case and add the class to manifest file*/
    }

    public static VolleySingelton getInstance() {
        if (mInstane == null) {
            mInstane = new VolleySingelton();
        }
        return mInstane;
    }
    public RequestQueue getrequestQueue()
    {   //u can make request here also
        // mRequest = Volley.newRequestQueue(MyApplication.getContext());
        return mRequest;
    }
}