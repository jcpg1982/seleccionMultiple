package pe.com.dms.movilasist.ui.fragment.regAsistFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.SwitchActivo;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.databinding.FragmentRegistroAsistenciaBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.TextWatcherAdapter;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.base.BaseFragment;
import pe.com.dms.movilasist.ui.fragment.listLastMarcFragment.ListLastMarcFragment;
import pe.com.dms.movilasist.util.FileUtils;
import pe.com.dms.movilasist.util.FragmentUtils;
import pe.com.dms.movilasist.util.PermissionsUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;

public class RegistroAsistenciaFragment extends BaseFragment implements View.OnClickListener,
        IRegistroAsistenciaFragmentContract.View,
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = RegistroAsistenciaFragment.class.getSimpleName();

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    RegistroAsistenciaFragmentPresenter presenter;

    private FragmentRegistroAsistenciaBinding binding;

    private Uri mPhotoUri;

    private static final long INTERVAL = 0; //1 segundo // 1000 * 60 * 1; 1 minute
    private static final long FASTEST_INTERVAL = 0; // 1 segundo // 1000 * 60 * 1; 1 minute
    private GoogleApiClient mGoogleApiClient;
    private SupportMapFragment mMapFragment;
    private LocationRequest mLocationRequest;
    private Double latitude, longitude;
    private String mAddress;
    private GoogleMap mMap;
    private int mTypeMarc;
    private int mMotivoId;
    private int veces = 0;

    public RegistroAsistenciaFragment() {
    }

    public static RegistroAsistenciaFragment newInstance() {
        RegistroAsistenciaFragment fragment = new RegistroAsistenciaFragment();
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
        buildGoogleApiClient(++veces);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        binding = FragmentRegistroAsistenciaBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        if (!mGoogleApiClient.isConnected())
            buildGoogleApiClient(++veces);
        presenter.obtainUserOffLine();
        presenter.obtainConfigOffLine();
        presenter.obtainMotivoOffLine();
        presenter.obtainTypeMarcacionOffLine();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");
        if (!mGoogleApiClient.isConnected())
            buildGoogleApiClient(++veces);
        mMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        initEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onCreateView");
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onCreateView");
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        presenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onCreateView");
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionsUtils.REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoUri = FileUtils.IntentF.pickImageCamera(this,
                            Constants.Intent.REQUEST_CODE_IMAGE_CAMERA);
                } else {
                    Utils.showToast(getContext(), R.string.error);
                }
                break;
            case PermissionsUtils.REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    verifyGpsActivate();
                } else {
                    presenter.viewMessageHabilitarLocation(getContext());
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult requestCode: " + requestCode + ", resultCode: " + resultCode + ", data: " + data);
        if (resultCode != Activity.RESULT_OK) {
            mPhotoUri = null;
            if (requestCode == Constants.Intent.REQUEST_CODE_ENABLED_GPS)
                presenter.viewMessageActivarGps(getContext());
            return;
        }
        switch (requestCode) {
            case Constants.Intent.REQUEST_CODE_IMAGE_CAMERA:
                Log.e(TAG, "onActivityResult REQUEST_CODE_IMAGE_CAMERA: " + requestCode);
                if (mPhotoUri != null) {
                    String pathTemp = mPhotoUri.getPath();
                    if (pathTemp != null) {
                        presenter.saveImageInVariable(pathTemp, binding.inputPhoto);
                    } else {
                        binding.inputPhoto.setText(null);
                        showErrorMessage("Erros al cargar la imagen", "La imagen no pudo ser guardada correctamente");
                    }
                }
                break;
            case Constants.Intent.REQUEST_CODE_ENABLED_GPS:
                Log.e(TAG, "onActivityResult REQUEST_CODE_ENABLED_GPS: " + requestCode);
                if (mGoogleApiClient.isConnected()) {
                    startLocationUpdates();
                } else {
                    buildGoogleApiClient(++veces);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startLocationUpdates();
                        }
                    }, 1000);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_last_marc:
                if (mFragmentInteractionListener != null)
                    mFragmentInteractionListener.onAddFragmentToStack(
                            ListLastMarcFragment.newInstance(),
                            "Ultimas Marcaciones",
                            null,
                            true,
                            true,
                            true,
                            ListLastMarcFragment.TAG);
                break;
            case R.id.btn_send_marc:
                if (validateEntries()) {
                    presenter.validatedConnection(TypeOption.SEND_MARC);
                } else {
                    showWarningMessage("Verifique haber colocado toda informacion en los campos solicitados");
                }
                break;
            case R.id.btn_add_photo:
            case R.id.input_photo:
                switch (binding.containerSpinerTipoMarc.getVisibility()) {
                    case View.VISIBLE:
                        if (mTypeMarc > 0)
                            checkPermissionStorageCamera();
                        else
                            mostrarMensajesinTipoMarcacion();
                        break;
                    case View.GONE:
                        checkPermissionStorageCamera();
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged");
        if (!FragmentUtils.isFragmentAttached(RegistroAsistenciaFragment.this))
            return;
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            presenter.setVchCoordenadasLoc(String.valueOf(latitude) + " " + String.valueOf(longitude));
            mMap.clear();
            mAddress = Utils.getStreet(getContext(),
                    latitude,
                    longitude);
            if (TextUtils.isEmpty(mAddress))
                mAddress = "No se logró encontrar dirección aproximada";
            binding.textAddress.setText(mAddress);
            presenter.setVchLugarReferencia(mAddress);
            LatLng latLng = new LatLng(latitude, longitude);
            hereYour(latLng);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                    LatLng(latitude, longitude), 16.5f));

            stopLocationUpdates();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e(TAG, "onMapReady");
        setupDataInMap(googleMap);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void verifyGpsActivate() {
        Log.e(TAG, "verifyGpsActivate: ");
        if (!mGoogleApiClient.isConnected())
            buildGoogleApiClient(++veces);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(getActivity())
                .checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (getActivity() == null && isAdded())
                    return;
                Log.e(TAG, "verifyGpsActivate onSuccess: ");
                if (mGoogleApiClient.isConnected()) {
                    startLocationUpdates();
                } else {
                    buildGoogleApiClient(++veces);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startLocationUpdates();
                        }
                    }, 1000);
                }
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "verifyGpsActivate addOnFailureListener: ");
                if (e instanceof ResolvableApiException) {
                    // SchoolLocation settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    Log.e(TAG, "verifyGpsActivate onFailure if: ");
                    try {
                        Log.e(TAG, "verifyGpsActivate try: ");
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                Constants.Intent.REQUEST_CODE_ENABLED_GPS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        Log.e(TAG, "verifyGpsActivate catch: " + sendEx.getMessage());
                    }
                }else{
                    Log.e(TAG, "verifyGpsActivate onFailure else: ");
                }
            }
        });
    }

    @Override
    public void fillData(Configuracion configuracion) {
        if (configuracion.getBitMarcacionGrupal() == SwitchActivo.ACTIVO) {
            presenter.setEditable(binding.inputCodPersonal, true);
            binding.btnSearch.setEnabled(true);
        } else {
            presenter.setEditable(binding.inputCodPersonal, false);
            binding.btnSearch.setEnabled(false);
        }

        if (configuracion.getBitColocarFotoMarca() == SwitchActivo.ACTIVO) {
            binding.btnAddPhoto.setVisibility(View.VISIBLE);
            binding.textPhoto.setVisibility(View.VISIBLE);
            binding.containerInputPhoto.setVisibility(View.VISIBLE);
        } else {
            binding.btnAddPhoto.setVisibility(View.GONE);
            binding.textPhoto.setVisibility(View.GONE);
            binding.containerInputPhoto.setVisibility(View.GONE);
        }

        if (configuracion.getBitIdentificacionMarca() == SwitchActivo.ACTIVO) {
            binding.textTipoMarcacion.setVisibility(View.VISIBLE);
            binding.containerSpinerTipoMarc.setVisibility(View.VISIBLE);
            presenter.obtainTypeMarcacionOffLine();
        } else {
            binding.textTipoMarcacion.setVisibility(View.GONE);
            binding.containerSpinerTipoMarc.setVisibility(View.GONE);
        }

        if (configuracion.getBitConLocalizacion() == SwitchActivo.ACTIVO) {
            binding.cardMapa.setVisibility(View.VISIBLE);
            initLocation();
        } else {
            binding.cardMapa.setVisibility(View.GONE);
        }
    }

    @Override
    public void closed() {
        ((MainActivity) getActivity()).closedMain();
    }

    @Override
    public void cleanViews() {
        binding.inputPhoto.setText(null);
    }

    @Override
    public void viewMessageExitoso() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Su marcacion fue enviada con exito")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_accept))
                .textColorPositiveButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }

    @Override
    public void viewMessageSinConfig() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Su dispositivo no cuenta con la configuracion iniciada para su funcionamiento comuniquese con soporte para su solucion")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.nav_cerrar_session))
                .textColorPositiveButton(R.color.success)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                        Intent Intent = new Intent(getBaseActivity(), LoginActivity.class);
                        startActivity(Intent);
                        getBaseActivity().finish();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }

    @Override
    public void mostrarInfoUser(Usuario user) {
        if (user != null) {
            binding.inputCodPersonal.setText(user.getVchCodigoPersonal());
            binding.textNameLastName.setText(user.getVchNombre() + " " + user.getVchApellidos());
        }
    }

    @Override
    public void llenarSpinerTypeMarc(List<TypeMarcacion> listTypeMarc) {
        if (listTypeMarc != null
                && listTypeMarc.size() > 0) {
            Log.e(TAG, "llenarSpiner if");
            ArrayList<String> itemSelect = new ArrayList<>();
            for (TypeMarcacion rellenas : listTypeMarc) {
                itemSelect.add(rellenas.getDescripcion());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    itemSelect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spTypeMarc.setAdapter(adapter);
        }
    }

    @Override
    public void llenarSpinerMotivo(List<Motivo> listMotivo) {
        if (listMotivo != null
                && listMotivo.size() > 0) {
            Log.e(TAG, "llenarSpiner if");
            ArrayList<String> itemSelect = new ArrayList<>();
            for (Motivo rellenas : listMotivo) {
                itemSelect.add(rellenas.getVchMotivo());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                    itemSelect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spMotivo.setAdapter(adapter);
        }
    }

    private void initEvents() {
        binding.btnLastMarc.setOnClickListener(this);
        binding.btnSendMarc.setOnClickListener(this);
        binding.btnAddPhoto.setOnClickListener(this);
        binding.inputPhoto.setOnClickListener(this);
        binding.inputPhoto.setKeyListener(null);

        binding.inputCodPersonal.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setVchCodPersonal(charOrEditable.toString());
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.inputPhoto.addTextChangedListener((TextWatcherAdapter)
                (action, charOrEditable, start, count, afterOrBefore) -> {
                    switch (action) {
                        case afterTextChanged:
                            Log.e(TAG, "afterTextChanged charOrEditable: " + charOrEditable + ", start: " + start + ", count: " + count + ", afterOrBefore: " + afterOrBefore);
                            if (charOrEditable.length() > 0)
                                presenter.setImgFoto(charOrEditable.toString());
                            break;
                        case beforeTextChanged:
                        case onTextChanged:
                            break;
                    }
                });

        binding.spMotivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMotivoId = Integer.parseInt(presenter.getListMotivo().get(position).getVchCodMotivo());
                presenter.setIntTipoMotivo(mMotivoId);
                Log.e(TAG, "onItemSelected motivo: " + presenter.getListMotivo().get(position));
                Log.e(TAG, "onItemSelected posicion: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spTypeMarc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTypeMarc = presenter.getListTypeMarc().get(position).getId();
                presenter.setIntTipoMarca(mTypeMarc);
                Log.e(TAG, "onItemSelected Tipo marcacion: " + presenter.getListTypeMarc().get(position));
                Log.e(TAG, "onItemSelected posicion: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void checkPermissionStorageCamera() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean granted = PermissionsUtils.hasPermissions(getActivity(), permissions);
        if (granted) {
            mPhotoUri = (FileUtils.IntentF.pickImageCamera(this,
                    Constants.Intent.REQUEST_CODE_IMAGE_CAMERA));
        } else {
            PermissionsUtils.requestPermissions(getActivity(), permissions,
                    PermissionsUtils.REQUEST_PERMISSION_CAMERA);
        }
    }

    private void initLocation() {
        if (getActivity() != null) {
            boolean success = Utils.checkGooglePlayServices(getContext());
            if (success) {
                checkPermissionLocation();
                mMapFragment.getMapAsync(this);
            } else {
                showNotGooglePlayServices();
            }
        }
    }

    public void checkPermissionLocation() {
        String[] permission = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean granted = PermissionsUtils.hasPermissions(getActivity(), permission);
        if (granted) {
            verifyGpsActivate();
        } else {
            PermissionsUtils.requestPermissions(getActivity(), permission,
                    PermissionsUtils.REQUEST_PERMISSION_LOCATION);
        }
    }

    private void buildGoogleApiClient(int numVeces) {
        Log.e(TAG, "buildGoogleApiClient: " + numVeces);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private void setupDataInMap(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(false);  //Botones de zoom
        mMap.getUiSettings().setZoomGesturesEnabled(false);  //Gesto de zoom
        mMap.getUiSettings().setCompassEnabled(false);       //Brujula
        mMap.getUiSettings().setAllGesturesEnabled(false);       //mover el mapa
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {
        if (!FragmentUtils.isFragmentAttached(RegistroAsistenciaFragment.this))
            return;
        if (mGoogleApiClient.isConnected()) {
            Log.e(TAG, "startLocationUpdates: " + mGoogleApiClient.isConnected());
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        } else {
            buildGoogleApiClient(++veces);
            Log.e(TAG, "startLocationUpdates else: ");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLocationUpdates();
                }
            }, 2000);
        }
    }

    protected void stopLocationUpdates() {
        Log.e(TAG, "jak stopLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private void showNotGooglePlayServices() {
        new DefaultDialog.Builder(getActivity())
                .title(getResources().getString(R.string.attention))
                .message(getResources().getString(R.string.not_google_play_services))
                .textPositiveButton(R.string.button_accept)
                .textColorPositiveButton(R.color.success)
                .cancelable(false)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.dismiss();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_NOT_GOOGLE_PLAY_SERVICES);
    }

    private void hereYour(LatLng here) {
        Drawable drawable = TintUtils.createTintedDrawable(getResources().getDrawable(
                R.drawable.ic_action_location), getResources().getColor(R.color.success));
        mMap.addMarker(new MarkerOptions()
                .draggable(false)
                .anchor(0.0f, 1.0f)
                .icon(getMarkerIconFromDrawable(drawable))
                .position(here));
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        if (drawable == null)
            return null;
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void mostrarMensajesinTipoMarcacion() {
        new DefaultDialog.Builder(getContext())
                .title(getResources().getString(R.string.attention))
                .message("Antes de tomar una foto debe seleccionar el tipo de marcacion")
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(getResources().getString(R.string.button_accept))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(getResources().getString(R.string.button_cancel))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        binding.spTypeMarc.requestFocus();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .buildAndShow(((AppCompatActivity) getActivity()).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SAVE_CONFIG);
    }

    private boolean validateEntries() {
        boolean validated = false;
        if (!TextUtils.isEmpty(binding.inputCodPersonal.getText().toString()))
            if (validateMotivo())
                if (validateTypeMarc())
                    if (validatePhoto())
                        if (validateGPS())
                            validated = true;

        return validated;
    }

    private boolean validateMotivo() {
        if (mMotivoId > 0)
            return true;
        else
            return false;
    }

    private boolean validateTypeMarc() {
        boolean validate = false;
        switch (binding.containerSpinerTipoMarc.getVisibility()) {
            case View.VISIBLE:
                if (mTypeMarc > 0)
                    validate = true;
                else
                    validate = false;
                break;
            case View.GONE:
                validate = true;
                break;
        }
        return validate;
    }

    private boolean validatePhoto() {
        boolean validated = false;
        switch (binding.containerInputPhoto.getVisibility()) {
            case View.VISIBLE:
                if (!TextUtils.isEmpty(binding.inputPhoto.getText().toString()))
                    validated = true;
                break;
            case View.GONE:
                validated = true;
        }
        return validated;
    }

    private boolean validateGPS() {
        boolean validated = false;
        switch (binding.cardMapa.getVisibility()) {
            case View.VISIBLE:
                //if (!TextUtils.isEmpty(binding.textAddress.getText().toString()))
                if (!TextUtils.isEmpty(String.valueOf(latitude)))
                    if (!TextUtils.isEmpty(String.valueOf(longitude)))
                        validated = true;
                break;
            case View.GONE:
                validated = true;
        }
        return validated;
    }
}
