package pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;

public interface IFiltroListAprobSolicPermFragmentIteratorLocal {

    Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine();
}
