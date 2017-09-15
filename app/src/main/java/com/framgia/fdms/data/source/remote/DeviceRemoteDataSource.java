package com.framgia.fdms.data.source.remote;

import android.text.TextUtils;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.source.DeviceDataSource;
import com.framgia.fdms.data.source.api.service.FDMSApi;
import com.framgia.fdms.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.framgia.fdms.utils.Constant.ApiParram.BOUGHT_DATE;
import static com.framgia.fdms.utils.Constant.ApiParram.CATEGORY_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_CATEGORY_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_CODE;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_NAME;
import static com.framgia.fdms.utils.Constant.ApiParram.DEVICE_STATUS_ID;
import static com.framgia.fdms.utils.Constant.ApiParram.MODEL_NUMBER;
import static com.framgia.fdms.utils.Constant.ApiParram.ORIGINAL_PRICE;
import static com.framgia.fdms.utils.Constant.ApiParram.PAGE;
import static com.framgia.fdms.utils.Constant.ApiParram.PER_PAGE;
import static com.framgia.fdms.utils.Constant.ApiParram.PICTURE;
import static com.framgia.fdms.utils.Constant.ApiParram.PRODUCTION_NAME;
import static com.framgia.fdms.utils.Constant.ApiParram.SERIAL_NUMBER;
import static com.framgia.fdms.utils.Constant.ApiParram.STATUS_ID;
import static com.framgia.fdms.utils.Constant.NOT_SEARCH;
import static com.framgia.fdms.utils.Constant.OUT_OF_INDEX;

public class DeviceRemoteDataSource implements DeviceDataSource.RemoteDataSource {

    private FDMSApi mFDMSApi;

    public DeviceRemoteDataSource(FDMSApi FDMSApi) {
        mFDMSApi = FDMSApi;
    }

    @Override
    public Observable<List<Device>> getListDevices(String deviceName, int categoryId, int statusId,
        int page, int perPage) {

        return mFDMSApi.getListDevices(
            getDeviceParams(deviceName, categoryId, statusId, page, perPage))
            .flatMap(new Function<Respone<List<Device>>, ObservableSource<List<Device>>>() {
                @Override
                public ObservableSource<List<Device>> apply(Respone<List<Device>> listRespone)
                    throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    @Override
    public Observable<List<Status>> getListCategory() {
        // TODO: replace by call API later
        List<Status> categories = new ArrayList<>();
        return Observable.just(categories);
    }

    @Override
    public Observable<List<Status>> getListStatus() {
        // TODO: replace by call API later
        List<Status> statuses = new ArrayList<>();
        return Observable.just(statuses);
    }

    @Override
    public Observable<Device> registerdevice(final Device device) {
        Map<String, RequestBody> parrams = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        RequestBody productionName = createPartFromString(device.getProductionName());
        RequestBody deviceStatusId =
            createPartFromString(String.valueOf(device.getDeviceStatusId()));
        RequestBody deviceCategoryId =
            createPartFromString(String.valueOf(device.getDeviceCategoryId()));
        RequestBody serialNumber = createPartFromString(device.getSerialNumber());
        RequestBody modellNumber = createPartFromString(device.getModelNumber());
        RequestBody deviceCode = createPartFromString(device.getDeviceCode());
        RequestBody boughtDate =
            createPartFromString(String.valueOf(format.format(device.getBoughtDate())));
        RequestBody originalPrice = createPartFromString(device.getOriginalPrice());

        parrams.put(PRODUCTION_NAME, productionName);
        parrams.put(DEVICE_STATUS_ID, deviceStatusId);
        parrams.put(DEVICE_CATEGORY_ID, deviceCategoryId);
        parrams.put(SERIAL_NUMBER, serialNumber);
        parrams.put(MODEL_NUMBER, modellNumber);
        parrams.put(DEVICE_CODE, deviceCode);
        parrams.put(BOUGHT_DATE, boughtDate);
        parrams.put(ORIGINAL_PRICE, originalPrice);

        MultipartBody.Part filePart = null;

        if (device.getPicture() != null && device.getPicture().getUrl() != null) {
            File file = new File(device.getPicture().getUrl());

            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                filePart = MultipartBody.Part.createFormData(PICTURE, file.getName(), requestBody);
            }
        }

        return mFDMSApi.uploadDevice(parrams, filePart)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
    }

    @Override
    public Observable<Device> updateDevice(Device device) {
        Map<String, RequestBody> parrams = new HashMap<>();
        RequestBody productionName = null, deviceStatusId = null, deviceCategoryId = null,
            deviceCode = null;
        if (!TextUtils.isEmpty(device.getProductionName())) {
            productionName = createPartFromString(device.getProductionName());
            parrams.put(PRODUCTION_NAME, productionName);
        }

        if (device.getDeviceStatusId() != -1) {
            deviceStatusId = createPartFromString(String.valueOf(device.getDeviceStatusId()));
            parrams.put(DEVICE_STATUS_ID, deviceStatusId);
        }

        if (device.getDeviceCategoryId() != -1) {
            deviceCategoryId = createPartFromString(String.valueOf(device.getDeviceCategoryId()));
            parrams.put(DEVICE_CATEGORY_ID, deviceCategoryId);
        }

        if (!TextUtils.isEmpty(device.getDeviceCode())) {
            deviceCode = createPartFromString(device.getDeviceCode());
            parrams.put(DEVICE_CODE, deviceCode);
        }

        MultipartBody.Part filePart = null;

        if (device.getPicture() != null && device.getPicture().getUrl() != null) {
            File file = new File(device.getPicture().getUrl());

            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

                filePart = MultipartBody.Part.createFormData(PICTURE, file.getName(), requestBody);
            }
        }

        return mFDMSApi.updateDevice(device.getId(), parrams, filePart)
            .flatMap(new Function<Respone<Device>, ObservableSource<Device>>() {
                @Override
                public ObservableSource<Device> apply(Respone<Device> deviceRespone)
                    throws Exception {
                    return Utils.getResponse(deviceRespone);
                }
            });
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
    public Observable<List<DeviceUsingHistory>> getDeviceUsingHistory(int deviceId) {
        return mFDMSApi.getDeviceUsingHistory(deviceId)
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
    public Observable<List<DeviceHistoryDetail>> getDeviceDetailHistory(int deviceId) {
        return mFDMSApi.getDeviceDetailHistory(deviceId)
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
    public Observable<List<Status>> getDeviceGroups() {
        return mFDMSApi.getDeviceGroups()
            .flatMap(new Function<Respone<List<Status>>, ObservableSource<List<Status>>>() {

                @Override
                public ObservableSource<List<Status>> apply(
                    @NonNull Respone<List<Status>> listRespone) throws Exception {
                    return Utils.getResponse(listRespone);
                }
            });
    }

    public Map<String, String> getDeviceParams(String deviceName, int categoryId, int statusId,
        int page, int perPage) {
        Map<String, String> parrams = new HashMap<>();
        if (categoryId != OUT_OF_INDEX) {
            parrams.put(CATEGORY_ID, String.valueOf(categoryId));
        }
        if (statusId != OUT_OF_INDEX) {
            parrams.put(STATUS_ID, String.valueOf(statusId));
        }

        if (page != OUT_OF_INDEX) {
            parrams.put(PAGE, String.valueOf(page));
        }
        if (perPage != OUT_OF_INDEX) {
            parrams.put(PER_PAGE, String.valueOf(perPage));
        }
        if (!deviceName.equals(NOT_SEARCH)) {
            parrams.put(DEVICE_NAME, deviceName);
        }
        return parrams;
    }

    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, partString);
    }
}