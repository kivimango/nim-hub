package com.kivimango.nimhub.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class PackageStoreFSImplTest {

    private PackageStoreProperties properties = createProperties();
    private PackageStore store = new PackageStoreFSImpl(properties);
    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lib.tar.gz");
    private Package lib = createPackage();

    @Test
    public void testPutShouldCreateFileInStorageDirectory() throws IOException {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib.tar.gz", "application/gzip", inputStream);
        Package lib = new Package();
        lib.setName("test-lib");
        lib.setVersion("1.0-STABLE");
        store.put(lib, packageFile.getBytes());

        Path savedFilePath = Paths.get(properties.getStorageDir() + lib.getName() + File.separator + lib.getName() + "-" + lib.getVersion() + ".tar.gz");
        byte[] savedContent = Files.readAllBytes(savedFilePath);
        assertTrue(Files.exists(savedFilePath));
        assertArrayEquals(packageFile.getBytes(), savedContent);

        cleanup();
    }

    @Test
    public void testGetShouldReturnStoredFilePath() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib.tar.gz", "application/gzip", inputStream);
        store.put(lib, packageFile.getBytes());

        String expected = Paths.get(properties.getStorageDir()).resolve(lib.getName()).resolve(lib.getName() + "-" + lib.getVersion() + ".tar.gz").toString();
        String actual = store.get(lib);
        assertEquals(expected, actual);

        cleanup();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetShouldThrowExceptionOnNonExistentFile() throws Exception {
        Package nonExistent = new Package();
        nonExistent.setId(23L);
        nonExistent.setName("non-existent");
        nonExistent.setVersion("0.1-alpha");
        store.get(nonExistent);
    }

    private Package createPackage() {
        Package lib = new Package();
        lib.setId(1L);
        lib.setName("test-lib");
        lib.setVersion("1.0-STABLE");
        return lib;
    }

    private void cleanup() {
        File f = new File(properties.getStorageDir());
        File[] files = f.listFiles();
        for(File ff: files) {
            ff.delete();
        }
        f.delete();
    }

    private PackageStoreProperties createProperties() {
        PackageStoreProperties props = new PackageStoreProperties();
        String pathStr = System.getProperty("java.io.tmpdir");
        Path path = Paths.get(pathStr).resolve("nim-hub");
        System.out.println(path);

        if(!Files.exists(path))
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
               e.printStackTrace();
            }

        props.setStorageDir(pathStr);
        return props;
    }
}