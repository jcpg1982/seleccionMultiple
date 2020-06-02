package pe.com.dms.movilasist.iterator.regAsistFragment.local;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.Marcaciones;

public interface IRegAsistFragmentInteractorLocal {

    Maybe<Configuracion> getConfigOffLine();

    Maybe<Usuario> getUserOffLine(String document);

    Maybe<List<Motivo>> getListMotivoOffLine();

    Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine();

    Single<Long> sendMarcOffLine(Marcacion marcacion);

}
