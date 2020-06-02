package pe.com.dms.movilasist.ui.fragment.regAsistFragment;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IRegistroAsistenciaFragmentContract {

    interface View extends IBaseContract.View {

        void mostrarInfoUser(Usuario user);

        void llenarSpinerTypeMarc(List<TypeMarcacion> listTypeMarc);

        void llenarSpinerMotivo(List<Motivo> listMotivo);

        void checkPermissionLocation();

        void verifyGpsActivate();

        void fillData(Configuracion configuracion);

        void closed();

        void cleanViews();

        void viewMessageExitoso();

        void viewMessageSinConfig();
    }

    interface Presenter extends IBaseContract.Presenter<IRegistroAsistenciaFragmentContract.View> {

        void validatedConnection(@TypeOption int typeOption);

        void obtainUserOffLine();

        void obtainConfigOffLine();

        void obtainMotivoOffLine();

        List<Motivo> getListMotivo();

        void obtainTypeMarcacionOffLine();

        List<TypeMarcacion> getListTypeMarc();

        void viewMessageActivarGps(Context context);

        void viewMessageHabilitarLocation(Context context);

        void setEditable(EditText editText, boolean editable);

        void saveImageInVariable(String path, EditText editText);

        void setVchCodPersonal(String vchCodPersonal);

        void setIntTipoMotivo(int intTipoMotivo);

        void setIntTipoMarca(int intTipoMarca);

        void setVchCoordenadasLoc(String vchCoordenadasLoc);

        void setImgFoto(String imgFoto);

        void setVchLugarReferencia(String vchLugarReferencia);

        void setIntMarcacion(int intMarcacion);

        void setIntIntegracionVAWEB(int IntIntegracionVAWEB);

        void sendMarcOffLine();

        void sendMarcOnLine();

    }
}
