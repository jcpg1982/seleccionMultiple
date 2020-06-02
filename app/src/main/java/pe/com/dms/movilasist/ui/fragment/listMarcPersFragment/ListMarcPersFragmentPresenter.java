package pe.com.dms.movilasist.ui.fragment.listMarcPersFragment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.local.IListMarcPersFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.remote.IListMarcPersFragmentIteratorRemote;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.filterModel.ResultListMarcPers;
import pe.com.dms.movilasist.model.request.RequestListMarcPers;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ListMarcPersFragmentPresenter extends BasePresenter<IListMarcPersFragmentContract.View>
        implements IListMarcPersFragmentContract.Presenter {

    String TAG = ListMarcPersFragmentPresenter.class.getSimpleName();

    @Inject
    IListMarcPersFragmentIteratorLocal iteratorLocal;
    @Inject
    IListMarcPersFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private RequestListMarcPers mRequestListMarcPers;

    private List<Marcaciones> listMarcPers;
    private int allPages;
    private int currentPage;
    private int PAGE_SIZE = 20;
    private boolean isLastPage;

    @Inject
    public ListMarcPersFragmentPresenter() {
        mRequestListMarcPers = new RequestListMarcPers();
        listMarcPers = new ArrayList<>();
    }

    @Override
    public void obtainDataUserAndConfig() {
        Configuracion configuracion = preferenceManager.getConfig();
        mRequestListMarcPers.setIntIntegracionVAWEB(configuracion.getBitIntegracionVAWEB());
    }

    @Override
    public void validateConnection() {
        Log.e(TAG, "validateConnection");
        getCompositeDisposable().add(iteratorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "validateConnection error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "validateConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getListMarcPersOnLine();
                        }
                    }
                }, error -> {
                    Log.e(TAG, "validateConnection error : " + error);
                    Log.e(TAG, "validateConnection error : " + error.toString());
                    Log.e(TAG, "validateConnection error : " + error.getMessage());
                    Log.e(TAG, "validateConnection error : " + error.getLocalizedMessage());
                }));
    }

    @Override
    public void setResultListMarcPers(ResultListMarcPers resultListMarcPers) {
        mRequestListMarcPers.setVchCodPersonal(resultListMarcPers.getCodPersonal());
        mRequestListMarcPers.setVchNombreApe(resultListMarcPers.getNameLastName());
        mRequestListMarcPers.setDtmFechaInicio(resultListMarcPers.getDateIni());
        mRequestListMarcPers.setDtmFechaFin(resultListMarcPers.getDateFin());
        mRequestListMarcPers.setIntPaginaSize(PAGE_SIZE);
        validateConnection();
    }

    @Override
    public int allPages() {
        return allPages;
    }

    @Override
    public int currentPage() {
        return currentPage;
    }

    @Override
    public int PAGE_SIZE() {
        return PAGE_SIZE;
    }

    @Override
    public boolean islastPage() {
        return isLastPage;
    }

    @Override
    public List<Marcaciones> getDataDummyListMarcPers() {
        listMarcPers.clear();
        Marcaciones m1 = new Marcaciones("12345678",
                "Juan perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);
        Marcaciones m2 = new Marcaciones("12345679",
                "julio Perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);
        Marcaciones m3 = new Marcaciones("12345680",
                "Martin Perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);
        Marcaciones m4 = new Marcaciones("12345681",
                "Cesar Perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);
        Marcaciones m5 = new Marcaciones("12345682",
                "Andres Perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);
        Marcaciones m6 = new Marcaciones("12345683",
                "Carlos perez", "permiso",
                "1982/05/24 00:00:00", "-72.4758 -12.5454",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRNB6V01_y5ZUNUEvGh-kPWzg21gAFX_nrGZbd6KVwDBqyk1ofn&usqp=CAU",
                "andres tinoco 47854 santiago de surco lima peru", 1);

        listMarcPers.add(m1);
        listMarcPers.add(m2);
        listMarcPers.add(m3);
        listMarcPers.add(m4);
        listMarcPers.add(m5);
        listMarcPers.add(m6);
        return listMarcPers;
    }

    @Override
    public void getListMarcPersOnLine() {
        Log.e(TAG, "setListMarcPers");
        mRequestListMarcPers.setIntPaginaNum(1);
        getCompositeDisposable().add(iteratorRemote.getListMarcPersOnLine(mRequestListMarcPers)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "setListMarcPers error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "setListMarcPers subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            getView().viewListMarcPers(response.getMarca());
                            if (response.getMarca().size() >= PAGE_SIZE) {
                                isLastPage = false;
                                getView().mAdapter().addFooter();
                            } else {
                                if (getView().mAdapter().isAddFooter())
                                    getView().mAdapter().removeFooter();
                                isLastPage = true;
                            }
                        } else {
                            getView().viewListMarcPers(null);
                            getView().showErrorMessage(response.getMensaje().getVchMensaje(), response.getMensaje().getVchMensaje());
                        }
                    }
                }, error -> {
                    Log.e(TAG, "setListMarcPers error : " + error);
                    Log.e(TAG, "setListMarcPers error : " + error.toString());
                    Log.e(TAG, "setListMarcPers error : " + error.getMessage());
                    Log.e(TAG, "setListMarcPers error : " + error.getLocalizedMessage());
                }));
    }

    @Override
    public void getMoreListMarcPersOnLine(int page) {
        mRequestListMarcPers.setIntPaginaNum(page);
        Log.e(TAG, "setMoreListMarcPers");
        getCompositeDisposable().add(iteratorRemote.getListMarcPersOnLine(mRequestListMarcPers)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "setMoreListMarcPers error : ");
                })
                .subscribe(list -> {
                    Log.e(TAG, "setMoreListMarcPers subscribe : " + list);
                    getView().viewListMarcPers(listMarcPers);
                }, error -> {
                    Log.e(TAG, "setMoreListMarcPers error : " + error);
                    Log.e(TAG, "setMoreListMarcPers error : " + error.toString());
                    Log.e(TAG, "setMoreListMarcPers error : " + error.getMessage());
                    Log.e(TAG, "setMoreListMarcPers error : " + error.getLocalizedMessage());
                }));
    }
}
