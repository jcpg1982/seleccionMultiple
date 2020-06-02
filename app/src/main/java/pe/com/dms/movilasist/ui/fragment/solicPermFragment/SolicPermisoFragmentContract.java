package pe.com.dms.movilasist.ui.fragment.solicPermFragment;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface SolicPermisoFragmentContract {

    interface View extends IBaseContract.View {

        void viewInfoUser(Usuario user);

        void cleanViews();

        void viewMessageExitoso();

        void llenarSpinerSolicitud(List<Conceptos> listConceptos);

        void llenarSpinnerSupervisor(List<Supervisor> listSupervisor);

        void closed();
    }

    interface Presenter extends IBaseContract.Presenter<SolicPermisoFragmentContract.View> {

        void obtainUser();

        void obtainConfig();

        void obtainListSolicitud();

        List<Conceptos> getListConceptos();

        void setConcepto(Conceptos concepto);

        void obtainListsupervisor();

        List<Supervisor> getListSupervisor();

        void setSupervisor(Supervisor supervisor);

        void showDatePickerDialog(FragmentManager fragment, final EditText editText, Long dateTime);

        void showTimePickerDialog(FragmentManager fragment, final EditText editText);

        void setDtmFechaInicio(String dtmFechaInicio);

        void setDtmFechaFin(String dtmFechaFin);

        void setVchHoraInicio(String vchHoraInicio);

        void setVchHoraFin(String vchHoraFin);

        void setIntPertenenciaInicio(int intPertenenciaInicio);

        void setIntPertenenciaFin(int intPertenenciaFin);

        void setVchObservacion(String vchObservacion);

        void setIntEstadoSolicitud(int intEstadoSolicitud);

        void validatedConnection();

        void sendSolicPermOffLine();

        void sendSolicPermOnLine();

        boolean validarHora(String horaIni, String horaFin);

        int integracionVAWEB();

        void setEditSolicPermOnLine(SolicitudPermiso solicitudPermiso);

        int positionSuper(Spinner spinner, String codSuper);

        int positionSolicitud(Spinner spinner, String codSolicitud);
    }
}
