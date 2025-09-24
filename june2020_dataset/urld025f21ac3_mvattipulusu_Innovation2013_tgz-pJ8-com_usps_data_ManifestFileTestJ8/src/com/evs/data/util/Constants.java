/*
 * Constants.java
 *
 * Author: Nat Meo
 *
 * Change Log:
 * 		03/01/2007 - Jose Rudel R. de Castro
 * 		Updated for R-2006 Rate Case
 *      Changed/updated constants
 * 		Updated 03/11/2007 (Ivan):  Add Rate key for Summary table
 *
 * 		03/21/2007 - Ivan Sutanto
 * 			Add more constant values for EVS_EXTRA_SERVICES_CODE
 *
 * 		09-04-2007
 * 			- Add more for CR 20973, 20974
 *
 * 		10-15-2007 - 14.0 RF3.2 Add Special Service Count as Constant
 *
 * 		11/06/2007 REQ 13.2 :- A Gandhi
 * 		 - Added STATS Extra Special Service Constants.
 *
 * 		11/06/2007 14.0 RF3.2 Add More constant
 *
 *			04/06/2008 REL 16.1.0
 * Various constant values that are used in multiple locations in code.
 *
 * 		09/02/2008 REL18.0.0
 *
 * 		10/16/2008 REL18.0.0-DiscountSurcharge (Ivan)
 *
 * 		01-14-2009 REL19.0.0
 *
 */

