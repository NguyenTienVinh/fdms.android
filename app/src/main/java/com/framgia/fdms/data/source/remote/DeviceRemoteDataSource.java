package com.framgia.fdms.data.source.remote;

import android.text.TextUtils;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.source.DeviceDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.screen.device.listdevice.DeviceFilterModel;
import com.framgia.fdms.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.framgia.fdms.utils.Constant.ApiParram.BOUGHT_DATE;
import static com.framgia.fdms.utils.Constant.ApiParram.CATEGORY_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.DESCRIPTION;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_BRANCH_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_CATEGORY_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_CODE;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_NAME;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_STATUS_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.HARD_DRIVE;
import static com.framgia.fdms.utils.Constant.ApiParram.IS_BAR_CODE;
import static com.framgia.fdms.utils.Constant.ApiParram.IS_MEETING_ROOM;
import static com.framgia.fdms.utils.Constant.ApiParram.MAKER_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.MEETING_ROOM_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.MODEL_NUMBER;
import static com.framgia.fdms.utils.Constant.ApiParram.ORIGINAL_PRICE;
import static com.framgia.fdms.utils.Constant.ApiParram.PAGE;
import static com.framgia.fdms.utils.Constant.ApiParram.PER_PAGE;
import static com.framgia.fdms.utils.Constant.ApiParram.PICTURE;
import static com.framgia.fdms.utils.Constant.ApiParram.PRODUCTION_NAME;
import static com.framgia.fdms.utils.Constant.ApiParram.RAM;
import static com.framgia.fdms.utils.Constant.ApiParram.SERIAL_NUMBER;
import static com.framgia.fdms.utils.Constant.ApiParram.STAFF_USING_NAME;
import static com.framgia.fdms.utils.Constant.ApiParram.STATUS_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.VENDOR_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.WARRANTY;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;

public class DeviceRemoteDataSource implements DeviceDataSource.RemoteDataSource {

    private FDMSApi mFDMSApi;

    public DeviceRemoteDataSource(FDMSApi FDMSApi) {
        mFDMSApi = FDMSApi;
    }

