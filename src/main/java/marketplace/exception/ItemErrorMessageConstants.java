package marketplace.exception;

/**
 * Class with errors related to items.
 */
public class ItemErrorMessageConstants {
    public static final String LONG_TITLE = "Exceeded the number of characters in title.";
    public static final String LONG_DESCRIPTION = "Exceeded the number of characters in description.";
    public static final String WRONG_DATE_FORMAT = "Wrong date format.";
    public static final String WRONG_FLOAT_VALUE = "Wrong float value.";
    public static final String WRONG_LONG_VALUE = "Wrong long value.";
    public static final String WRONG_TIME_FORMAT = "Wrong time format.";
    public static final String WRONG_ITEM = "Wrong item id was received from frontend.";
    public static final String WRONG_IMAGE_TYPE = "Wrong type of item image. Must be: jpeg, jpg png!";
    public static final String WRONG_ITEM_PARAMETERS = "Wrong values of a start price, bid increment, stop date or a stop time";
    public static final String NOT_FOUND = "Item now found.";

}
