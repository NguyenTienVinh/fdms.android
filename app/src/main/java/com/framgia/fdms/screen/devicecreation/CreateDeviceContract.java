package com.framgia.fdms.screen.devicecreation;

import android.content.Intent;
import com.framgia.fdms.BasePresenter;
import com.framgia.fdms.BaseViewModel;
import com.framgia.fdms.data.model.Device;

/**
 * This specifies the contract between the view and the presenter.
 */
interface CreateDeviceContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void showProgressbar();

        void hideProgressbar();

        void onRegisterError();

        void onRegisterSuccess(Device device);

        void onGetDeviceSuccess(Device device);

        void onGetDeviceError(String error);

        void onInputProductionNameError();

        void onInputSerialNumberError();

        void onInputModellNumberError();

        void onInputDeviceCodeError();

        void onInputCategoryError();

        void onInputMeetingRoomError();

        void onInputStatusError();

        void onInputVendorError();

        void onInputMakerError();

        void onInputWarrantyError();

        void onActionDeviceClick();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void onUpdateError(String message);

        void onUpdateSuccess(String device);

        void onPickDateTimeClick();

        void onLoadError(String msg);

        void onInputBoughtDateError();

        void onInputOriginalPriceError();

        void onGetDeviceCodeSuccess(String deviceCode);

        void onPrintClick();

        void setProgressBar(int visibility);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void registerDevice(Device device);

        void updateDevice(Device device);

        void getDeviceById(Device device);

        boolean validateDataInput(Device device);

        void getDeviceCode(int deviceCategoryId, int branchId);

        boolean validateDataEditDevice(Device device);
    }
}
