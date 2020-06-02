package pe.com.dms.movilasist.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface OnFragmentInteractionListener {
    /**
     * Invocado para informar a la actividad que se agregue un fragmento al stack.
     *
     * @param fragment        Frggmento para ser añadido al stack del manejador de fragmentos.
     * @param title           Usado normalmente para el titulo en la Toolbar de la actividad.
     * @param replaceFragment <code>true</code> {@link
     *                        FragmentTransaction#replace(int, Fragment)}. <code>false</code>
     *                        {@link FragmentTransaction#add(Fragment, String)}.
     * @param addToBackStack  <code>true</code> Se agrega al back stack. {@link
     *                        FragmentTransaction#addToBackStack(String)}.
     * @param isAnimated      <code>true</code> Para mostrar una animación.
     * @param tag             Etiqueta para encontrar al fragmento.
     */
    void onAddFragmentToStack(
            @NonNull Fragment fragment,
            CharSequence title,
            CharSequence subTitle,
            boolean replaceFragment,
            boolean addToBackStack,
            boolean isAnimated,
            @NonNull String tag);

    /**
     * Invocado para informar a la actividad que se debe remover un fragmento atravez de {@link
     * FragmentManager#popBackStack()} o ejecutar otras tareas mientras es
     * invocado éste método: Por ejemplo ejecutar el método {@link
     * AppCompatActivity#onBackPressed()}}.
     *
     * @param withBackPressed : <code>true</code> Si desea ejecutar otras tareas aparte de remover el
     *                        fragmento actual del stack.
     */
    void onRemoveFragmentToStack(boolean withBackPressed);
}
