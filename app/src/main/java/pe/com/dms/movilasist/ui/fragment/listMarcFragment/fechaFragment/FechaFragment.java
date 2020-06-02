package pe.com.dms.movilasist.ui.fragment.listMarcFragment.fechaFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.FragmentFechaBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.adapter.fragments.ListMarcAdapter.ListMarcAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.fragment.base.BaseMainFragment;

public class FechaFragment extends BaseMainFragment implements View.OnClickListener,
        IFechaFragmentContract.View,
        ListMarcAdapter.OnReloadClickListener {
    public static final String TAG = FechaFragment.class.getSimpleName();

    private FragmentFechaBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    FechaFragmentPresenter presenter;

    private ListMarcAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int page = 1;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener =
            new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!presenter.islastPage()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= presenter.PAGE_SIZE()) {
                            presenter.getMoreListMarcOnLine(++page);
                        }
                    }
                }
            };

    public FechaFragment() {
    }

    public static FechaFragment newInstance() {
        FechaFragment fragment = new FechaFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteractionListener = null;
        presenter.detachView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.e(TAG, "onCreate");
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFechaBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        setupRecyclerView();
        initEvents();
        return rootView;
    }

    private void initEvents() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewListMarc(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void setModelListMarc(ResultListMarc resultListMarc) {
        presenter.setModelListMarc(resultListMarc);
    }

    @Override
    public void viewListMarc(List<Marcaciones> marcacionList) {
        if (marcacionList != null && marcacionList.size() > 0) {
            mAdapter.setData((ArrayList<Marcaciones>) marcacionList);
            binding.txtListEmpty.setVisibility(View.GONE);
            binding.recycler.setVisibility(View.VISIBLE);
        } else {
            binding.txtListEmpty.setVisibility(View.VISIBLE);
            binding.recycler.setVisibility(View.GONE);
        }
    }

    @Override
    public ListMarcAdapter mAdapter() {
        return mAdapter;
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setAdapter(mAdapter);

        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_primary, false, true));
        mAdapter = new ListMarcAdapter();

        binding.recycler.setAdapter(mAdapter);
        binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    public void onReloadClick() {
        presenter.getMoreListMarcOnLine(page);
    }
}
