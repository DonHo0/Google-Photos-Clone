package com.DonH;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class imageMagick {

    private imageMagickVersion version=detectImageMagickVersion();
    public int run(String... cmds) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(cmds);
        builder.inheritIO();
        Process process = builder.start();
        boolean b = process.waitFor(1, TimeUnit.SECONDS);
        if (!b) {
            process.destroy();
        }
        return process.exitValue();
    }
    public  boolean createThumbnail(Path source, Path target) {
        try {
            System.out.println("Creating thumbnail for: " + target.normalize().toAbsolutePath().toString());
            List<String> command = new ArrayList<>(List.of("convert", "-resize", "300x",
                    source.normalize().toAbsolutePath().toString(), target.normalize().toAbsolutePath().toString()));
            if( version== imageMagickVersion.IM_7){
                command.add(0,"magick");
            }

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
    public imageMagickVersion detectImageMagickVersion() {
        try{
            int exitCode = run("magick", "--version");
            if(exitCode==0){
                return imageMagickVersion.IM_7;
            }

        exitCode = run("convert", "--version");
        if(exitCode==0){
            return imageMagickVersion.IM_6;
        }
        return  imageMagickVersion.NA;
    }
        catch (Exception e){
            return imageMagickVersion.NA;
        }
    }

    public enum imageMagickVersion {
        NA, IM_6, IM_7
    }
}
