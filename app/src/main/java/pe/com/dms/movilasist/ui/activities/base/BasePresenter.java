package pe.com.dms.movilasist.ui.activities.base;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends IBaseContract.View> implements IBaseContract.Presenter<T> {
    private T view;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public T getView() {
        return view;
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        if (disposables != null) {
            disposables.dispose();
        }
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        return disposables;
    }

}