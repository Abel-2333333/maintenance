package com.ruoyi.maintenance.wechat.enums;

import java.util.EnumSet;

/**
 * @author Abel
 * @since 2/11/2023 10:47 PM
 */
public interface BaseEnum {
    Integer getCode ();

    String getValue ();

    static <E extends Enum <E> & BaseEnum> E getEnumByCode ( Integer code, Class <E> clazz ) {
        if ( code == null ) {
            return null;
        }
        EnumSet <E> enumSet = EnumSet.allOf ( clazz );
        return enumSet.stream ().
                filter ( e -> e.getCode ().equals ( code ) )
                .findFirst ()
                .orElse ( null );
    }

    static <E extends Enum <E> & BaseEnum> E getEnumByValue ( String value, Class <E> clazz ) {
        if(value == null){
            return null;
        }
        EnumSet<E> enumSet = EnumSet.allOf(clazz);
        return enumSet.stream ().
                filter ( e -> e.getValue ().equals ( value ) )
                .findFirst ()
                .orElse ( null );
    }
}
