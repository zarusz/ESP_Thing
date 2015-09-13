package com.zarusz.control.hibernate;

import com.zarusz.control.config.hibernate.CustomNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomasz on 9/12/2015.
 */
public class CustomNamingStrategyTest {

    private NamingStrategy s;

    @Before
    public void init() {
        s = new CustomNamingStrategy();
    }

    @Test
    public void testColumnNames() {
        // arrange
        final String propertyDisplayName = "displayName";

        // act
        final String column_displayName = s.columnName(propertyDisplayName);
        final String propertyToColumn_displayName = s.propertyToColumnName(propertyDisplayName);

        // assert
        final String expectedColumn = "display_name";
        assertThat(column_displayName).isEqualTo(expectedColumn);
        assertThat(propertyToColumn_displayName).isEqualTo(expectedColumn);
    }

    @Test
    public void testTableNames() {
        // arrange
        final String className = "Device";

        // act
        final String tableName = s.columnName(className);

        // assert
        final String expectedTableName = "device";
        assertThat(tableName).isEqualTo(expectedTableName);
    }

    /*

    @Test
    public void testJoinedKeyColumn() {
        // arrange
        final String className = "Device";

        // act
        final String tableName = s.joinKeyColumnName();

        // assert
        final String expectedTableName = "device";
        assertThat(tableName).isEqualTo(expectedTableName);
    }
    */

}
