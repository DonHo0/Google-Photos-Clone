package com.DonH;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class imageMagick {

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
