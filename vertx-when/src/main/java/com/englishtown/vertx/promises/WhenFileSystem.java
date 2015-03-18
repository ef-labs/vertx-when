package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystemProps;
import io.vertx.core.file.OpenOptions;

import java.util.List;

/**
 * Wraps the vert.x {@link io.vertx.core.file.FileSystem} to use promises rather than handlers
 */
public interface WhenFileSystem {

    /**
     * Copy a file from the path {@code from} to path {@code to}, asynchronously.
     * <p>
     * The copy will fail if the destination already exists.
     *
     * @param from the path to copy from
     * @param to   the path to copy to
     * @return a promise for completion
     */
    Promise<Void> copy(String from, String to);

    /**
     * Copy a file from the path {@code from} to path {@code to}, asynchronously.
     * <p>
     * If {@code recursive} is {@code true} and {@code from} represents a directory, then the directory and its contents
     * will be copied recursively to the destination {@code to}.
     * <p>
     * The copy will fail if the destination if the destination already exists.
     *
     * @param from      the path to copy from
     * @param to        the path to copy to
     * @param recursive
     * @return a promise for completion
     */
    Promise<Void> copyRecursive(String from, String to, boolean recursive);

    /**
     * Move a file from the path {@code from} to path {@code to}, asynchronously.
     * <p>
     * The move will fail if the destination already exists.
     *
     * @param from the path to copy from
     * @param to   the path to copy to
     * @return a promise for completion
     */
    Promise<Void> move(String from, String to);

    /**
     * Truncate the file represented by {@code path} to length {@code len} in bytes, asynchronously.
     * <p>
     * The operation will fail if the file does not exist or {@code len} is less than {@code zero}.
     *
     * @param path the path to the file
     * @param len  the length to truncate it to
     * @return a promise for completion
     */
    Promise<Void> truncate(String path, long len);

    /**
     * Change the permissions on the file represented by {@code path} to {@code perms}, asynchronously.
     * <p>
     * The permission String takes the form rwxr-x--- as
     * specified <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
     *
     * @param path  the path to the file
     * @param perms the permissions string
     * @return a promise for completion
     */
    Promise<Void> chmod(String path, String perms);

    /**
     * Change the permissions on the file represented by {@code path} to {@code perms}, asynchronously.<p>
     * The permission String takes the form rwxr-x--- as
     * specified in {<a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>}.
     * <p>
     * If the file is directory then all contents will also have their permissions changed recursively. Any directory permissions will
     * be set to {@code dirPerms}, whilst any normal file permissions will be set to {@code perms}.
     *
     * @param path     the path to the file
     * @param perms    the permissions string
     * @param dirPerms the directory permissions
     * @return a promise for completion
     */
    Promise<Void> chmodRecursive(String path, String perms, String dirPerms);

    /**
     * Change the ownership on the file represented by {@code path} to {@code user} and {code group}, asynchronously.
     *
     * @param path  the path to the file
     * @param user  the user name
     * @param group the user group
     * @return a promise for completion
     */
    Promise<Void> chown(String path, String user, String group);

    /**
     * Obtain properties for the file represented by {@code path}, asynchronously.
     * <p>
     * If the file is a link, the link will be followed.
     *
     * @param path the path to the file
     * @return a promise for file properties
     */
    Promise<FileProps> props(String path);

    /**
     * Obtain properties for the link represented by {@code path}, asynchronously.
     * <p>
     * The link will not be followed.
     *
     * @param path the path to the file
     * @return a promise for file properties
     */
    Promise<FileProps> lprops(String path);

    /**
     * Create a hard link on the file system from {@code link} to {@code existing}, asynchronously.
     *
     * @param link     the link
     * @param existing the link destination
     * @return a promise for completion
     */
    Promise<Void> link(String link, String existing);

    /**
     * Create a symbolic link on the file system from {@code link} to {@code existing}, asynchronously.
     *
     * @param link     the link
     * @param existing the link destination
     * @return a promise for completion
     */
    Promise<Void> symlink(String link, String existing);

    /**
     * Unlinks the link on the file system represented by the path {@code link}, asynchronously.
     *
     * @param link the link
     * @return a promise for completion
     */
    Promise<Void> unlink(String link);

    /**
     * Returns the path representing the file that the symbolic link specified by {@code link} points to, asynchronously.
     *
     * @param link the link
     * @return a promise for the path
     */
    Promise<String> readSymlink(String link);

