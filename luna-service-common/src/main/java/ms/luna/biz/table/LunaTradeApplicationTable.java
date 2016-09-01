package ms.luna.biz.table;

/**
 * Created by SDLL18 on 16/8/25.
 */
public class LunaTradeApplicationTable {

    public static final String TABLE_NAME = "luna_trade_application";

    public static final String FIELD_ID = "application_id";
    public static final String FIELD_CONTACT_NAME = "contactName";
    public static final String FIELD_CONTACT_PHONE = "contactPhone";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_IDCARD_PIC_URL = "idcardPicUrl";
    public static final String FIELD_IDCARD_PERIOD = "idcardPeriod";
    public static final String FIELD_MERCHANT_NAME = "merchantName";
    public static final String FIELD_MERCHANT_PHONE = "merchantPhone";
    public static final String FIELD_MERCHANT_NO = "merchantNo";
    public static final String FIELD_LICENCE_PIC_URL = "licencePicUrl";
    public static final String FIELD_LICENCE_PERIOD = "licencePeriod";
    public static final String FIELD_ACCOUNT_TYPE = "accountType";
    public static final String FIELD_ACCOUNT_NAME = "accountName";
    public static final String FIELD_ACCOUNT_BANK = "accountBank";
    public static final String FIELD_ACCOUNT_CITY = "accountCity";
    public static final String FIELD_ACCOUNT_PROVINCE = "accountProvince";
    public static final String FIELD_ACCOUNT_ADDRESS = "accountAddress";
    public static final String FIELD_ACCOUNT_NO = "accountNo";
    public static final String FIELD_APP_STATUS = "appStatus";
    public static final String FIELD_MERCHANT_ID = "merchantId";


    public static final String FIELD_APP_CHECK_RESULT = "checkResult";

    public static final int ACCOUNT_TYPE_PERSONAL = 0;
    public static final int ACCOUNT_TYPE_COMPANY = 1;

    public static final int APP_STATUS_CHECKING = 0;
    public static final int APP_STATUS_OK = 1;
    public static final int APP_STATUS_REFUSE = 2;

    public static final int APP_CHECK_ACCEPT = 0;
    public static final int APP_CHECK_REFUSE = 1;

}