    @Override
    public Observable<List<Device>> getListDevices(DeviceFilterModel filterModel, int page,
        int perPage) {
        return mFDMSApi.getListDevices(getDeviceParams(filterModel, page, perPage))
            .flatMap(new Function<Respone<List<Device>>, ObservableSource<List<Device>>>() {
                @Override
                public ObservableSource<List<Device>> apply(Respone<List<Device>> listRespone)
                    throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    @Override
    public Observable<List<Device>> getListDevices(String deviceName, int categoryId, int statusId,
        int page, int perPage) {
        return null;
    }

    @Override
    public Observable<Device> registerdevice(final Device device) {
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody productionName, deviceStatusId, deviceCategoryId, vendorId, makerId,
            meetingRoomId, serialNumber, modelNumber, deviceCode, isBarcode, isMeetingRoom,
            boughtDate, originalPrice, warranty, ram, hardDrive, description;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        productionName = createPartFromString(device.getProductionName());
        deviceStatusId = createPartFromString(String.valueOf(device.getDeviceStatusId()));
        deviceCategoryId = createPartFromString(String.valueOf(device.getDeviceCategoryId()));
        vendorId = createPartFromString(String.valueOf(device.getVendorId()));
        makerId = createPartFromString(String.valueOf(device.getMarkerId()));
        deviceCode = createPartFromString(device.getDeviceCode());
        isBarcode = createPartFromString(String.valueOf(device.isBarcode()));
        boughtDate = createPartFromString(String.valueOf(format.format(device.getBoughtDate())));
        originalPrice = createPartFromString(device.getOriginalPrice());
        isMeetingRoom = createPartFromString(String.valueOf(device.isDeviceMeetingRoom()));
        if (device.getMeetingRoom().getId() > 0) {
            meetingRoomId = createPartFromString(String.valueOf(device.getMeetingRoom().getId()));
            params.put(MEETING_ROOM_ID, meetingRoomId);
        }
        if (device.getSerialNumber() != null) {
            serialNumber = createPartFromString(device.getSerialNumber());
            params.put(SERIAL_NUMBER, serialNumber);
        }
        if (device.getModelNumber() != null) {
            modelNumber = createPartFromString(device.getModelNumber());
            params.put(MODEL_NUMBER, modelNumber);
        }
        if (device.getWarranty() != null) {
            warranty = createPartFromString(device.getWarranty());
            params.put(WARRANTY, warranty);
        }
        if (device.getRam() != null) {
            ram = createPartFromString(device.getRam());
            params.put(RAM, ram);
        }
        if (device.getHardDriver() != null) {
            hardDrive = createPartFromString(device.getHardDriver());
            params.put(HARD_DRIVE, hardDrive);
        }
        if (device.getDeviceDescription() != null) {
            description = createPartFromString(device.getDeviceDescription());
            params.put(DESCRIPTION, description);
        }
        params.put(PRODUCTION_NAME, productionName);
        params.put(DEVICE_STATUS_ID, deviceStatusId);
        params.put(DEVICE_CATEGORY_ID, deviceCategoryId);
        params.put(VENDOR_ID, vendorId);
        params.put(MAKER_ID, makerId);
        params.put(IS_BAR_CODE, isBarcode);
        params.put(IS_MEETING_ROOM, isMeetingRoom);
        params.put(DEVICE_CODE, deviceCode);
        params.put(BOUGHT_DATE, boughtDate);
        params.put(ORIGINAL_PRICE, originalPrice);

        MultipartBody.Part filePart = null;

        if (device.getPicture() != null && device.getPicture().getUrl() != null) {
            File file = new File(device.getPicture().getUrl());

            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                filePart = MultipartBody.Part.createFormData(PICTURE, file.getName(), requestBody);
            }
        }

        return mFDMSApi.uploadDevice(params, filePart)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
    }

    @Override
    public Observable<String> updateDevice(Device device) {
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody productionName, deviceStatusId, deviceVendorId, deviceMakerId,
            deviceSerialNumber, deviceModelNumber, deviceWarranty, deviceRam, deviceHardDriver,
            deviceDescription, isMeetingRoom, deviceMeetingRoomId, isBarCode;
        if (!TextUtils.isEmpty(device.getProductionName())) {
            productionName = createPartFromString(device.getProductionName());
            params.put(PRODUCTION_NAME, productionName);
        }
        if (device.getDeviceStatusId() > 0) {
            deviceStatusId = createPartFromString(String.valueOf(device.getDeviceStatusId()));
            params.put(DEVICE_STATUS_ID, deviceStatusId);
        }
        if (device.getVendor().getId() > 0) {
            deviceVendorId = createPartFromString(String.valueOf(device.getVendor().getId()));
            params.put(VENDOR_ID, deviceVendorId);
        }
        if (device.getMarker().getId() > 0) {
            deviceMakerId = createPartFromString(String.valueOf(device.getMarker().getId()));
            params.put(MAKER_ID, deviceMakerId);
        }
        if (device.getMeetingRoom().getId() > 0) {
            deviceMeetingRoomId =
                createPartFromString(String.valueOf(device.getMeetingRoom().getId()));
            params.put(MEETING_ROOM_ID, deviceMeetingRoomId);
        }
        if (device.getSerialNumber() != null) {
            deviceSerialNumber = createPartFromString(device.getSerialNumber());
            params.put(SERIAL_NUMBER, deviceSerialNumber);
        }
        if (device.getModelNumber() != null) {
            deviceModelNumber = createPartFromString(device.getModelNumber());
            params.put(MODEL_NUMBER, deviceModelNumber);
        }
        if (device.getWarranty() != null) {
            deviceWarranty = createPartFromString(device.getWarranty());
            params.put(WARRANTY, deviceWarranty);
        }
        if (device.getRam() != null) {
            deviceRam = createPartFromString(device.getRam());
            params.put(RAM, deviceRam);
        }
        if (device.getHardDriver() != null) {
            deviceHardDriver = createPartFromString(device.getHardDriver());
            params.put(HARD_DRIVE, deviceHardDriver);
        }
        if (device.getDeviceDescription() != null) {
            deviceDescription = createPartFromString(device.getDeviceDescription());
            params.put(DESCRIPTION, deviceDescription);
        }
        isMeetingRoom = createPartFromString(String.valueOf(device.isDeviceMeetingRoom()));
        params.put(IS_MEETING_ROOM, isMeetingRoom);
        isBarCode = createPartFromString(String.valueOf(device.isBarcode()));
        params.put(IS_BAR_CODE, isBarCode);

        MultipartBody.Part filePart = null;

        if (device.getPicture() != null && device.getPicture().getUrl() != null) {
            File file = new File(device.getPicture().getUrl());

            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                filePart = MultipartBody.Part.createFormData(PICTURE, file.getName(), requestBody);
            }
        }

        return mFDMSApi.updateDevice(device.getId(), params, filePart)
            .flatMap(new Function<Respone<String>, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(Respone<String> deviceRespone)
                    throws Exception {
                    if (deviceRespone == null) {
                        return Observable.error(new NullPointerException());
                    }
                    if (deviceRespone.isError()) {
                        return Observable.error(
                            new NullPointerException("ERROR" + deviceRespone.getStatus()));
                    }
                    return Observable.just(deviceRespone.getMessage());
                }
            });
    }

    @Override
    public Observable<Respone<String>> deleteDevice(Device device) {
        return mFDMSApi.deleteDevice(device.getId());
    }

    @Override
    public Observable<Device> getDeviceByQrCode(String qrCode) {
        return mFDMSApi.getDeviceByQrCode(qrCode)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
    }

