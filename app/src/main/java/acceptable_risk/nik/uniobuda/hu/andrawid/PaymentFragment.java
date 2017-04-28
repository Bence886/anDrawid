package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.vending.billing.IInAppBillingService;

import acceptable_risk.nik.uniobuda.hu.andrawid.util.IabHelper;
import acceptable_risk.nik.uniobuda.hu.andrawid.util.IabResult;
import acceptable_risk.nik.uniobuda.hu.andrawid.util.Purchase;

/**
 * Created by tbenc on 2017. 04. 14..
 */

public class PaymentFragment extends Fragment {

    IInAppBillingService mService;

    IabHelper mHelper;

    View rootView;

    //ToDo: Hash It m8!!
    private String myPublicKey = "AIzaSyCcFUmW0j_wxmA9Ww7GMl-VV3FwxEut1oE";

    public static PaymentFragment newInstance() {

        Bundle args = new Bundle();

        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        getActivity().bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.playment_fragment_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHelper = new IabHelper(getActivity(), myPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (mHelper== null) return;
                if (!result.isSuccess()) return;
            }
        });

        //ToDo: Hash This Too M8!
        String payload = "";
        mHelper.launchPurchaseFlow(getActivity(), "OneTime", 100001, mPurchaseFinishedListener, payload);

        //TODO: getViews here

        //SuccessfulPayment();
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            if (mHelper==null)
                return;

            if (result.isFailure()){
                return;
            }

            //ToDo: Some more hashing.
            String payload = "";
            if (info.getDeveloperPayload() != payload)
                return;

            if (info.getSku().equals("OneTime")){
                SuccessfulPayment();
            }
        }
    };

    //TODO: call this                      --!!--
    private void SuccessfulPayment()
    {
        getArguments().clear();
        getArguments().putBoolean("payed", true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService!=null)
            getActivity().unbindService(mServiceConn);
    }
}
