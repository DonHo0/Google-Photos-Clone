package com.DonH;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.Annotation;

import static   org.junit.platform.commons.support.AnnotationSupport.findAnnotation;
public class EnableIfImageMagickIsInstalledConditon implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
        return findAnnotation(extensionContext.getElement(),EnableIfImageMagickIsInstalled.class) //
                .map((annotaion)->(new imageMagick().detectImageMagickVersion()!= imageMagick.imageMagickVersion.NA)
                ? ConditionEvaluationResult.enabled("ImageMagick is installed"):
                ConditionEvaluationResult.disabled("ImageMagick is not installed"))
        .orElse(ConditionEvaluationResult.disabled("IM is not installed"));

    }
}
