package com.zarusz.control.repository.jpa;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Tomasz on 9/30/2016.
 */
public class Utils {

    public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
