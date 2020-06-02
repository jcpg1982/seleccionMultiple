package pe.com.dms.movilasist.iterator.lastMarcFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.model.Marcaciones;

public interface ILastMarcFragmentInteractorLocal {

    Maybe<List<Marcaciones>> setListLastMarcacionesOffLine();

    Single<Long>  deleteLastMarcOffLine(Marcacion marcacion);

}
