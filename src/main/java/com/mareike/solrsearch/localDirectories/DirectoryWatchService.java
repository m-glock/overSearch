package com.mareike.solrsearch.localDirectories;

/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.mareike.solrsearch.Indexer;
import com.mareike.solrsearch.Main;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;

public class DirectoryWatchService implements Runnable{

    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace;
    private String deletedFolder = "clcvlbjdvnvdscksdabfg";

    @SuppressWarnings("unchecked")
    private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    public DirectoryWatchService(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.recursive = recursive;

        if (recursive) {
            Main.logger.info("Adding directory watcher to " + dir + " ...\n");
            registerAll(dir);
            Main.logger.info("Done.");
        } else {
            register(dir);
        }

        // enable trace after initial registration
        this.trace = true;
    }


    @Override
    public void run() {
        processEvents();
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                Main.logger.info("register "+ dir + "\n");
            } else {
                if (!dir.equals(prev)) {
                    Main.logger.info("update: " + prev + " -> " + dir + "\n");
                }
            }
        }
        keys.put(key, dir);
    }

    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void processEvents() {
        for (;;) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                Main.logger.info("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);



                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                            if(child.toString().equals(deletedFolder)) {
                                deletedFolder = "clcvlbjdvnvdscksdabfg";
                            }
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readable
                    }
                }

                updateFiles(event, child);
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    private void updateFiles(WatchEvent event, Path path){
        String extension = FilenameUtils.getExtension(path.toString());
        if(extension.equals("") && event.kind().name().equals("ENTRY_DELETE")){
            Main.logger.info("folder has been deleted: " + path.toString());
            deleteAllFilesInFolder(path.toFile());
            deletedFolder = path.toString();
        }
        if(!extension.equals("") && !path.toString().contains("~$") && !path.toString().endsWith("tmp") && !path.toString().contains(deletedFolder)){
            switch(event.kind().name()){
                case "ENTRY_MODIFY":
                    Main.logger.info("create file " + path.toString());
                    Indexer.indexFileOrFolder(path.toString());
                    break;
                case "ENTRY_DELETE":
                    Main.logger.info("delete file " + path.toString());
                    Indexer.deleteFile(path.toString());
                    break;
                default:
                    break;
            }
        }
    }

    private void deleteAllFilesInFolder(File folder){
        Indexer.deleteFile(folder.toString());
    }
}