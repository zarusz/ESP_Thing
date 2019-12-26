package com.zarusz.control.app.scripting;

import java.util.List;

/**
 * Created by Tomasz on 01.11.2016.
 */
public interface ScriptRepository {

    List<String> findAll();
    List<String> findAll(int scriptFlags);

}
