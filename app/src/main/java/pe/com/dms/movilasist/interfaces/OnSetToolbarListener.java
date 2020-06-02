package pe.com.dms.movilasist.interfaces;

public interface OnSetToolbarListener {

    /**
     * Cambia el titulo de la toolbar de una actividad.
     *
     * @param title : Titulo para ser seteado en la toolbar.
     */
    void onSetTitle(String title);

    /**
     * Cambia el subtitulo de la toolbar de una actividad.
     *
     * @param subTitle : sub titulo para ser seteado en la toolbar.
     */
    void onSetSubTitle(String subTitle);

    void onShowToolbar();

    void onHideToolbar();
}