package com.evs.data.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants
{

    //PDA file version :: 16.0 Release
	public static final String EVS_VERSION_1 = "1.2";
	public static final String EVS_VERSION_2 = "2.0";
 	public static final double SA_WEIGHT_LIMIT = 0.8125;
 	public static final double PM_WEIGHT_LIMIT = 0.9999;
    //Pankaj Gupta - For release 17.0
        public static final double MIN_PARCEL_WEIGHT_FOR_PRS = 0.2187;

    public static final String PHEONIX_ZIP = "850";
    public static final int GRACE_PERIOD = 4;

    // the system types custoemrs can be members of
    public static final int EVS = 1;
    public static final int PRS = 2;
    public static final int SBP = 3;

    // the delimiter character used in confirmation/error/warning files
    public static final String CEW_DELIM = ",";

    // the number of servers to try to connect to before failing
    public static final int MAX_SERVER_RETRY = 3;

    // indicators of types of records
    public static final int RECORD_NONE = 0;
	public static final int RECORD_BAD_HEADER = -1;
    public static final int RECORD_HEADER = 1;
    public static final int RECORD_DETAIL = 2;
	public static final int RECORD_DETAIL_2 = 3;
	public static final int RECORD_DETAIL_3 = 4;
	public static final int RECORD_DETAIL_4 = 5;
	public static final int RECORD_CONTAINER = 6;

    // Inter/intra BMC constants
    public static final String DEST_RATE_INTER_EVS = "E";
    public static final String DEST_RATE_INTRA_EVS = "T";
    public static final String DEST_RATE_INTER_WWS = "P";
    public static final String DEST_RATE_INTRA_WWS = "Q";

    // facility type indicators
    public static final String FACILITY_DDU = "1";
    public static final String FACILITY_SCF = "2";
    public static final String FACILITY_BMC = "3";
    public static final String FACILITY_ASF = "4";

    // Jose Rudel R. de Castro - Updated for R-2006 Rate Case
    // mail class indicators
    // eVS Mail Class
	public static final String CLASS_FIRST_CLASS_MAIL = "FC";
	public static final String CLASS_PRIORITY_MAIL = "PM";
	public static final String CLASS_STANDARD_MAIL = "SA";
	public static final String CLASS_STANDARD_MAIL_REGULAR = "SA";
	public static final String CLASS_STANDARD_MAIL_NONPROFIT = "S2";
	public static final String CLASS_STANDARD_MAIL_MARKETING_NONPROFIT = "SM";
	public static final String CLASS_STANDARD_MAIL_ENHANCED_CARRIER_ROUTE = "S3";
	public static final String CLASS_STANDARD_MAIL_NONPROFIT_ENHANCED_CARRIER_ROUTE = "S4";
    public static final String CLASS_PARCEL_POST = "BP";
    public static final String CLASS_PARCEL_SELECT = "PS";
	public static final String CLASS_BOUND_PRINTED_MATTER = "BB";
    public static final String CLASS_MEDIA_MAIL = "BS";
    public static final String CLASS_LIBRARY_MAIL = "BL";
	public static final String CLASS_EXPRESS_MAIL = "EX";
	
	//REL31.0.0 International Mail classes
	public static final String INTL_FIRST_CLASS_MAIL = "LC";
	public static final String INTL_CLASS_PRIORITY_MAIL = "CP";
	public static final String INTL_CLASS_EXPRESS_MAIL = "IE";
	public static final String INTL_CLASS_GLOBAL_EXPRESS_GAURANTEED = "PG";
	// eVS mail class international SRS 228 Rel 31.0.0
	public static final String CLASS_FIRST_CLASS_MAIL_INTL = "LC";
	public static final String CLASS_PRIORITY_MAIL_INTL = "CP";
	public static final String CLASS_EXPRESS_MAIL_INTL = "IE";
	public static final String CLASS_GLOBAL_EXPRESS_GUARANTEED_INTL = "PG";
	public static final String RATE_INDICATOR_FCMI="SP";
	public static final String BLOCKNAME_FCMI="PACKAGES";
	
    //PRS Mail Class
    public static final String CLASS_PARCEL_SELECT_RETURN = "RP";
    public static final String CLASS_INTER_RBMC_PARCEL_SELECT_RETURN = "InterRbmcRP";
    public static final String CLASS_BOUND_PRINTED_RETURN = "RB";
    public static final String CLASS_PRIORITY_RETURN = "MR";
	public static final String CLASS_CRITICAL_PRIORITY_MAIL = "CM";

    //Jose Rudel R. de Castro - Added Postal Routing Barcode for R-2006 Rate Case
    //postal routing barcode
    public static final String POSTAL_ROUTING_BARCODE_NONE = "0";
    public static final String POSTAL_ROUTING_BARCODE_UCC_EAN = "1";
    public static final String POSTAL_ROUTING_BARCODE_POSTNET = "2";

    // mail class indicators for the Wizard Web Service
    public static final String CLASS_WWS_PARCEL_POST = "PP";
    public static final String CLASS_WWS_BOUND_PRINTED_MATTER = "BP";
    public static final String CLASS_WWS_MEDIA_MAIL = "ML";
    public static final String CLASS_WWS_STANDARD_MAIL = "SM";
    public static final String CLASS_WWS_STANDARD_MAIL_NON_PROFIT = "S2";
    public static final String CLASS_WWS_PRIORITY_MAIL = "PM";
    public static final String CLASS_WWS_FIRST_CLASS = "FC";
	public static final String CLASS_WWS_EXPRESS_MAIL = "EX"; // REL20.0
    public static final String CLASS_WWS_TEMP_STANDARD_MAIL = "SR"; /* REL 26.0 */
    public static final String CLASS_WWS_TEMP_STANDARD_MAIL_NON_PROFIT = "NP"; /* REL 26.0 */

    // Jose Rudel R. de Castro - Added for R-2006 Rate Case
    // processing category and shape indicator
    public static final String PROCESSING_CATEGORY_MACHINABLE_PARCELS = "3";
	public static final String PROCESSING_CATEGORY_IRREGULAR_PARCELS = "4";
	public static final String PROCESSING_CATEGORY_NONMACHINABLE_PARCELS = "5";
	public static final String PROCESSING_CATEGORY_NOTFLAT_MACHINABLE_GREATER_OR_EQUAL_TO_6OUNCES = "6";
	public static final String PROCESSING_CATEGORY_NOTFLAT_MACHINABLE_LESS_THAN_6OUNCES = "7";
	public static final String PROCESSING_CATEGORY_PRIORITY_MAIL = "9";
	public static final String PROCESSING_CATEGORY_FLATS = "2"; /* REL 26.0 */
	public static final String PROCESSING_CATEGORY_LETTER = "1"; /* REL 26.0 */
	public static final String PROCESSING_CATEGORY_OD = "O"; /* REL 26.0 */

    // processing categories
    public static final String PROCESSING_CATEGORY_EVS_MACHINABLE = "3";
    public static final String PROCESSING_CATEGORY_EVS_IRREGULAR = "4";
    public static final String PROCESSING_CATEGORY_EVS_NONMACHINABLE = "5";
    public static final String PROCESSING_CATEGORY_EVS_PARCEL_FIRSTCLASS = "6";
	public static final String PROCESSING_CATEGORY_EVS_EXPRESS_MAIL_REG = "8"; // REL21.0.0

    // processing categories for the Wizard Web Service
    public static final String PROCESSING_CATEGORY_WWS_MACHINABLE = "MP";
    public static final String PROCESSING_CATEGORY_WWS_IRREGULAR = "IR";
    public static final String PROCESSING_CATEGORY_WWS_OUTSIDE_PARCEL = "OS";

	// 16.0 Sortation Level [IMD VER 2.0]
	public static final String SORTATION_LEVEL_3DIGIT = "3D";
	public static final String SORTATION_LEVEL_5DIGIT = "5D";
	public static final String SORTATION_LEVEL_BMC = "BM";
	public static final String SORTATION_LEVEL_MIXED_BMC = "MB";
	public static final String SORTATION_LEVEL_ADC = "AD";
	public static final String SORTATION_LEVEL_MIXED_ADC = "MA";
	public static final String SORTATION_LEVEL_PRESORTED = "PR";
	public static final String SORTATION_LEVEL_CARRIER_ROUTE = "CR";
	public static final String SORTATION_LEVEL_CR_BASIC = "CB";
	public static final String SORTATION_LEVEL_HIGH_DENSITY = "CH";
	public static final String SORTATION_LEVEL_CR_SATURATION = "CS";
	public static final String SORTATION_LEVEL_INTRA_BMC_SP = "1A";
	public static final String SORTATION_LEVEL_INTER_BMC_SP = "1E";
	public static final String SORTATION_LEVEL_SINGLE_PIECE = "SP";
	public static final String SORTATION_LEVEL_NA = "NA";
	// 26.0 Sortation Level [IMD VER 2.0]
	public static final String SORTATION_LEVEL_BASIC = "BA";
	public static final String SORTATION_LEVEL_NON_PROFIT_3D = "N3"; //
	public static final String SORTATION_LEVEL_NON_PROFIT_5D = "N5";
	public static final String SORTATION_LEVEL_NON_PROFIT_SCF = "NT";
	public static final String SORTATION_LEVEL_NON_PROFIT_NDC = "ND";
	public static final String SORTATION_LEVEL_NON_PROFIT_MIXED_NDC = "NM";
	public static final String SORTATION_LEVEL_NON_PROFIT_ADC = "NC";
	public static final String SORTATION_LEVEL_NON_PROFIT_MIXED_ADC = "NX";
	public static final String SORTATION_LEVEL_NON_PROFIT_CR_BASIC = "NB";
	public static final String SORTATION_LEVEL_NON_PROFIT_CR_HIGH_DENSITY = "NH";
	public static final String SORTATION_LEVEL_NON_PROFIT_CR_SATURATION = "NR";


    // 16.0 shape indicators [IMD VER 2.0]
	public static final String SHAPE_OVERSIZE = "OS";
    public static final String SHAPE_BALLOON = "BN";
	public static final String SHAPE_BALLON_3D = "B3";
	public static final String SHAPE_FLAT_RATE_ENV = "FE";
	public static final String SHAPE_FLAT_RATE_BOX = "FB";
	public static final String SHAPE_LARGE_FLAT_RATE_BOX = "PL";
	public static final String SHAPE_LARGE_FLAT_RATE_BOX_MILITARY = "PM";
	public static final String SHAPE_SMALL_FLAT_RATE_BOX = "FS"; //REL18.0.0
	public static final String SHAPE_METRO_LETTER = "ML";
	public static final String SHAPE_DIMENSIONAL_RECTANGULAR = "DR";
	public static final String SHAPE_DIMENSIONAL_NONRECTANGULAR = "DN";
	public static final String SHAPE_INTRA_BMC_BN = "2A";
	public static final String SHAPE_INTER_BMC_BN = "2E";
	public static final String SHAPE_INTRA_BMC_OS = "3A";
	public static final String SHAPE_INTER_BMC_OS = "3E";
	public static final String SHAPE_BASIC = "BA";
	public static final String SHAPE_POST_OFFICE_TO_ADD = "PA";
	public static final String SHAPE_POST_OFFICE_ADDRESSEE_FLAT_RATE_ENV = "E4";
	public static final String SHAPE_FLAT_RATE_ENV_HOLD_FOR_PICKUP = "E3";
    public static final String SHAPE_RESIDUAL = "RS";
    public static final String SHAPE_NORMAL = "NA";
	public static final String SHAPE_FLAT_RATE_PADDED_ENV = "FP";
    // 26.0 shape indicators [IMD VER 2.0]
	public static final String SHAPE_FLAT_RATE_ENV_LEGAL_HOLD_FOR_PICKUP = "E5";
	public static final String SHAPE_POST_OFFICE_ADDRESSEE_FLAT_RATE_ENV_LEGAL = "E6";
	public static final String SHAPE_CRITICAL_MAIL_LETTERS = "AL";
	public static final String SHAPE_CRITICAL_MAIL_FLATS = "AF";
	public static final String SHAPE_FLAT_RATE_LEGAL_ENV = "FA";
	public static final String SHAPE_REGIONAL_RATE_BOX_A = "C6";
	public static final String SHAPE_REGIONAL_RATE_BOX_B = "C7";

	// permit types
    public static final String PERMIT_TYPE_EVS = "PI";
    public static final String PERMIT_TYPE_PRS = "BR";

    // used to determine if standard mail pieces are above or below
    // the break weight.
    public static final double STANDARD_MAIL_BREAK_WEIGHT = 0.20630000000000001D;
	public static final double PARCEL_POST_SELECT_BREAK_WEIGHT = 35.00;

    // weight indicators used for the WWS
    public static final String WEIGHT_INDICATOR_ABOVE = "A";
    public static final String WEIGHT_INDICATOR_BELOW = "B";
    public static final String WEIGHT_INDICATOR_NONE = "N";

    // indicators of filtered types
    public static final int FILTERED_NONE = 0;
    public static final int FILTERED_STC = 1;
    public static final int FILTERED_MAIL_CLASS = 2;
    public static final int FILTERED_MISSING = 3;
    public static final int FILTERED_RATE = 4;
	public static final int FILTERED_PIC_CODE = 5;
	public static final int FILTERED_SPECIAL_SERVICE_COMBINATION = 6;
	public static final int FILTERED_INVALID_CORP_DUNS = 7;
	public static final int FILTERED_INELIGIBLE_PIC = 9;
	public static final int FILTERED_INVALID_PERMIT = 10;
	public static final int FILTERED_DETAIL_TOO_SHORT = 11;
	public static final int FILTERED_INVALID_BARCODE_CONSTRUCT = 12;
	public static final int FILTERED_INVALID_PAYMENT_METHOD = 13;
	public static final int FILTERED_INVALID_COUNTRY_CODE = 14;
	public static final int FILTERED_MISSING_MAIL_OWNER_MID = 15;
	public static final int FILTERED_OVERLABEL = 16;
	public static final int FILTERED_DETAIL_LENGTH_INCORRECT = 17;
	public static final int FILTERED_INELIGIBLE_PIC_OVERLABEL = 18;
	//REL 26.0.0 code matches those in E_RPT_CODE_REF and E_ERROR_WARNING_MAP tables
    public static final int WARNING_STC = 1;
	public static final int WARNING_PIC_CODE = 5;
	public static final int WARNING_CONTAINER_TYPE = 3;
	public static final int WARNING_NON_INCIDENTAL_ENCLOSURE = 4;
	public static final int WARNING_ZIP_MISMATCH = 8;
	public static final int WARNING_POBOX_INDICATOR = 14;
	public static final int WARNING_WAIVER_SIGNATURE = 15;
	public static final int WARNING_DELIVERY_OPTION_IND = 16;
	public static final int WARNING_DESTINATION_DELIVERY_POINT = 17;
	public static final int WARNING_POSTAL_CODE = 18;
	public static final int WARNING_POSTAL_CODE_INVALID = 19;
	public static final int WARNING_INVALID_ORIGIN_COUNTRY_CODE = 20;
	public static final int WARNING_NON_ALPHA_APP_ID = 21;
	public static final int WARNING_INVALID_APPLICATION_IDENTIFIER = 22;

	// REL21.0.0 filtered header types
	public static final int FILTERED_HEADER_DUP = 1;
	public static final int FILTERED_HEADER_MAILER_ID = 2;
	public static final int FILTERED_HEADER_PERMIT = 3;
	public static final int FILTERED_HEADER_VERSION = 4;
	public static final int FILTERED_HEADER_FORMAT = 5;
	public static final int FILTERED_HEADER_OTHER = 6;
	public static final int FILTERED_CONTAINER_FORMAT = 7;

    // number of columns used for multihashtables
    //REL18.0.0-DiscountSurcharge
	/* REL 26.0 adding Price Type in the grouping */
    public static final int TABLE_COLUMNS_POSTAGE = 16; /* REL 26. 0 */
    public static final int TABLE_COLUMNS_SERVICES = 9; //REL24.0.0
    public static final int TABLE_COLUMNS_STATUS = 6; //REL24.0.0
    public static final int TABLE_COLUMNS_EVS_MAP = 4;
    public static final int TABLE_COLUMNS_PRS_MAP = 6; // REL22.0 Include PriceType
    public static final int TABLE_COLUMNS_PRS_TOTAL = 7; //REL24.0.0

	public static final int TABLE_COLUMNS_POSTAGE_INTER = 18; /* REL 26.0 */
	public static final int TABLE_COLUMNS_SERVICES_INTER = 11; /* REL 26.0 */
	public static final int TABLE_COLUMNS_PRS_TOTAL_INTER = 9; //REL25.0

    // monthly adjustment types
    public static final int ADJUSTMENT_EVS_POSTAGE = 1;
    public static final int ADJUSTMENT_PRS_POSTAGE = 2;
    public static final int ADJUSTMENT_EVS_MISSHIPPED = 3;
    public static final int ADJUSTMENT_EVS_UNMANIFESTED = 4;
    public static final int ADJUSTMENT_PRS_DOUBLE_PAYMENT = 5;
    public static final int ADJUSTMENT_EVS_MANIFEST_ERROR = 13;
	public static final int ADJUSTMENT_EVS_SHIPMENTFEE = 14;
	public static final int ADJUSTMENT_EVS_DUP = 15;
	public static final int ADJUSTMENT_EVS_ADHOC = 16;
	public static final int ADJUSTMENT_EVS_PRESORT = 17;
	public static final int ADJUSTMENT_EVS_CONTENT = 18;
	public static final int ADJUSTMENT_EVS_DIM_WT_AVG  = 19;
	public static final int ADJUSTMENT_EVS_DRI_EXCEPTION = 20;

    // status codes for monthly adjustment transactions
    public static final int ADJUSTMENT_STATUS_NONE = 0;
    public static final int ADJUSTMENT_STATUS_PENDING = 1;
    public static final int ADJUSTMENT_STATUS_PROCESSED = 2;
    public static final int ADJUSTMENT_STATUS_ERROR = 3;

    // status codes for reconciliation extract
    public static final int RECON_EXTRACT_STATUS_NONE = 0;
    public static final int RECON_EXTRACT_STATUS_NOTICE_1 = 1;
    public static final int RECON_EXTRACT_STATUS_NOTICE_2 = 2;
    public static final int RECON_EXTRACT_STATUS_ADVICE = 3;

    // key indexes for rate combination summary
    public static final int RATE_KEY_MAIL_CLASS = 0;
    public static final int RATE_KEY_DESTINATION_RATE_IND = 1;
    public static final int RATE_KEY_RATE_IND = 2;
    public static final int RATE_KEY_PROCESSING_CATEGORY = 3;
    public static final int RATE_KEY_ZONE = 4;
    public static final int RATE_KEY_WEIGHT_IND = 5;
    /* Added for Routing Barcode (03/11/2007 -- Ivan) */
	public static final int RATE_KEY_ROUTNG_BARCODE = 6;
	// REL18.0.0-DiscountSurcharge
	public static final int RATE_KEY_DISCOUNT_SURCHARGE = 7;
	public static final int RATE_KEY_TIER = 8;

    //REL22.0
	public static final int RATE_KEY_PRICE_TYPE = 5;
	/* REL 26.0 */
	public static final int RATE_KEY_PRICE_TYPE_GRP = 9;
	public static final int RATE_KEY_FORM_TYPE = 10;
	public static final int RATE_KEY_WWS_PROCESSING_CATEGORY = 11;
	//REL24.0
	public static final int RATE_KEY_PAYMENT_ACCT_NO = 12;
	public static final int RATE_KEY_PAYMENT_METHOD = 13;
	public static final int RATE_KEY_PO_OF_ACCT_ZIP_CODE = 14;

	public static final int RATE_KEY_PS_GROUP_NO = 15;
	public static final int RATE_KEY_MAIN_EFN_INTER = 16;
	public static final int RATE_KEY_SUB_EFN_INTER = 17;

    // zone type
    public static final String ZONE_V = "V";
    public static final String ZONE_W = "W";
    public static final String ZONE_X = "X";
    public static final String ZONE_Y = "Y";

    // LINE SPECIFICATION
	public static final int SERVICE_CODE_1_START = 79;
	public static final int SERVICE_CODE_1_END = 81;
	public static final int SERVICE_RATE_1_START = 81;
	public static final int SERVICE_RATE_1_END = 86;

	public static final int SERVICE_CODE_2_START = 86;
	public static final int SERVICE_CODE_2_END = 88;
	public static final int SERVICE_RATE_2_START = 88;
	public static final int SERVICE_RATE_2_END = 93;

	public static final int SERVICE_CODE_3_START = 93;
	public static final int SERVICE_CODE_3_END = 95;
	public static final int SERVICE_RATE_3_START = 95;
	public static final int SERVICE_RATE_3_END = 100;

	// Jose Rudel R. de Castro - Added for R-2006 Rate Case
	public static final String PDA_FILE_TYPE = "eVS1";


    // Jose Rudel R. de Castro - Updated for R-2006 Rate Case
    // The number of place holders for Service Code/Service Rate has been reduced from 6 to 3
	// to allow room for the dimensional weighting criteria (positions 101 - 121).
//	public static final int SERVICE_CODE_4_START = 100;
//	public static final int SERVICE_CODE_4_END = 102;
//	public static final int SERVICE_RATE_4_START = 102;
//	public static final int SERVICE_RATE_4_END = 107;
//
//	public static final int SERVICE_CODE_5_START = 107;
//	public static final int SERVICE_CODE_5_END = 109;
//	public static final int SERVICE_RATE_5_START = 109;
//	public static final int SERVICE_RATE_5_END = 114;
//
//	public static final int SERVICE_CODE_6_START = 114;
//	public static final int SERVICE_CODE_6_END = 116;
//	public static final int SERVICE_RATE_6_START = 116;
//	public static final int SERVICE_RATE_6_END = 121;

	public static final int CEW_HEADER = 1;
	public static final int CEW_DETAIL = 2;

	// Jose Rudel R. de Castro - Added for R-2006 Rate Case
	// dimensional weighting
	public static final String DIM_WEIGHT_RECTANGULAR = "DR";
	public static final String DIM_WEIGHT_NONRECTANGULAR = "DN";
	public static final double DIM_WEIGHT_CRITERIA = 1728;
	public static final double DIM_WEIGHT_DIVISOR = 194;
	public static final double DIM_WEIGHT_MULTIPLIER = 0.785;

	public static final String EVS_EXTRA_SPECIAL_SERVICE_DC = "01";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_SC = "02";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_CM = "03";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_IM = "04";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_COD = "05";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_RR = "06";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_RRM = "07";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_RD = "08";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_SHL10 = "11";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_SHM10 = "12";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_BI = "13";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_EXPRESSMAIL_IM = "19"; //REL21.0

	public static final String EVS_EXTRA_SPECIAL_SERVICE_RD_V15 = "950";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_RR_V15 = "955";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_RRE_V15 = "957";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_EXPRESSMAIL_IM_V15 = "925"; //REL21.0
	public static final String EVS_EXTRA_SPECIAL_SERVICE_CM_V15 = "910";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_IM_V15_1 = "930";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_IM_V15_2 = "931";
	public static final String EVS_EXTRA_SPECIAL_SERVICE_COD_V15 = "915";

	//13.2 STATS Extra Special Services.
	public static final String STATS_EXTRA_SERVICE_INSURED = "01";
	public static final String STATS_EXTRA_SERVICE_COD = "02";
	public static final String STATS_EXTRA_SERVICE_RETURN_RECEIPT = "08";
	public static final String STATS_EXTRA_SERVICE_RISTRICTED_DELIVERY  = "13";
	public static final String STATS_EXTRA_SERVICE_DELIVERY_CONF  = "19";
	public static final String STATS_EXTRA_SERVICE_SIGNATURE_CONF  = "21";

	public static double MAX_BALLOON_WEIGHT = 20.0;

	// Old Rate IND
	public static final String RATE_INDICATOR_INTERBMC_BALLOON = "2E";
	public static final String RATE_INDICATOR_INTERBMC_SINGLE = "1E";
	public static final String RATE_INDICATOR_INTRABMC_BALLOON = "2A";
	public static final String RATE_INDICATOR_INTRABMC_SINGLE = "1A";

	// Rate Indicators (Appendix G: Table G-4) Release 16.0
	public static final String RATE_INDICATOR_INTRA_BMC_SP = "1A";
	public static final String RATE_INDICATOR_INTER_BMC_SP = "1E";
	public static final String RATE_INDICATOR_INTRA_BMC_BN = "2A";
	public static final String RATE_INDICATOR_INTER_BMC_BN = "2E";
	public static final String RATE_INDICATOR_INTRA_BMC_OS = "3A";
	public static final String RATE_INDICATOR_INTER_BMC_OS = "3E";
	public static final String RATE_INDICATOR_5D = "5D";
	public static final String RATE_INDICATOR_3D = "3D";
	public static final String RATE_INDICATOR_AD = "AD";
	public static final String RATE_INDICATOR_BALLOON_3D = "B3";
	public static final String RATE_INDICATOR_BALLOON_5D = "BN";
	public static final String RATE_INDICATOR_BASIC_RATE = "BA";
	public static final String RATE_INDICATOR_BMC = "BM";
	public static final String RATE_INDICATOR_BALLOON = "BN";
	public static final String RATE_INDICATOR_CARRIER_ROUTE_BASIC = "CB";
	public static final String RATE_INDICATOR_CARRIER_ROUTE_HIGH_DENSITY = "CH";
	public static final String RATE_INDICATOR_CARRIER_ROUTE = "CR";
	public static final String RATE_INDICATOR_CARRIER_ROUTE_SATURATION = "CS";
	public static final String RATE_INDICATOR_DIMENSIONAL_RECTANGULAR = "DR";
	public static final String RATE_INDICATOR_DIMENSIONAL_NONRECTANGULAR = "DN";
	public static final String RATE_INDICATOR_FLAT_RATE_BOX = "FB";
	public static final String RATE_INDICATOR_FLAT_RATE_ENV = "FE";
	public static final String RATE_INDICATOR_LARGE_FLAT_RATE_BOX = "PL";
	public static final String RATE_INDICATOR_FLAT_RATE_BOX_MILITARY = "PM";
	public static final String RATE_INDICATOR_SMALL_FLAT_RATE_BOX = "FS";
	public static final String RATE_INDICATOR_MIXED_BMC = "MB";
	public static final String RATE_INDICATOR_MIXED_ADC = "MA";
	public static final String RATE_INDICATOR_NON_PRESORTED = "NP";
	public static final String RATE_INDICATOR_OVERSIZED = "OS";
	public static final String RATE_INDICATOR_PRESORTED = "PR";
	public static final String RATE_INDICATOR_SINGLE_PIECE = "SP";
	public static final String RATE_INDICATOR_EX_FLAT_RATE_ENV = "E4"; // REL21.0.0
	public static final String RATE_INDICATOR_EX_SINGLE_PIECE = "PA";    // REL21.0.0
	public static final String RATE_INDICATOR_EX_FLAT_RATE_ENV_PICKUP = "E3"; // REL21.0.0
	public static final String RATE_INDICATOR_EX_SINGLE_PIECE_PICKUP = "PP";    // REL21.0.0
	public static final String RATE_INDICATOR_EX_LEGAL_FLAT_RATE_ENV_PICKUP = "E5"; // REL 26.0.0
	public static final String RATE_INDICATOR_EX_LEGAL_FLAT_RATE_ENV = "E6"; // REL 26.0.0
	public static final String RATE_INDICATOR_EX_FLAT_RATE_HOLD_FOR_PICKUP = "E9"; // REL 26.0.0
	public static final String RATE_INDICATOR_EX_FLAT_RATE_PO_TO_ADDRESSEE = "E8"; // REL 29.0.0
	public static final String RATE_INDICATOR_EX_LEGAL_FLAT_RATE_ENV_SUN_HOL_DELIVERY = "E7"; // REL 29.0.0
	public static final String RATE_INDICATOR_EX_FLAT_RATE_BOX_SUN_HOL_DELIERY = "EE"; // REL 29.0.0
	public static final String RATE_INDICATOR_FLAT_RATE_PADDED_ENV = "FP";    // REL23.0
	public static final String RATE_INDICATOR_CUBIC_PRICING = "CP";    // REL23.0
	public static final String RATE_INDICATOR_SOFTPACK_CUBIC_TIER_1 = "P5";    // REL29.0
	public static final String RATE_INDICATOR_SOFTPACK_CUBIC_TIER_2 = "P6";    // REL29.0
	public static final String RATE_INDICATOR_SOFTPACK_CUBIC_TIER_3 = "P7";    // REL29.0
	public static final String RATE_INDICATOR_SOFTPACK_CUBIC_TIER_4 = "P8";    // REL29.0
	public static final String RATE_INDICATOR_SOFTPACK_CUBIC_TIER_5 = "P9";    // REL29.0
	public static final String RATE_INDICATOR_FULL_TRAY_BOX = "O1"; // REL29.0
	public static final String RATE_INDICATOR_HALF_TRAY_BOX = "O2"; // REL29.0
	public static final String RATE_INDICATOR_EMM_TRAY_BOX = "O3"; // REL29.0
	public static final String RATE_INDICATOR_FLAT_TUB_TRAY_BOX = "O4"; // REL29.0
	public static final String RATE_INDICATOR_PALLET = "O5"; // REL29.0
	public static final String RATE_INDICATOR_FULL_PALLET_BOX = "O6"; // REL29.0
	public static final String RATE_INDICATOR_HALF_PALLET_BOX = "O7"; // REL29.0
	public static final String RATE_INDICATOR_CUBIC_LITE_TIER_1 = "C6";    // REL26.0
	public static final String RATE_INDICATOR_CUBIC_LITE_TIER_2 = "C7";    // REL26.0
	public static final String RATE_INDICATOR_CUBIC_LITE_TIER_3 = "C8";    // REL29.0
	public static final String RATE_INDICATOR_CRITICAL_MAIL_LETTER = "AL";    // REL26.0
	public static final String RATE_INDICATOR_CRITICAL_MAIL_FLAT = "AF";    // REL26.0
	public static final String RATE_INDICATOR_FLAT_RATE_LEGAL_ENV = "FA"; // REL 26.0

	public static final String RATE_INDICATOR_UNDER_1LB_SINGLE_PIECE = "US"; // REL 26.1.0
	public static final String RATE_INDICATOR_UNDER_1LB_ADC = "UA"; // REL 26.1.0
	public static final String RATE_INDICATOR_UNDER_1LB_3_DIGIT = "U3"; // REL 26.1.0
	public static final String RATE_INDICATOR_UNDER_1LB_5_DIGIT = "U5"; // REL 26.1.0
	public static final String RATE_INDICATOR_REGIONAL_GROUND_PARCEL = "RG"; // REL 26.1.0


	public static final String RATE_INDICATOR_LW_BMC = "DC"; // REL29.0.0
	public static final String RATE_INDICATOR_LW_MIXED_BMC = "BB"; // REL29.0.0
	public static final String RATE_INDICATOR_LW_3D = "DE"; // REL29.0.0
	public static final String RATE_INDICATOR_LW_5D = "DF"; // REL29.0.0

	public static final String DISCOUNT_SURCHARGE_MACHINABLE = "D1";
	public static final String DISCOUNT_SURCHARGE_OBMC = "D2";
	public static final String DISCOUNT_SURCHARGE_PRESORT_BMC = "D3";
	public static final String DISCOUNT_SURCHARGE_NONMACHINABLE_INTRA = "D4";
	public static final String DISCOUNT_SURCHARGE_NONMACHINABLE_INTER = "D5";
	public static final String DISCOUNT_SURCHARGE_NONMACHINABLE_DBMC = "D6";
	public static final String DISCOUNT_SURCHARGE_NONMACHINABLE_3D_DSCF = "D7";
	public static final String DISCOUNT_SURCHARGE_D8 = "D8";
	public static final String DISCOUNT_SURCHARGE_D9 = "D9";

	public static final String DESTINATION_RATE_IND_DBMC	= "B";
	public static final String DESTINATION_RATE_IND_DDU		= "D";
	public static final String DESTINATION_RATE_IND_DSCF 	= "S";
	public static final String DESTINATION_RATE_IND_ADC 	= "A";
	public static final String DESTINATION_RATE_IND_ASF 	= "F";
	public static final String DESTINATION_RATE_IND_NONE 	= "N";
	public static final String DESTINATION_RATE_IND_ISC 	= "I";
	//REL22.0.0
	public static final String DESTINATION_RATE_IND_RBMC	= "B";
	public static final String DESTINATION_RATE_IND_RDU		= "D";
	public static final String DESTINATION_RATE_IND_RSCF 	= "S";



	//13.2 changes Allowed STATS criterias - called from STATSSAMPLEFILTER.java
	public static final String STATS_MAIL_CLASS_FIRST_CLASS 		= "1";
	public static final String STATS_MAIL_CLASS_PRIORITY_MAIL 		= "2";
	public static final String STATS_MAIL_CLASS_STANDARD_REGULAR 	= "4";
	public static final String STATS_MAIL_CLASS_PACKAGE_SERVICES 	= "5";
	public static final String STATS_MAIL_NP_CLASS_PACKAGE_SERVICES 	= "9";

	//Updated 18.0.0 Now Called Shape
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_ENV 		= "3";
	public static final String STATS_MAIL_TYPE_PARCELS 							= "5";
	public static final String STATS_MAIL_TYPE_IPP 								= "7";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_2 		= "8";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_BOX		= "9";
	public static final String STATS_MAIL_TYPE_USPS_TRIANGULAR_TUBE 		   	= "A";
	public static final String STATS_MAIL_TYPE_USPS_TRIANGULAR_SMALL_TUBE      	= "B";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_BOX_ALL 	= "C";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_BOX_3 	= "D";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_Mail_4	= "E";
	public static final String STATS_MAIL_TYPE_USPS_FLAT_RATE_PRIORITY_BOX_ALL2	= "F";

	public static final String STATS_MAIL_SUBCLASS_FILL 			= "0";
	public static final String STATS_MAIL_SUBCLASS_PARCEL_POST 		= "1";
	public static final String STATS_MAIL_SUBCLASS_BPM 				= "2";
	public static final String STATS_MAIL_SUBCLASS_MEDIA_MAIL 		= "3";
	public static final String STATS_MAIL_SUBCLASS_LIBRARY_MAIL 	= "4";
	public static final String STATS_MAIL_SUBCLASS_PARCEL_SELECT	= "H";
	public static final String STATS_MAIL_SUBCLASS_PARCEL_SELECT_RETURN	= "I";

	// 14.0 RF3.2
	public static final int SERVICE_LIST_SIZE = 3;
	//14.0 RF3.2
	public static final double PARCEL_BARCODE_DISCOUNT = 0.03;
	public static final String WWS_MACHINABLE_PROCESSING_CATEGORY = "MP";

	// Rel 16.1 Price Change May 12
	public static final String DEFAULT_VALUE_EVS_DISCOUNT_SURCHARGE = "*";
	public static final String PATTERN_EVS_MAN_RATE_IND_DISC_SURCH = "evs.man.rate.ind.disc.surc.";

	// Copy this from EvsEjb so that it will not have to depend on EvsEjb
	public static final String DEFAULT_POSTAGE_PAYMENT_METHOD = "P";

	public static final String STATS_MARKING_NONE = "00";
	public static final String STATS_MARKING_PRESORT = "02";
	public static final String STATS_MARKING_AUTOCR = "05";
	public static final String STATS_MARKING_AUTO = "09";
	public static final String STATS_MARKING_NONAUTO = "11";
	public static final String STATS_MARKING_CMM = "12";
	public static final String STATS_MARKING_NFM = "13";
	public static final String STATS_MARKING_PARCELPOST = "14";
	public static final String STATS_MARKING_PARCELSELECT = "15";
	public static final String STATS_MARKING_DROPSHIP = "16";
	public static final String STATS_MARKING_PARCELSELECT_BMC_PRST = "17";
	public static final String STATS_MARKING_PARCELSELECT_OBMC_PRST = "18";
	public static final String STATS_MARKING_PARCELSELECT_BARCODED = "19";

	public static final double MAX_LENGTH_PLUS_GIRTH_SIZE = 108.00;
	public static final double MIN_LENGTH_PLUS_GIRTH_SIZE = 84.00;

	//REL23.0
	public static final String PM_PRICE_TYPE_CODE_COMM_BASE = "C";
	public static final String PM_PRICE_TYPE_CODE_COMM_PLUS = "CP";
	public static final String PM_PRICE_TYPE_CODE_COMM_PLUS_CUBIC = "CPC";
	public static final String PM_PRICE_TYPE_CODE_COMM_PLUS_ENVELOPE = "CPE"; // REL 26.0

	public static final String EX_PRICE_TYPE_CODE_COMM_BASE = "C";
	public static final String EX_PRICE_TYPE_CODE_COMM_PLUS = "CP";
	public static final double INCHES_TO_CUBIC_FEET_DIVIDER = 1728.0;

	public static final String invPicCode = "Invalid Pic code ";
	public static final String invRate = "Invalid rate ingredients";

	public static final String CUSTOMIZED_POSTAGE_CODE = "C";
	public static final String PUBLISHED_POSTAGE_CODE = "P";
	public static final String UOM_CODE = "1";

	public static final String EXCL_VAL_BARCODE_CONSTR = "L01-G01-I01";

	public static final String PTS_FEL = "PTSExtractFEL";
	public static final String PTS_EXTRACT_MANIFEST_ONLY = "PTSExtractManOnly";
	public static final String PTS_EXTRACT_MANIFEST_DUPLICATES = "PTSExtractManDup";
	public static final String PTS_EXTRACT_UNMANIFESTED = "PTSUnmanifested";

	public static int STATUS_EXTRACT_MODULE_ID = 110;
	public static int PAYMENT_EXTRACT_MODULE_ID = 120;
	
	//31.0.0 - Pankaj Gupta
	public static int RATE_INGREDIENT_MODULE_ID = 220;
	public static int MID_LIST_MODULE_ID = 230;

	//28.0.0 - rybvp0
	public static int RECON_EXTRACT_MODULE_ID = 200;
	public static final String PIPE_DELIM = "|";

	//31.0.0 - Aps Payment Extract Dispatcher
	public static int APS_PAYMENT_EXTRACT_MODULE_ID = 180;
	
	public static int EXTRACT_FORMAT_OPTION1 = 1;
	public static int EXTRACT_FORMAT_OPTION2 = 2;
	public static int EXTRACT_FORMAT_OPTION3 = 3;
	// Third Party Format Option
	public static int EXTRACT_FORMAT_OPTION4 = 4;
	public static String STATUS_EXTRACT_PREFIX = "Status";
	public static String PAYMENT_EXTRACT_PARTIAL_PREFIX = "eVSPaymentPartial";
	public static String PAYMENT_EXTRACT_COMPLETE_PREFIX = "eVSPaymentComplete";
	public static String RECON_EXTRACT_PREFIX = "eVSReconciliationExtract";
	public static String RECON_EXTRACT_EXTENSION = "rxt";
	public static String STATUS_EXTRACT_EXTENSION = "ew";
	public static String PAYMENT_EXTRACT_EXTENSION = "pse";

	public static int RECALC_MODULE_ID = 77;

	public static String CLASS_STANDARD_MAIL_FULFILLMENT="SA";
	public static String CLASS_STANDARD_MAIL_MARKETING="S2";
	public static String WEB_SAMPLE = "WebSamples";
    public static final String CLASS_PARCEL_SELECT_LIGHTWEIGHT = "LW";
	public static final double MAX_WEIGHT_MACHINABLE = 25.00; // REL 29.0.0
	public static final double MAX_LENGTH_MACHINABLE = 27.00; // REL 29.0.0
	public static final double MAX_WIDTH_MACHINABLE = 17.00; // REL 29.0.0
	public static final double MAX_HEIGHT_MACHINABLE = 17.00; // REL 29.0.0
	public static final double MAX_LENGTH_MACHINABLE_S2 = 12.00; // REL 29.0.0
	public static final double MAX_WIDTH_MACHINABLE_S2 = 9.00; // REL 29.0.0
	public static final double MAX_HEIGHT_MACHINABLE_S2 = 2.00; // REL 29.0.0

	public static final String INVALID_PERMIT_EVS = "I";
	public static final String INVALID_PERMIT_PRS = "9";
	public static final String DUPLICATE_CHECK_THRESHOLD = "DUPLICATE_CHECK_THRESHOLD";

	//RELEASE 31, APS Payment Extract Constants
	public static final String APS_POSTAGE_STMT_EXTRACT_H1RECORD = "H1Record";
	public static final String APS_POSTAGE_STMT_EXTRACT_D1RECORD = "D1Record";
	public static final String APS_POSTAGE_STMT_EXTRACT_S1RECORD = "S1Record";
	public static String APS_POSTAGE_STMT_EXTRACT_FILE_NAME_PREFIX = "eVSAPSExtract";
	public static String APS_POSTAGE_STMT_EXTRACT_FILE_NAME_HYPHEN = "-";
	public static String APS_POSTAGE_STMT_EXTRACT_DATE_FORMAT="yyyyMMddHHmmss";
	public static final String APS_FILE_DELIMITER = "|";
	public static final String APS_DESTINATION = "";
	public static final String APS_EXTENSION = ".aps";
	public static final String SERVER_SETTINGS_ENABLE = "ENABLE";
	
	

	public final static List<String>fcUnder1lbRateInds; // REL 26.1.0
	static {
		fcUnder1lbRateInds = new ArrayList<String>();
		fcUnder1lbRateInds.add(RATE_INDICATOR_UNDER_1LB_SINGLE_PIECE);
		fcUnder1lbRateInds.add(RATE_INDICATOR_UNDER_1LB_ADC );
		fcUnder1lbRateInds.add(RATE_INDICATOR_UNDER_1LB_3_DIGIT);
		fcUnder1lbRateInds.add(RATE_INDICATOR_UNDER_1LB_5_DIGIT);
	}

	public final static Map<String, String>evsDri2FacTypeConvert;
	static {
		evsDri2FacTypeConvert = new HashMap<String, String>();
		evsDri2FacTypeConvert.put(DESTINATION_RATE_IND_DDU,"1");
		evsDri2FacTypeConvert.put(DESTINATION_RATE_IND_DSCF, "2");
		evsDri2FacTypeConvert.put(DESTINATION_RATE_IND_DBMC,"3");
		evsDri2FacTypeConvert.put(DESTINATION_RATE_IND_ASF, "4");
		evsDri2FacTypeConvert.put(DESTINATION_RATE_IND_NONE, "5");
	}

}
