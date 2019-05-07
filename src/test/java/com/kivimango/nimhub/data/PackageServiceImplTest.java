package com.kivimango.nimhub.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static com.kivimango.nimhub.data.Samples.*;
import static com.kivimango.nimhub.util.TestData.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PackageServiceImplTest {

    private PackageRepository repository = Mockito.mock(PackageRepository.class);
    private PackageStore store = Mockito.mock(PackageStore.class);
    private UserRepository users =  Mockito.mock(UserRepository.class);

    @InjectMocks
    private PackageService service = new PackageServiceImpl(repository, store, users);

    @Test
    public void testSaveShouldSaveNewEntityAndShouldReturnWithDetails() throws IOException {
        File testFile = ResourceUtils.getFile("classpath:lib-1.0-FINAL.tar.gz");
        byte[] content = Files.readAllBytes(testFile.toPath());

        given(repository.save(Mockito.any())).willReturn(testPackage);
        given(users.findByUsername("testUser")).willReturn(Optional.of(testUser));

        PackageDto saved = service.save(testUploadForm, content, "testUser");

        assertEquals(username, saved.getOwner().getUsername());
        assertEquals(packageName, saved.getName());
        assertEquals(description, saved.getDescription());
        assertEquals(license, saved.getLicense());
        assertEquals(web, saved.getWeb());
        assertEquals(version, saved.getVersion());
        assertFalse(saved.getHidden());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetShouldThrowExceptionOnPackageNotFoundInDatabase() throws ResourceNotFoundException {
        given(repository.findByNameAndVersion(packageName, version)).willReturn(Optional.empty());
        service.get(packageName, version);
    }

    @Test
    public void testIsExistsShouldReturnFalseOnNonExistentPackage() {
        given(repository.existsByNameAndVersion(packageName, version)).willReturn(false);
        // UnnecessaryStubbingException
        //given(store.exists(packageName, version)).willReturn(true);
        assertFalse(service.isExists(packageName, version));
    }

    @Test
    public void testIsExistsShouldReturnFalseOnNonExistentFile() {
        given(repository.existsByNameAndVersion(packageName, version)).willReturn(true);
        given(store.exists(packageName, version)).willReturn(false);
        assertFalse(service.isExists(packageName, version));
    }

    @Test
    public void testExistsShouldReturnTrueIfEntityExistsInDatabaseAndOnDisk() {
        given(repository.existsByNameAndVersion(packageName, version)).willReturn(true);
        given(store.exists(packageName, version)).willReturn(true);
        assertTrue(service.isExists(packageName, version));
    }
}