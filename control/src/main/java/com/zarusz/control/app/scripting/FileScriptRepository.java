package com.zarusz.control.app.scripting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tomasz on 01.11.2016.
 */
@Repository
public class FileScriptRepository implements ScriptRepository {

    private List<String> scripts;

    @Inject
    public FileScriptRepository(@Value("${control.scripts}") String scriptsFolderPath) {

        File scriptsFolder = new File(scriptsFolderPath);
        List<File> scriptFiles = Stream.of(scriptsFolder.list())
            .map(x -> new File(scriptsFolder, x))
            .filter(x -> x.isFile() && x.getName().endsWith(".groovy"))
            .collect(Collectors.toList());

        scripts = scriptFiles.stream().map(x -> x.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> findAll() {
        return scripts;
    }

    @Override
    public List<String> findAll(int scriptFlags) {
        return scripts;
    }
}
