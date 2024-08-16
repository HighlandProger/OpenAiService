package ru.rusguardian.open.ai.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import ru.rusguardian.open.ai.service.config.ApplicationConfiguration;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {ApplicationConfiguration.class})
public class ModuleAutoConfiguration {
}