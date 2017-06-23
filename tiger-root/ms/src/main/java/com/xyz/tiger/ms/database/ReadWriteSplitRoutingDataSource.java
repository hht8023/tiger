/**
* Copyright © 1998-2017, enn Inc. All Rights Reserved.
*/
package com.xyz.tiger.ms.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * TODO
 * @author Hanht
 * @date 2017年6月15日
 */
public class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {

	@Override
    protected Object determineCurrentLookupKey() {
		System.out.println("切换="+DbContextHolder.getDbType());
        return DbContextHolder.getDbType();
    }

}
