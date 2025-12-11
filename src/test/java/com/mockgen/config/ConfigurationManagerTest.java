package com.mockgen.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationManagerTest {
    @TempDir
    Path tempDir;

    @Test
    void testLoadProperties() throws IOException {
        File propertiesFile = new File(tempDir.toFile(), "app.properties");
        try (FileWriter writer = new FileWriter(propertiesFile)) {
            writer.write("csv.input.path=/path/to/file.csv\n");
            writer.write("filter.product.id=P-123\n");
            writer.write("report.top.sales.count=5\n");
            writer.write("csv.separator=;\n");
            writer.write("report.output=FILE\n");
        }

        ConfigurationManager manager = new ConfigurationManager();
    }
}