package com.DonH;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ImageMagickTest {

    @Test
    void imageMagick_installed(){
        assertThat(new imageMagick().detectImageMagickVersion()).isNotEqualTo(imageMagick.imageMagickVersion.NA);
    }
    @Test
    @EnableIfImageMagickIsInstalled
    void thumbnail_Creation_Working(){

    }

}