    /**
     * Deletes the file represented by the specified {@code path}, asynchronously.
     *
     * @param path path to the file
     * @return a promise for completion
     */
    Promise<Void> delete(String path);

    /**
     * Deletes the file represented by the specified {@code path}, asynchronously.
     * <p>
     * If the path represents a directory and {@code recursive = true} then the directory and its contents will be
     * deleted recursively.
     *
     * @param path      path to the file
     * @param recursive delete recursively?
     * @return a promise for completion
     */
    Promise<Void> deleteRecursive(String path, boolean recursive);

    /**
     * Create the directory represented by {@code path}, asynchronously.
     * <p>
     * The operation will fail if the directory already exists.
     *
     * @param path path to the file
     * @return a promise for completion
     */
    Promise<Void> mkdir(String path);

    /**
     * Create the directory represented by {@code path}, asynchronously.
     * <p>
     * The new directory will be created with permissions as specified by {@code perms}.
     * <p>
     * The permission String takes the form rwxr-x--- as specified
     * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
     * <p>
     * The operation will fail if the directory already exists.
     *
     * @param path  path to the file
     * @param perms the permissions string
     * @return a promise for completion
     */
    Promise<Void> mkdir(String path, String perms);

    /**
     * Create the directory represented by {@code path} and any non existent parents, asynchronously.
     * <p>
     * The operation will fail if the directory already exists.
     *
     * @param path path to the file
     * @return a promise for completion
     */
    Promise<Void> mkdirs(String path);

    /**
     * Create the directory represented by {@code path} and any non existent parents, asynchronously.
     * <p>
     * The new directory will be created with permissions as specified by {@code perms}.
     * <p>
     * The permission String takes the form rwxr-x--- as specified
     * in <a href="http://download.oracle.com/javase/7/docs/api/java/nio/file/attribute/PosixFilePermissions.html">here</a>.
     * <p>
     * The operation will fail if the directory already exists.<p>
     *
     * @param path  path to the file
     * @param perms the permissions string
     * @return a promise for completion
     */
    Promise<Void> mkdirs(String path, String perms);

    /**
     * Read the contents of the directory specified by {@code path}, asynchronously.
     * <p>
     * The result is an array of String representing the paths of the files inside the directory.
     *
     * @param path path to the file
     * @return a promise for the contents
     */
    Promise<List<String>> readDir(String path);

    /**
     * Read the contents of the directory specified by {@code path}, asynchronously.
     * <p>
     * The parameter {@code filter} is a regular expression. If {@code filter} is specified then only the paths that
     * match  @{filter}will be returned.
     * <p>
     * The result is an array of String representing the paths of the files inside the directory.
     *
     * @param path   path to the directory
     * @param filter the filter expression
     * @return a promise for the contents
     */
    Promise<List<String>> readDir(String path, String filter);

    /**
     * Reads the entire file as represented by the path {@code path} as a {@link io.vertx.core.buffer.Buffer}, asynchronously.
     * <p>
     * Do not user this method to read very large files or you risk running out of available RAM.
     *
     * @param path path to the file
     * @return a promise for the file data
     */
    Promise<Buffer> readFile(String path);

    /**
     * Creates the file, and writes the specified {@code Buffer data} to the file represented by the path {@code path},
     * asynchronously.
     *
     * @param path path to the file
     * @return a promise for completion
     */
    Promise<Void> writeFile(String path, Buffer data);

    /**
     * Open the file represented by {@code path}, asynchronously.
     * <p>
     * The file is opened for both reading and writing. If the file does not already exist it will be created.
     *
     * @param path    path to the file
     * @param options options describing how the file should be opened
     * @return a promise for the async file
     */
    Promise<AsyncFile> open(String path, OpenOptions options);

    /**
     * Creates an empty file with the specified {@code path}, asynchronously.
     *
     * @param path path to the file
     * @return a promise for completion
     */
    Promise<Void> createFile(String path);

    /**
     * Creates an empty file with the specified {@code path} and permissions {@code perms}, asynchronously.
     *
     * @param path  path to the file
     * @param perms the permissions string
     * @return a promise for completion
     */
    Promise<Void> createFile(String path, String perms);

    /**
     * Determines whether the file as specified by the path {@code path} exists, asynchronously.
     *
     * @param path path to the file
     * @return a promise for existence
     */
    Promise<Boolean> exists(String path);

    /**
     * Returns properties of the file-system being used by the specified {@code path}, asynchronously.
     *
     * @param path path to anywhere on the filesystem
     * @return a promise for the file system properties
     */
    Promise<FileSystemProps> fsProps(String path);

}
