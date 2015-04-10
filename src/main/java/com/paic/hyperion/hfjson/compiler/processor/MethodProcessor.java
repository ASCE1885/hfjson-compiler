package com.paic.hyperion.hfjson.compiler.processor;

import com.paic.hyperion.hfjson.annotation.HFJsonObject;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public abstract class MethodProcessor extends Processor {

    protected MethodProcessor(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    public boolean isCallbackMethodAnnotationValid(Element element, String annotationName) {
        TypeElement enclosingElement = (TypeElement)element.getEnclosingElement();

        if (enclosingElement.getAnnotation(HFJsonObject.class) == null) {
            error(enclosingElement, "%s: @%s methods can only be in classes annotated with @%s.", enclosingElement.getQualifiedName(), annotationName, HFJsonObject.class.getSimpleName());
            return false;
        }

        ExecutableElement executableElement = (ExecutableElement)element;
        if (executableElement.getParameters().size() > 0) {
            error(element, "%s: @%s methods must not take any parameters.", enclosingElement.getQualifiedName(), annotationName);
            return false;
        }

        List<? extends Element> allElements = enclosingElement.getEnclosedElements();
        int methodInstances = 0;
        for (Element enclosedElement : allElements) {
            for (AnnotationMirror am : enclosedElement.getAnnotationMirrors()) {
                if (am.getAnnotationType().asElement().getSimpleName().toString().equals(annotationName)) {
                    methodInstances++;
                }
            }
        }
        if (methodInstances != 1) {
            error(element, "%s: There can only be one @%s method per class.", enclosingElement.getQualifiedName(), annotationName);
            return false;
        }

        return true;
    }

}