    @Override
    public Observable<List<Dashboard>> getDashboardDevice() {
        return mFDMSApi.getDashboardDevice()
            .flatMap(new Function<Respone<List<Dashboard>>, ObservableSource<List<Dashboard>>>() {
                @Override
                public ObservableSource<List<Dashboard>> apply(Respone<List<Dashboard>> listRespone)
                    throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    @Override
    public Observable<List<DeviceUsingHistory>> getDeviceUsingHistory(String deviceId, int page,
        int perPage) {
        return mFDMSApi.getDeviceUsingHistory(deviceId, page, perPage)
            .flatMap(
                new Function<Respone<List<DeviceUsingHistory>>,
                    ObservableSource<List<DeviceUsingHistory>>>() {
                    @Override
                    public ObservableSource<List<DeviceUsingHistory>> apply(
                        Respone<List<DeviceUsingHistory>> listRespone) throws Exception {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    @Override
    public Observable<List<DeviceHistoryDetail>> getDeviceDetailHistory(int deviceId, int page,
        int perPage) {
        return mFDMSApi.getDeviceDetailHistory(deviceId, page, perPage)
            .flatMap(
                new Function<Respone<List<DeviceHistoryDetail>>,
                    ObservableSource<List<DeviceHistoryDetail>>>() {
                    @Override
                    public ObservableSource<List<DeviceHistoryDetail>> apply(
                        Respone<List<DeviceHistoryDetail>> listRespone) throws Exception {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    @Override
    public Observable<Device> getDevice(int deviceId) {
        return mFDMSApi.getDevice(deviceId)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
    }

    @Override
    public Observable<List<Device>> getTopDevice(int topDevice) {
        return mFDMSApi.getTopDevice(topDevice)
            .flatMap(new Function<Respone<List<Device>>, ObservableSource<List<Device>>>() {
                @Override
                public ObservableSource<List<Device>> apply(Respone<List<Device>> listRespone)
                    throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    @Override
    public Observable<Device> getDeviceCode(int deviceCategoryId, int branchId) {
        return mFDMSApi.getDeviceCode(deviceCategoryId, branchId)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
    }

    @Override
    public Observable<List<Device>> getListDeviceByMeetingRoomId(int meetingRoomId, int page,
        int perPage) {
        return mFDMSApi.getListDeviceByMeetingRoomId(meetingRoomId, page, perPage)
            .flatMap(new Function<Respone<List<Device>>, ObservableSource<List<Device>>>() {
                @Override
                public ObservableSource<List<Device>> apply(
                    @NonNull Respone<List<Device>> listRespone) throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    @Override
    public Observable<List<DeviceUsingHistory>> getUserDevice(String status, String staffEmail,
        int page, int perPage) {
        return mFDMSApi.getUserDevice(status, staffEmail, page, perPage)
            .flatMap(
                new Function<Respone<List<DeviceUsingHistory>>,
                    ObservableSource<List<DeviceUsingHistory>>>() {
                    @Override
                    public ObservableSource<List<DeviceUsingHistory>> apply(
                        Respone<List<DeviceUsingHistory>> listRespone) throws Exception {
                        return Utils.getResponse(listRespone);
                    }
                });
    }

    public Map<String, String> getDeviceParams(DeviceFilterModel filterModel, int page,
        int perPage) {
        Map<String, String> parrams = new HashMap<>();

        if (filterModel.getCategory() != null
            && filterModel.getCategory().getId() != OUT_OF_INDEX) {
            parrams.put(CATEGORY_ID, String.valueOf(filterModel.getCategory().getId()));
        }

        if (filterModel.getStatus() != null && filterModel.getStatus().getId() != OUT_OF_INDEX) {
            parrams.put(STATUS_ID, String.valueOf(filterModel.getStatus().getId()));
        }

        if (!TextUtils.isEmpty(filterModel.getDeviceName())) {
            parrams.put(DEVICE_NAME, String.valueOf(filterModel.getDeviceName()));
        }

        if (!TextUtils.isEmpty(filterModel.getStaffName())) {
            parrams.put(STAFF_USING_NAME, String.valueOf(filterModel.getStaffName()));
        }

        if (filterModel.getVendor() != null && filterModel.getVendor().getId() != OUT_OF_INDEX) {
            parrams.put(VENDOR_ID, String.valueOf(filterModel.getVendor().getId()));
        }

        if (filterModel.getMarker() != null && filterModel.getMarker().getId() != OUT_OF_INDEX) {
            parrams.put(MAKER_ID, String.valueOf(filterModel.getMarker().getId()));
        }

        if (filterModel.getMeetingRoom() != null
            && filterModel.getMeetingRoom().getId() != OUT_OF_INDEX) {
            parrams.put(MEETING_ROOM_ID, String.valueOf(filterModel.getMeetingRoom().getId()));
        }

        if (filterModel.getBranch() != null && filterModel.getBranch().getId() != OUT_OF_INDEX) {
            parrams.put(DEVICE_BRANCH_ID, String.valueOf(filterModel.getBranch().getId()));
        }

        if (page != OUT_OF_INDEX) {
            parrams.put(PAGE, String.valueOf(page));
        }
        if (perPage != OUT_OF_INDEX) {
            parrams.put(PER_PAGE, String.valueOf(perPage));
        }

        return parrams;
    }

    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, partString);
    }
}