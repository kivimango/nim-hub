package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.PackageDto;
import com.kivimango.nimhub.data.PackageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@PreAuthorize("hasRole('USER')")
final class PackageController {

    private final PackageService packages;

    PackageController(PackageService packages) {
        this.packages = packages;
    }

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

}
