package pe.com.dms.movilasist.ennum;

public enum NavigationIndex {
    CONFIG(0),
    REG_ASIST(1),
    LIST_MARC(2),
    LIST_MAR_PERS(3),
    SOL_PERM(4),
    LIST_SOL_PERM(5),
    APRO_SOL(6),
    LOGOUT(7);

    private int index;

    NavigationIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}