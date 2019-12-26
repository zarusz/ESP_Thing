package com.zarusz.control.config.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Created by Tomasz on 9/13/2015.
 */
public class CustomNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        String columnName = super.joinKeyColumnName(joinedColumn, joinedTable);
        return columnName + "_id";
    }
}
