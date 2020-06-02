package pe.com.dms.movilasist.iterator.regAsistFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;

public class RegAsistFragmentInteractorRemoteImpl implements IRegAsistFragmentInteractorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public RegAsistFragmentInteractorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                                @NonNull DataSourceLocal dataSourceLocal,
                                                @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        mPreferences = preferences;
        mLocal = dataSourceLocal;
    }

    @Override
    public Maybe<Message> validarConexion() {
        return mRemote.validarConnection();
    }

    /*@Override
    public Observable<Usuario> getInfoUserOnLine() {
        return mRemote.infoUserOnLine().doOnNext(usuario -> mLocal.insertUserOffLine(usuario));
    }

    @Override
    public Observable<Configuracion> getConfigOnLine() {
        return mRemote.getConfiguracionOnLine().doOnNext(configuracion -> mLocal.setConfiguracionOffLine(configuracion));
    }

    @Override
    public Observable<List<Personal>> getlistPersonalOnLine() {
        return mRemote.getListPersonalOnLine().doOnNext(lista -> mLocal.setListPersonalOffLine(lista));
    }

    @Override
    public Observable<List<Conceptos>> getListConceptosOnLine() {
        return mRemote.getListConceptosOnLine().doOnNext(list -> mLocal.setListConceptosOffLine(list));
    }

    @Override
    public Observable<List<Motivo>> getListMotivoOnLine() {
        return mRemote.getListMotivoOnLine().doOnNext(list -> mLocal.setListMotivoOffLine(list));
    }

    @Override
    public Observable<List<Supervisor>> getListSupervisoresOnLine() {
        return mRemote.getlistSupervisoresOnLine().doOnNext(list -> mLocal.setListSupervisoresOffLine(list));
    }

    @Override
    public Observable<Configuracion> getConfiguracionOnLine() {
        return mRemote.getConfiguracionOnLine().doOnNext(configuracion -> mLocal.setConfiguracionOffLine(configuracion));
    }*/

    @Override
    public Observable<Message> sendMarcOnLine(Marcacion marcacion) {
        return mRemote.setMarcOnLine(marcacion);
    }
}
