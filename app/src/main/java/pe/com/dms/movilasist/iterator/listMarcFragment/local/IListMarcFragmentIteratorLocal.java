package pe.com.dms.movilasist.iterator.listMarcFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestListMarc;

public interface IListMarcFragmentIteratorLocal {

    Maybe<List<Marcaciones>> obtainListMarcWithDate(RequestListMarc requestListMarc);

}
