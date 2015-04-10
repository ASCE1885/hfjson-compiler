package com.paic.hyperion.hfjson.compiler.type.container;

import com.paic.hyperion.hfjson.compiler.TypeUtils;
import com.paic.hyperion.hfjson.compiler.type.Type;
import com.squareup.javapoet.ClassName;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class ContainerType extends Type {

    public Type subType;

    public static ContainerType containerTypeFor(TypeMirror typeMirror, TypeMirror genericClassTypeMirror, Elements elements, Types types) {
        ContainerType containerType = null;
        switch (genericClassTypeMirror.toString()) {
            case "java.util.List":
            case "java.util.ArrayList":
                containerType = new ListContainerType(ClassName.bestGuess(genericClassTypeMirror.toString()));
                break;
            case "java.util.Map":
            case "java.util.HashMap":
                containerType = new MapContainerType(ClassName.bestGuess(genericClassTypeMirror.toString()));
                break;
            case "java.util.Set":
            case "java.util.HashSet":
                containerType = new SetContainerType(ClassName.bestGuess(genericClassTypeMirror.toString()));
                break;
            case "java.util.Queue":
            case "java.util.Deque":
            case "java.util.ArrayDeque":
                containerType = new QueueContainerType(ClassName.bestGuess(genericClassTypeMirror.toString()));
                break;
        }

        if (containerType == null) {
            if (typeMirror instanceof ArrayType) {
                typeMirror = ((ArrayType)typeMirror).getComponentType();
                containerType = new ArrayCollectionType();
            }
        } else {
            typeMirror = TypeUtils.getTypeFromCollection(typeMirror);
        }

        if (containerType != null) {
            containerType.subType = Type.typeFor(typeMirror, null, elements, types);
        }

        return containerType;
    }
}
