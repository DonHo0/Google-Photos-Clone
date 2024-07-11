package com.DonH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static String UserHome = System.getProperty("user.home");
    static Path thumbnailsDirectory = Path.of(UserHome).resolve(".photos");

    public static void main(String[] args) throws IOException {
        Files.createDirectories(thumbnailsDirectory);
        String directory=args.length==1?args[0]:".";
        AtomicInteger imageCounter = new AtomicInteger();
        long started = System.currentTimeMillis();
        Path sourceDirectory = Path.of(directory);
        try (Stream<Path> files = Files.walk(sourceDirectory)
                .filter(Files::isRegularFile)
                .filter(Main::isImage)) {
            files.forEach(f -> {
                imageCounter.incrementAndGet();
                createThumbnail(f, thumbnailsDirectory.resolve(f.getFileName()));
            });
        }
        long ended = System.currentTimeMillis();
        System.out.println("Converted "+ imageCounter+" images to thumbnails. In "+((ended-started)*0.001)+" seconds");
    }

    private static boolean isImage(Path path) {
        try {
            String probeContentType = Files.probeContentType(path);
            return probeContentType != null && probeContentType.contains("image");
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean createThumbnail(Path source, Path target) {
        try {
            System.out.println("Creating thumbnail for: " + target.normalize().toAbsolutePath().toString());
            List<String> command = List.of("magick", "convert", "-resize", "300x", source.normalize().toAbsolutePath().toString(), target.normalize().toAbsolutePath().toString());
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.inheritIO();
            Process process = builder.start();
            boolean b = process.waitFor(3, TimeUnit.SECONDS);
            if (!b) {
                process.destroy();
            }
            return b;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
