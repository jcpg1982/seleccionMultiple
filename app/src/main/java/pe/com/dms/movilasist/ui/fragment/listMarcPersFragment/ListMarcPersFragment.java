package pe.com.dms.movilasist.ui.fragment.listMarcPersFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import pe.com.dms.movilasist.annotacion.SelectorType;
import pe.com.dms.movilasist.databinding.FragmentListMarcPersBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.filterModel.ResultListMarcPers;
import pe.com.dms.movilasist.ui.activities.searchActivity.SearchActivity;
import pe.com.dms.movilasist.ui.adapter.fragments.ListMarcPersAdapter.ListMarcPersAdapter;
import pe.com.dms.movilasist.ui.decorations.DividerItemDecoration;
import pe.com.dms.movilasist.ui.fragment.base.BaseToolbarFragment;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;

public class ListMarcPersFragment extends BaseToolbarFragment implements View.OnClickListener,
        IListMarcPersFragmentContract.View,
        ListMarcPersAdapter.OnReloadClickListener {

    public static final String TAG = ListMarcPersFragment.class.getSimpleName();

    private FragmentListMarcPersBinding binding;

    private OnFragmentInteractionListener mFragmentInteractionListener;

    @Inject
    ListMarcPersFragmentPresenter presenter;

    private ResultListMarcPers mResultListMarcPers;
    private ListMarcPersAdapter mAdapter;
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
                            presenter.getMoreListMarcPersOnLine(++page);
                        }
                    }
                }
            };

    public ListMarcPersFragment() {
    }

    public static ListMarcPersFragment newInstance() {
        ListMarcPersFragment fragment = new ListMarcPersFragment();
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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.e(TAG, "onCreate");
        presenter.attachView(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListMarcPersBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        initEvents();
        setupRecyclerView();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.obtainDataUserAndConfig();
        viewListMarcPers(null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: requestCode: " + requestCode
                + ", requestCode: " + requestCode
                + ", data: " + data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.Intent.REQUEST_CODE_LIST_MARC_PERS:

                try {
                    mResultListMarcPers = (ResultListMarcPers) data.getSerializableExtra(
                            Constants.Intent.EXTRA_SELECTOR_FILTER);
                } catch (Error | Exception e) {
                    e.printStackTrace();
                    Utils.showToast(getContext(), e.getMessage());
                    return;
                }
                if (mResultListMarcPers != null) {
                    presenter.setResultListMarcPers(mResultListMarcPers);
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_marc, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getIcon() != null)
                menuItem.setIcon(TintUtils.createTintedDrawable(
                        menuItem.getIcon().mutate(), getResources().getColor(R.color.colorCardLight)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constants.Intent.EXTRA_TITLE,
                        getResources().getString(R.string.filter_list_marc_pers));
                intent.putExtra(Constants.Intent.EXTRA_SELECTOR_TYPE,
                        SelectorType.LIST_MARC_PERS);
                startActivityForResult(intent,
                        Constants.Intent.REQUEST_CODE_LIST_MARC_PERS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onReloadClick() {
        presenter.getMoreListMarcPersOnLine(page);
    }

    private void initEvents() {
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(mLayoutManager);
        binding.recycler.setAdapter(mAdapter);

        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(),
                R.drawable.line_divider_primary, false, true));
        mAdapter = new ListMarcPersAdapter();

        binding.recycler.setAdapter(mAdapter);
        binding.recycler.addOnScrollListener(recyclerViewOnScrollListener);
    }

    @Override
    public void viewListMarcPers(List<Marcaciones> listMarcPers) {
        if (listMarcPers != null && listMarcPers.size() > 0) {
            binding.recycler.setVisibility(View.VISIBLE);
            binding.txtListEmpty.setVisibility(View.GONE);
            mAdapter.setData((ArrayList<Marcaciones>) listMarcPers);
        } else {
            binding.recycler.setVisibility(View.GONE);
            binding.txtListEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ListMarcPersAdapter mAdapter() {
        return mAdapter;
    }
}
