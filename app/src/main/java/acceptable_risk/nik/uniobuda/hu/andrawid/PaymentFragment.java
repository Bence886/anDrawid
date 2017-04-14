package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tbenc on 2017. 04. 14..
 */

public class PaymentFragment extends Fragment {

    View rootView;

    public static PaymentFragment newInstance() {

        Bundle args = new Bundle();

        PaymentFragment fragment = new PaymentFragment();
        fragment.setArguments(args);
        return fragment;
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

        //TODO: getViews here
        
        SuccessfulPayment();
    }



    //TODO: call this                      --!!--
    private void SuccessfulPayment()
    {
        getArguments().clear();
        getArguments().putBoolean("payed", true);
    }
}
