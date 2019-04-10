package com.kivimango.nimhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * To trigger performing entity date auditing
 */

@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
