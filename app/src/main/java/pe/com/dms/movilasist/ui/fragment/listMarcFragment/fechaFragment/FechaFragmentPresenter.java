package pe.com.dms.movilasist.ui.fragment.listMarcFragment.fechaFragment;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.listMarcFragment.local.IListMarcFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcFragment.remote.IListMarcFragmentIteratorRemote;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarc;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class FechaFragmentPresenter extends BasePresenter<IFechaFragmentContract.View>
        implements IFechaFragmentContract.Presenter {

    String TAG = FechaFragmentPresenter.class.getSimpleName();

    @Inject
    IListMarcFragmentIteratorLocal iteratorLocal;
    @Inject
    IListMarcFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;
    private RequestListMarc requestListMarc;
    private int allPages;
    private int currentPage;
    private int PAGE_SIZE = 20;
    private boolean isLastPage;

    @Inject
    public FechaFragmentPresenter() {
        requestListMarc = new RequestListMarc();
    }

    @Override
    public void setModelListMarc(ResultListMarc resultListMarc) {

        requestListMarc.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        requestListMarc.setIntMostrarMarca(preferenceManager.getConfig().getIntMostrarMarca());
        requestListMarc.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
        requestListMarc.setDtmFechaInicio(resultListMarc.getDateIni());
        requestListMarc.setDtmFechaFin(resultListMarc.getDateFin());
        requestListMarc.setIntPaginaSize(PAGE_SIZE);
        validateConnection();
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
                            getListMarcOnLine();
                        }
                    }
                }, error -> {
                    Log.e(TAG, "validateConnection error : " + error);
                    Log.e(TAG, "validateConnection error : " + error.toString());
                    Log.e(TAG, "validateConnection error : " + error.getMessage());
                    Log.e(TAG, "validateConnection error : " + error.getLocalizedMessage());
                    obtainlistMarcOffLine();
                }));
    }

    @Override
    public void obtainlistMarcOffLine() {
        Log.e(TAG, "obtainlistMarcOffLine");
        getCompositeDisposable().add(iteratorLocal.obtainListMarcWithDate(requestListMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "obtainlistMarcOffLine error : ");
                })
                .subscribe(list -> {
                    Log.e(TAG, "obtainlistMarcOffLine subscribe : " + list);
                    getView().viewListMarc(list);
                }, error -> {
                    Log.e(TAG, "obtainlistMarcOffLine error : " + error);
                    Log.e(TAG, "obtainlistMarcOffLine error : " + error.toString());
                    Log.e(TAG, "obtainlistMarcOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainlistMarcOffLine error : " + error.getLocalizedMessage());
                    obtainlistMarcOffLine();
                }));
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
    public void getListMarcOnLine() {
        Log.e(TAG, "getListMarcOnLine");
        requestListMarc.setIntPaginaNum(1);
        getCompositeDisposable().add(iteratorRemote.getListMarcOnLine(requestListMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "getListMarcOnLine error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "getListMarcOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            getView().viewListMarc(response.getMarca());
                            if (response.getPaginacion().getCantRegistro() >= PAGE_SIZE) {
                                isLastPage = false;
                                getView().mAdapter().addFooter();
                            } else {
                                if (getView().mAdapter().isAddFooter())
                                    getView().mAdapter().removeFooter();
                                isLastPage = true;
                            }
                        } else {
                            getView().viewListMarc(null);
                            getView().showErrorMessage(response.getMensaje().getVchMensaje(), response.getMensaje().getVchMensaje());
                        }
                    }
                }, error -> {
                    Log.e(TAG, "getListMarcOnLine error : " + error);
                    Log.e(TAG, "getListMarcOnLine error : " + error.toString());
                    Log.e(TAG, "getListMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "getListMarcOnLine error : " + error.getLocalizedMessage());
                    obtainlistMarcOffLine();
                }));
    }

    @Override
    public void getMoreListMarcOnLine(int page) {
        Log.e(TAG, "getMoreListMarcOnLine");
        requestListMarc.setIntPaginaNum(page);
        getCompositeDisposable().add(iteratorRemote.getListMarcOnLine(requestListMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "getMoreListMarcOnLine error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "getMoreListMarcOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            if (response.getMarca() != null && response.getMarca().size() > 0) {
                                getView().viewListMarc(response.getMarca());
                            } else {
                                getView().viewListMarc(null);
                            }
                        }
                    }
                }, error -> {
                    Log.e(TAG, "getMoreListMarcOnLine error : " + error);
                    Log.e(TAG, "getMoreListMarcOnLine error : " + error.toString());
                    Log.e(TAG, "getMoreListMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "getMoreListMarcOnLine error : " + error.getLocalizedMessage());
                    obtainlistMarcOffLine();
                }));
    }
}
