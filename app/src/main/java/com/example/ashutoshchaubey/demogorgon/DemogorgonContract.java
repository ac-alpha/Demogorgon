package com.example.ashutoshchaubey.demogorgon;

/**
 * Created by ashutoshchaubey on 03/01/18.
 */

import android.provider.BaseColumns;

/**
 * Created by ashutoshchaubey on 27/12/17.
 */

public class DemogorgonContract  {

    public DemogorgonContract(){}

    public class VisionsEntry implements BaseColumns{

        public static final String TABLE_NAME = "visions";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_RESCUER_NAME = "name";
        public static final String COLUMN_VISION_IMAGE_URI = "ImageUri";
        public static final String COLUMN_WHETHER_RESCUED = "rescued";

        public static final int RESCUED = 1;
        public static final int NOT_RESCUED = 0;

    }

    public class CharactersEntry implements BaseColumns{

        public static final String TABLE_NAME = "characters";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_CHARACTER_NAME = "Name";
        public static final String COLUMN_CHARACTER_IMAGE_ID = "ImageUri";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_NUMBER_OF_RESCUES = "NumberOfRescues";

    }

    public class ButtonsInfo implements BaseColumns{
        public static final String TABLE_NAME="ButtonsInfoShownOrNot";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SHOWN_OR_NOT = "ShownOrNot";
    }

}

