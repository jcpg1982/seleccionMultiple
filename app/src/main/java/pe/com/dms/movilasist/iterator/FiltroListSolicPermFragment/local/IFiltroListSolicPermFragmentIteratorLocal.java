package pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.model.Marcaciones;

public interface IFiltroListSolicPermFragmentIteratorLocal {

    Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine();
}
