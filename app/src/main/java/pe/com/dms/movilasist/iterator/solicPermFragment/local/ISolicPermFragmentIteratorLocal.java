package pe.com.dms.movilasist.iterator.solicPermFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.Supervisor;

public interface ISolicPermFragmentIteratorLocal {

    Single<Long> sendSolicPermOffLine(SolicitudPermiso solicitudPermiso);

    Maybe<List<Conceptos>> getListConceptosOffLine();

    Maybe<List<Supervisor>> getListSupervisoresOffLine();

}
