package com.framgia.fdms.screen.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.screen.ViewPagerScroll;
import com.framgia.fdms.screen.dashboard.DashboardFragment;
import com.framgia.fdms.screen.device.DeviceFragment;
import com.framgia.fdms.screen.devicedetail.DeviceDetailActivity;
import com.framgia.fdms.screen.profile.ProfileFragment;
import com.framgia.fdms.screen.request.RequestFragment;
import com.framgia.fdms.screen.scanner.ScannerActivity;
import com.framgia.fdms.utils.permission.PermissionUtil;
import com.framgia.fdms.widget.FDMSShowcaseSequence;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.app.Activity.RESULT_OK;
import static com.framgia.fdms.screen.device.DeviceViewModel.Tab.TAB_MANAGE_DEVICE;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DASH_BOARD;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_DEVICE_MANAGER;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_PROFILE;
import static com.framgia.fdms.screen.main.MainViewModel.Tab.TAB_REQUEST_MANAGER;
import static com.framgia.fdms.utils.Constant.BundleConstant.BUNDLE_CONTENT;
import static com.framgia.fdms.utils.Constant.RequestConstant.REQUEST_SCANNER;
import static com.framgia.fdms.utils.permission.PermissionUtil.MY_PERMISSIONS_REQUEST_CAMERA;

/**
 * Exposes the data to be used in the Newmain screen.
 */
public class MainViewModel extends BaseObservable
    implements MainContract.ViewModel, ViewPagerScroll {
    private MainContract.Presenter mPresenter;
    private ViewPagerAdapter mPagerAdapter;
    private int mTab = TAB_DASH_BOARD;
    private AppCompatActivity mActivity;
    private FDMSShowcaseSequence mSequence;
    private boolean mIsShowCase;
    private boolean mIsShowCaseRequest;

    public MainViewModel(AppCompatActivity activity) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DashboardFragment.newInstance());
        fragments.add(RequestFragment.newInstance());
        fragments.add(DeviceFragment.newInstance());
        fragments.add(ProfileFragment.newInstance());
        mPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), fragments);
        mActivity = activity;
        mSequence = new FDMSShowcaseSequence(activity);
        ShowcaseConfig config = new ShowcaseConfig();
        config.setMaskColor(R.color.color_black_transprarent);
        mSequence.setConfig(config);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public ViewPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Bindable
    public int getTab() {
        return mTab;
    }

    public void setTab(int tab) {
        mTab = tab;
        notifyPropertyChanged(BR.tab);
    }

    public void onDirectChildTab(@Tab int tab, ViewPager viewPager) {
        if (mPagerAdapter == null) return;
        viewPager.setCurrentItem(tab);
        switch (tab) {
            case TAB_DASH_BOARD:
                // TODO: 07/07/2017  call onShowCase
                break;
            case TAB_REQUEST_MANAGER:
                if (!isShowCaseRequest()) {
                    ((RequestFragment) mPagerAdapter.getItem(TAB_REQUEST_MANAGER)).onShowCase();
                }
                break;
            case TAB_DEVICE_MANAGER:
                // TODO: 07/07/2017  call onShowCase
                break;
            case TAB_PROFILE:
                // TODO: 07/07/2017  call onShowCase
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_SCANNER
            || resultCode != RESULT_OK
            || data == null
            || data.getExtras() == null) {
            return;
        }
        getResult(data.getExtras().getString(BUNDLE_CONTENT));
    }

    public void onStartScannerQrCode() {
        if (PermissionUtil.checkCameraPermission(mActivity)) {
            startScannerActivity();
        }
    }

    @Override
    public void onGetDecodeSuccess(Device device) {
        mActivity.startActivity(DeviceDetailActivity.getInstance(mActivity, device));
    }

    private void startScannerActivity() {
        mActivity.startActivityForResult(ScannerActivity.newIntent(mActivity), REQUEST_SCANNER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA
            && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScannerActivity();
        } else {
            Snackbar.make(mActivity.findViewById(android.R.id.content),
                R.string.msg_denied_read_camera, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetDeviceError(String error) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
            .show();
    }

    @Override
    public void getResult(String resultQrCode) {
        if (mPresenter == null) return;
        mPresenter.getDevice(resultQrCode);
    }

    @Override
    public void onCurrentPosition(int position) {
        setTab(position);
    }

    @Bindable
    public FDMSShowcaseSequence getSequence() {
        return mSequence;
    }

    public void setSequence(FDMSShowcaseSequence sequence) {
        mSequence = sequence;
        notifyPropertyChanged(BR.sequence);
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public void onShowCaseDashBoard() {
        ((DashboardFragment) mPagerAdapter.getItem(TAB_DASH_BOARD)).onShowCase();
    }

    public boolean isShowCase() {
        return mIsShowCase;
    }

    @Override
    public void setShowCase(boolean showCase) {
        mIsShowCase = showCase;
    }

    public boolean isShowCaseRequest() {
        return mIsShowCaseRequest;
    }

    @Override
    public void setShowCaseRequest(boolean showCaseRequest) {
        mIsShowCaseRequest = showCaseRequest;
    }

    @Override
    public void setTabWithDevice(int tab, Device device) {
        setTab(tab);
        ((DeviceFragment) mPagerAdapter.getItem(tab)).setTabWithDevice(
            TAB_MANAGE_DEVICE, device);
    }

    @IntDef({TAB_DASH_BOARD, TAB_REQUEST_MANAGER, TAB_DEVICE_MANAGER, TAB_PROFILE})
    public @interface Tab {
        int TAB_DASH_BOARD = 0;
        int TAB_REQUEST_MANAGER = 1;
        int TAB_DEVICE_MANAGER = 2;
        int TAB_PROFILE = 3;
    }
}
