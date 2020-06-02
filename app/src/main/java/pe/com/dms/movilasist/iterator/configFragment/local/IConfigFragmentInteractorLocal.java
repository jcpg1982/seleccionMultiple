package pe.com.dms.movilasist.iterator.configFragment.local;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Configuracion;

public interface IConfigFragmentInteractorLocal {

    Maybe<Configuracion> obtainConfig();

    Single<Long> saveConfigOffLine(Configuracion configuracion);
}
