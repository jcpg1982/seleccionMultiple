package pe.com.dms.movilasist.ui.fragment.recoveryPasword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentRecoveryPasswordBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.util.TextUtils;

public class RecoveryPasswordFragment extends BaseMainFragment implements View.OnClickListener,
        IRecoveryPasswordFragmentContract.View {
    public static final String TAG = RecoveryPasswordFragment.class.getSimpleName();

    private FragmentRecoveryPasswordBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    RecoveryPasswordFragmentPresenter presenter;

    public RecoveryPasswordFragment() {
    }

    public static RecoveryPasswordFragment newInstance() {
        RecoveryPasswordFragment fragment = new RecoveryPasswordFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.e(TAG, "onCreate");
        presenter.attachView(this);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRecoveryPasswordBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        initEvents();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        saveDataInVariables();
        switch (v.getId()) {
            case R.id.btn_send:
                if (validateEntries()) {
                    if (presenter.validateCorreo())
                        presenter.validaConnection();
                    else
                        showErrorMessage("Ingrese el formato correcto example@mail.com",
                                "Ingrese el formato correcto example@mail.com");
                } else {
                    showErrorMessage("Los campos no deben estar vacios",
                            "Los campos no deben estar vacios");
                    binding.inputCorreo.requestFocus();
                }
                break;
            case R.id.btn_cancel:
                closed();
                break;
        }
    }

    @Override
    public void closed() {
        if (mFragmentInteractionListener != null)
            mFragmentInteractionListener.onRemoveFragmentToStack(true);
    }

    private void init() {
        //((LoginActivity) getActivity()).setColorToolbar(Color.TRANSPARENT);
    }

    private void initEvents() {
        binding.btnSend.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
    }

    private void saveDataInVariables() {
        presenter.setCorreo(binding.inputCorreo.getText().toString().trim());
    }

    private boolean validateEntries() {
        boolean result = false;
        if (!TextUtils.isEmpty(binding.inputCorreo.getText().toString().trim()))
            result = true;
        return result;
    }
}
