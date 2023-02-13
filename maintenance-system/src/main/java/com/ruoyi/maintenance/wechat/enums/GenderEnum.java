package com.ruoyi.maintenance.wechat.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abel
 * @since 2/11/2023 10:39 PM
 */
public enum GenderEnum {

    /**
     * 0. 未知, 1. 男, 2. 女
     */
    MALE(1L, "1"),
    WOMAN(2L, "2"),
    UNKNOWN (0L, "0");

    private final Long code;
    private final String value;

    GenderEnum ( Long code, String value ) {
        this.code = code;
        this.value = value;
    }

    public Long getCode () {
        return code;
    }

    public String getValue () {
        return value;
    }

    public static GenderEnum getEnum ( Long code ) {
        for ( GenderEnum e :GenderEnum.values () ){
            if ( e.code.equals ( code ) ) {
                return e;
            }
        }
        return null;
    }

    public static List <Long> getCodes () {
        ArrayList <Long> list = new ArrayList <> ();
        for ( GenderEnum e : GenderEnum.values () ) {
            list.add ( e.getCode () );
        }
        return list;
    }
}
