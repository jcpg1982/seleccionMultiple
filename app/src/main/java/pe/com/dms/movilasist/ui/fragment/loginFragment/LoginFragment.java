package pe.com.dms.movilasist.ui.fragment.loginFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentLoginBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.ui.activities.configActivity.ConfigActivity;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.ConfigPagerFragment;
import pe.com.dms.movilasist.ui.fragment.recoveryPasword.RecoveryPasswordFragment;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;

public class LoginFragment extends BaseMainFragment implements View.OnClickListener,
        ILoginFragmentContract.View {
    public static final String TAG = LoginFragment.class.getSimpleName();

    private FragmentLoginBinding binding;

    String mUser, mPassword;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    LoginFragmentPresenter presenter;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        setRetainInstance(true);
        setHasOptionsMenu(true);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        initEvents();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_config, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //@ColorInt int tintColorToolbar = TintUtils.getToolbarColor(getContext(), true);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getIcon() != null)
                menuItem.setIcon(TintUtils.createTintedDrawable(
                        menuItem.getIcon().mutate(), getResources().getColor(R.color.colorCardLight)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_config:
                Intent Intent = new Intent(getBaseActivity(), ConfigActivity.class);
                startActivity(Intent);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        saveDataInVariables();
        switch (v.getId()) {
            case R.id.btn_login:
                if (validateEntries())
                    presenter.validaConnection();
                break;
            case R.id.text_recovery_password:
                if (mFragmentInteractionListener != null)
                    mFragmentInteractionListener.onAddFragmentToStack(
                            RecoveryPasswordFragment.newInstance(),
                            "Recuperar contraseña",
                            null,
                            true,
                            true,
                            true,
                            RecoveryPasswordFragment.TAG);
                break;
        }
    }

    @Override
    public void moveToMain() {
        Intent Intent = new Intent(getBaseActivity(), MainActivity.class);
        ((LoginActivity) getActivity()).overridePendingTransition(R.anim.transition_slide_bottom_in, R.anim.transition_slide_left_out);
        startActivity(Intent);
        ((LoginActivity) getActivity()).closed();
    }

    private void init() {
        presenter.getConfigOffLine();
    }

    private void initEvents() {
        binding.btnLogin.setOnClickListener(this);
        binding.textRecoveryPassword.setOnClickListener(this);
        binding.textRecoveryPassword.setPaintFlags(binding.textRecoveryPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void saveDataInVariables() {
        mUser = binding.inputUser.getText().toString().trim();
        presenter.setUser(mUser);
        mPassword = binding.inputPassword.getText().toString().trim();
        presenter.setPassword(mPassword);
    }

    private boolean validateEntries() {
        boolean result = false;
        if (!TextUtils.isEmpty(mUser))
            if (!TextUtils.isEmpty(mPassword))
                result = true;
        return result;
    }
}
