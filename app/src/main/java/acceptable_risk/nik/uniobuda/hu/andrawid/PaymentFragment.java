package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import acceptable_risk.nik.uniobuda.hu.andrawid.util.IabHelper;
import acceptable_risk.nik.uniobuda.hu.andrawid.util.IabResult;
import acceptable_risk.nik.uniobuda.hu.andrawid.util.Inventory;
import acceptable_risk.nik.uniobuda.hu.andrawid.util.Purchase;

/**
 * Created by tbenc on 2017. 04. 14..
 */

public class PaymentFragment extends Fragment {

    IInAppBillingService mService;

    boolean bIsPro =false;

    public static final String SKU_Color = "ColorPruchase";
    public static final String SKU_PRO = "PROPurchas";

    static final int RC_REQUEST =10001;

    IabHelper mHelper;

    View rootView;
    Button pro, oneTime;

    //ToDo: Hash It m8!!
    private String myPublicKey = "AIzaSyCypaap51NLHaR4VIu6w55u9VNNuHAHSmk";

    public static PaymentFragment newInstance() {

        Bundle args = new Bundle();

        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Bence:", "onCreate: Creating IAB helper");
        mHelper = new IabHelper(getActivity(), myPublicKey);

        Log.d("Bence", "Starting setup. ");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.d("Bence:", "Setup finished.");
                if (mHelper== null) return;
                if (!result.isSuccess()) return;
                Log.d("Bence", "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.playment_fragment_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (bIsPro) ((ColorPickerActivity)getActivity()).OkClicked();

        //TODO: getViews here
        pro = (Button) rootView.findViewById(R.id.pro_Button);
        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bIsPro){
                    Toast.makeText(getContext(), "Yoy are already a pro user!", Toast.LENGTH_SHORT);
                    return;
                }

                mHelper.launchPurchaseFlow(getActivity(), SKU_PRO, RC_REQUEST, mPurchaseFinishedListener, GeneratePayeload());
            }
        });

        oneTime = (Button) rootView.findViewById(R.id.oneTime_Button);
        oneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(getActivity(), SKU_Color, RC_REQUEST, mPurchaseFinishedListener, GeneratePayeload());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper == null) return;
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String GeneratePayeload() {
        //ToDo: generate some random stuff
        //SHOULD be the same for the same user
        return "a";
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
            if (!info.getDeveloperPayload().equals(payload))
                return;

            if (info.getSku().equals(SKU_PRO)){
                SuccessfulPayment();
                ((ColorPickerActivity)getActivity()).OkClicked();
            }

            if (info.getSku().equals(SKU_Color)){
                SuccessfulPayment();
                ((ColorPickerActivity)getActivity()).OkClicked();
            }
        }
    };

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            Log.d("Bence", "Query inventory finished.");
            if (mHelper== null) return;;
            if (result.isFailure()) return;;

            Log.d("Bence", "Query inventory was successful");

            Purchase oneTimePurchase = inv.getPurchase(SKU_PRO);
            bIsPro = (oneTimePurchase != null && verifyDeveloperPayload(oneTimePurchase));
            if (bIsPro)SuccessfulPayment();
        }
    };

    private void SuccessfulPayment()
    {
        getArguments().clear();
        getArguments().putBoolean("payed", true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean verifyDeveloperPayload(Purchase oneTimePurchase) {
        String payload = oneTimePurchase.getDeveloperPayload();

        // Ez a google álláspontja a dologról de nekem erre nincs kapacitásom
        // A warningal megjelölt rész jó QAD megoldásnak tűnik.
         /*
         * TODO: verify that the developer payload of the purchase is correct. It will be (later)
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return payload == GeneratePayeload();
    }
}
