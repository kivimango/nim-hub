package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.PackageDto;
import com.kivimango.nimhub.data.PackageService;
import com.kivimango.nimhub.data.ResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
final class PackageController {

    private final PackageService packages;

    PackageController(PackageService packages) {
        this.packages = packages;
    }

    @GetMapping(value = "/packages/{name}/{version}", produces = "application/gzip")
    public ResponseEntity<Resource> download(@PathVariable("name") String name, @PathVariable("version") String version) throws ResourceNotFoundException, IOException {
        String path = packages.get(name, version);
        FileSystemResource fsr = new FileSystemResource(path);
        // because of the fix for CVE-2015-5211 https://stackoverflow.com/questions/41364732/zip-file-downloaded-as-f-txt-file-springboot
        return ok()
                .header("Content-Disposition", "inline, filename=\"" + fsr.getFilename() + "\"")
                .header("Content-Length", String.valueOf(fsr.contentLength()))
                .body(fsr);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/packages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PackageDto> uploadPackage(@Valid PackageUploadForm form, Principal principal) throws IOException {
        PackageDto saved = packages.save(form, form.getFile().getBytes(), principal.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + saved.getName()).buildAndExpand().toUri();
        return created(location).body(saved);
    }

    @ExceptionHandler(BindException.class)
    ResponseEntity<Set<ValidationError>> handleFormValidationErrors(BindException ex) {
        Set<ValidationError> errors = new HashSet<>();

        for(FieldError error : ex.getFieldErrors()) {
            errors.add(new ValidationError(error.getField(), error.getDefaultMessage()));
        }

        return badRequest().body(errors);
    }

    @ExceptionHandler({ResourceNotFoundException.class, IOException.class})
    ResponseEntity<String> resourceNotFound(Exception exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
    }

}
