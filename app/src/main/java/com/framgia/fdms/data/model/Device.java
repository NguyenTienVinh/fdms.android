package com.framgia.fdms.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.fdms.BR;
import com.framgia.fdms.R;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

import static com.framgia.fdms.utils.Constant.AVAIABLE;
import static com.framgia.fdms.utils.Constant.BROKEN;
import static com.framgia.fdms.utils.Constant.USING;

/**
 * Created by Age on 4/1/2017.
 */
public class Device extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("device_id")
    private int mDeviceId;
    @Expose
    @SerializedName("device_code")
    private String mDeviceCode;
    @Expose
    @SerializedName("production_name")
    private String mProductionName;
    @Expose
    @SerializedName("branch")
    private String mBranch;
    @Expose
    @SerializedName("device_status_id")
    private int mDeviceStatusId;
    @Expose
    @SerializedName("device_category_id")
    private int mDeviceCategoryId;
    @Expose
    @SerializedName("meeting_room_id")
    private int mMeetingRoomId;
    @Expose
    @SerializedName("meeting_room")
    private Producer mMeetingRoom;
    @Expose
    @SerializedName("picture")
    private Picture mPicture;
    @Expose
    @SerializedName("original_price")
    private String mOriginalPrice;
    @Expose
    @SerializedName("bought_date")
    private Date mBoughtDate;
    @Expose
    @SerializedName("printed_code")
    private String mPrintedCode;
    @Expose
    @SerializedName("is_barcode")
    private boolean mIsBarcode;
    @Expose
    @SerializedName("is_meeting_room")
    private boolean mIsDeviceMeetingRoom;
    @Expose
    @SerializedName("device_status_name")
    private String mDeviceStatusName;
    @Expose
    @SerializedName("device_category_name")
    private String mDeviceCategoryName;
    @Expose
    @SerializedName("serial_number")
    private String mSerialNumber;
    @Expose
    @SerializedName("model_number")
    private String mModelNumber;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("summary")
    private Summary mSummary;
    @Expose
    @SerializedName("user")
    private User mUser;
    @Expose
    @SerializedName("borrow_date")
    private Date mBorrowDate;
    @Expose
    @SerializedName("return_date")
    private Date mReturnDate;

    private boolean mIsSelected;
    @Expose
    @SerializedName("vendor")
    private Producer mVendor;
    @Expose
    @SerializedName("maker")
    private Producer mMarker;
    @Expose
    @SerializedName("vendor_id")
    private int mVendorId;
    @Expose
    @SerializedName("marker_id")
    private int mMarkerId;
    @Expose
    @SerializedName("warranty")
    private String mWarranty;
    @Expose
    @SerializedName("ram")
    private String mRam;
    @Expose
    @SerializedName("hard_driver")
    private String mHardDriver;
    @Expose
    @SerializedName("description")
    private String mDeviceDescription;
    @Expose
    @SerializedName("version")
    private int mVersion;

    public Device() {
    }

    protected Device(Parcel in) {
        mId = in.readInt();
        mDeviceId = in.readInt();
        mDeviceCode = in.readString();
        mProductionName = in.readString();
        mBranch = in.readString();
        mDeviceStatusId = in.readInt();
        mDeviceCategoryId = in.readInt();
        mMeetingRoomId = in.readInt();
        mMeetingRoom = in.readParcelable(Producer.class.getClassLoader());
        mPicture = in.readParcelable(Picture.class.getClassLoader());
        mOriginalPrice = in.readString();
        mPrintedCode = in.readString();
        mIsBarcode = in.readByte() != 0;
        mIsDeviceMeetingRoom = in.readByte() != 0;
        mDeviceStatusName = in.readString();
        mDeviceCategoryName = in.readString();
        mSerialNumber = in.readString();
        mModelNumber = in.readString();
        mStatus = in.readInt();
        mUser = in.readParcelable(User.class.getClassLoader());
        mIsSelected = in.readByte() != 0;
        mVendor = in.readParcelable(Producer.class.getClassLoader());
        mMarker = in.readParcelable(Producer.class.getClassLoader());
        mVendorId = in.readInt();
        mMarkerId = in.readInt();
        mWarranty = in.readString();
        mRam = in.readString();
        mHardDriver = in.readString();
        mDeviceDescription = in.readString();
        mVersion = in.readInt();
    }

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    public void cloneDevice(Device device) {
        setId(device.getId());
        setDeviceId(device.getDeviceId());
        setDeviceCode(device.getDeviceCode());
        setProductionName(device.getProductionName());
        setBranch(device.getBranch());
        setDeviceStatusId(device.getDeviceStatusId());
        setDeviceCategoryId(device.getDeviceCategoryId());
        setPicture(device.getPicture());
        setPrintedCode(device.getPrintedCode());
        setOriginalPrice(device.getOriginalPrice());
        setBoughtDate(device.getBoughtDate());
        setBarcode(device.isBarcode());
        setDeviceStatusName(device.getDeviceStatusName());
        setDeviceCategoryName(device.getDeviceCategoryName());
        setSerialNumber(device.getSerialNumber());
        setModelNumber(device.getModelNumber());
        setStatus(device.getStatus());
        setSummary(device.getSummary());
        setUser(device.getUser());
        setVendor(device.getVendor());
        setMarker(device.getMarker());
        setVendorId(device.getVendorId());
        setMarkerId(device.getMarkerId());
        setMeetingRoomId(device.getMeetingRoomId());
        setDeviceMeetingRoom(device.isDeviceMeetingRoom());
        setMeetingRoom(device.getMeetingRoom());
        setWarranty(device.getWarranty());
        setRam(device.getRam());
        setHardDriver(device.getHardDriver());
        setDeviceDescription(device.getDeviceDescription());
        setVersion(device.getVersion());
    }

    public Device(String deviceCode, String productionName, String deviceCategoryName) {
        mDeviceCode = deviceCode;
        mProductionName = productionName;
        mDeviceCategoryName = deviceCategoryName;
    }

    @Bindable
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
        notifyPropertyChanged(BR.deviceId);
    }

    @Bindable
    public String getDeviceCode() {
        return mDeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        mDeviceCode = deviceCode;
        notifyPropertyChanged(BR.deviceCode);
    }

    @Bindable
    public String getProductionName() {
        return mProductionName;
    }

    public void setProductionName(String productionName) {
        mProductionName = productionName;
        notifyPropertyChanged(BR.productionName);
    }

    @Bindable
    public int getDeviceStatusId() {
        return mDeviceStatusId;
    }

    public void setDeviceStatusId(int deviceStatusId) {
        mDeviceStatusId = deviceStatusId;
        notifyPropertyChanged(BR.deviceStatusId);
    }

    @Bindable
    public int getDeviceCategoryId() {
        return mDeviceCategoryId;
    }

    public void setDeviceCategoryId(int deviceCategoryId) {
        mDeviceCategoryId = deviceCategoryId;
        notifyPropertyChanged(BR.deviceCategoryId);
    }

    @Bindable
    public Picture getPicture() {
        return mPicture;
    }

    public void setPicture(Picture picture) {
        mPicture = picture;
        notifyPropertyChanged(BR.picture);
    }

    @Bindable
    public String getOriginalPrice() {
        return mOriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        mOriginalPrice = originalPrice;
        notifyPropertyChanged(BR.originalPrice);
    }

    @Bindable
    public Date getBoughtDate() {
        return mBoughtDate;
    }

    public void setBoughtDate(Date boughtDate) {
        mBoughtDate = boughtDate;
        notifyPropertyChanged(BR.boughtDate);
    }

    @Bindable
    public String getPrintedCode() {
        return mPrintedCode;
    }

    public void setPrintedCode(String printedCode) {
        mPrintedCode = printedCode;
        notifyPropertyChanged(BR.printedCode);
    }

    @Bindable
    public boolean isBarcode() {
        return mIsBarcode;
    }

    public void setBarcode(boolean barcode) {
        mIsBarcode = barcode;
        notifyPropertyChanged(BR.barcode);
    }

    @Bindable
    public String getDeviceStatusName() {
        return mDeviceStatusName;
    }

    public void setDeviceStatusName(String deviceStatusName) {
        mDeviceStatusName = deviceStatusName;
    }

    @Bindable
    public String getDeviceCategoryName() {
        return mDeviceCategoryName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        mDeviceCategoryName = deviceCategoryName;
        notifyPropertyChanged(BR.deviceCategoryName);
    }

    @Bindable
    public String getSerialNumber() {
        return mSerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        mSerialNumber = serialNumber;
        notifyPropertyChanged(BR.serialNumber);
    }

    @Bindable
    public String getModelNumber() {
        return mModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        mModelNumber = modelNumber;
        notifyPropertyChanged(BR.modelNumber);
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public Summary getSummary() {
        return mSummary;
    }

    public void setSummary(Summary summary) {
        mSummary = summary;
    }

    @Bindable
    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public int getMeetingRoomId() {
        return mMeetingRoomId;
    }

    public void setMeetingRoomId(int meetingRoomId) {
        mMeetingRoomId = meetingRoomId;
        notifyPropertyChanged(BR.meetingRoomId);
    }

    @Bindable
    public int getVendorId() {
        return mVendorId;
    }

    public void setVendorId(int vendorId) {
        mVendorId = vendorId;
        notifyPropertyChanged(BR.vendorId);
    }

    @Bindable
    public int getMarkerId() {
        return mMarkerId;
    }

    public void setMarkerId(int markerId) {
        mMarkerId = markerId;
        notifyPropertyChanged(BR.markerId);
    }

    @Bindable
    public Producer getVendor() {
        return mVendor;
    }

    public void setVendor(Producer vendor) {
        mVendor = vendor;
        notifyPropertyChanged(BR.vendor);
    }

    @Bindable
    public Producer getMarker() {
        return mMarker;
    }

    public void setMarker(Producer marker) {
        mMarker = marker;
        notifyPropertyChanged(BR.marker);
    }

    @Bindable
    public String getWarranty() {
        return mWarranty;
    }

    public void setWarranty(String warranty) {
        mWarranty = warranty;
        notifyPropertyChanged(BR.warranty);
    }

    @Bindable
    public int getResourceId() {
        switch (mDeviceStatusId) {
            case USING:
                return R.drawable.ic_using;
            case AVAIABLE:
                return R.drawable.ic_avaiable;
            case BROKEN:
                return R.drawable.ic_broken;
            default:
                return R.drawable.ic_avaiable;
        }
    }

    @Bindable
    public Date getBorrowDate() {
        return mBorrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        mBorrowDate = borrowDate;
        notifyPropertyChanged(BR.borrowDate);
    }

    @Bindable
    public Date getReturnDate() {
        return mReturnDate;
    }

    public void setReturnDate(Date returnDate) {
        mReturnDate = returnDate;
        notifyPropertyChanged(BR.returnDate);
    }

    @Bindable
    public String getRam() {
        return mRam;
    }

    public void setRam(String ram) {
        mRam = ram;
        notifyPropertyChanged(BR.ram);
    }

    @Bindable
    public String getHardDriver() {
        return mHardDriver;
    }

    public void setHardDriver(String hardDriver) {
        mHardDriver = hardDriver;
        notifyPropertyChanged(BR.hardDriver);
    }

    @Bindable
    public boolean isDeviceMeetingRoom() {
        return mIsDeviceMeetingRoom;
    }

    public void setDeviceMeetingRoom(boolean deviceMeetingRoom) {
        mIsDeviceMeetingRoom = deviceMeetingRoom;
        notifyPropertyChanged(BR.deviceMeetingRoom);
    }

    @Bindable
    public int getVersion() {
        return mVersion;
    }

    public void setVersion(int version) {
        mVersion = version;
        notifyPropertyChanged(BR.version);
    }

    @Bindable
    public String getDeviceDescription() {
        return mDeviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        mDeviceDescription = deviceDescription;
        notifyPropertyChanged(BR.deviceDescription);
    }

    @Bindable
    public String getBranch() {
        return mBranch;
    }

    public void setBranch(String branch) {
        mBranch = branch;
        notifyPropertyChanged(BR.branch);
    }

    @Bindable
    public Producer getMeetingRoom() {
        return mMeetingRoom;
    }

    public void setMeetingRoom(Producer meetingRoom) {
        mMeetingRoom = meetingRoom;
        notifyPropertyChanged(BR.meetingRoom);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeInt(mDeviceId);
        parcel.writeString(mDeviceCode);
        parcel.writeString(mProductionName);
        parcel.writeString(mBranch);
        parcel.writeInt(mDeviceStatusId);
        parcel.writeInt(mDeviceCategoryId);
        parcel.writeInt(mMeetingRoomId);
        parcel.writeParcelable(mMeetingRoom, i);
        parcel.writeParcelable(mPicture, i);
        parcel.writeString(mOriginalPrice);
        parcel.writeString(mPrintedCode);
        parcel.writeByte((byte) (mIsBarcode ? 1 : 0));
        parcel.writeByte((byte) (mIsDeviceMeetingRoom ? 1 : 0));
        parcel.writeString(mDeviceStatusName);
        parcel.writeString(mDeviceCategoryName);
        parcel.writeString(mSerialNumber);
        parcel.writeString(mModelNumber);
        parcel.writeInt(mStatus);
        parcel.writeParcelable(mUser, i);
        parcel.writeByte((byte) (mIsSelected ? 1 : 0));
        parcel.writeParcelable(mVendor, i);
        parcel.writeParcelable(mMarker, i);
        parcel.writeInt(mVendorId);
        parcel.writeInt(mMarkerId);
        parcel.writeString(mWarranty);
        parcel.writeString(mRam);
        parcel.writeString(mHardDriver);
        parcel.writeString(mDeviceDescription);
        parcel.writeInt(mVersion);
    }
}
