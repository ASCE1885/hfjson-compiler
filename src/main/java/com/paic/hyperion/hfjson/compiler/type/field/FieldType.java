package com.paic.hyperion.hfjson.compiler.type.field;

import com.paic.hyperion.hfjson.Constants;
import com.paic.hyperion.hfjson.annotation.HFJsonObject;
import com.paic.hyperion.hfjson.compiler.TypeUtils;
import com.paic.hyperion.hfjson.compiler.type.Type;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.lang.annotation.Annotation;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class FieldType extends Type {

    public abstract TypeName getNonPrimitiveTypeName();

    @Override
    public String getParameterizedTypeString() {
        return "$T";
    }

    @Override
    public Object[] getParameterizedTypeStringArgs() {
        return new Object[] { getNonPrimitiveTypeName() };
    }

    public static FieldType fieldTypeFor(TypeMirror typeMirror, TypeMirror typeConverterType, Elements elements, Types types) {
        if (typeMirror != null) {
            if (typeConverterType != null && !"void".equals(typeConverterType.toString())) {
                return new TypeConverterFieldType(TypeName.get(typeMirror), ClassName.bestGuess(typeConverterType.toString()));
            } else if (typeMirror.getKind() == TypeKind.BOOLEAN) {
                return new BooleanFieldType(true);
            } else if ("java.lang.Boolean".equals(typeMirror.toString())) {
                return new BooleanFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.INT) {
                return new IntegerFieldType(true);
            } else if ("java.lang.Integer".equals(typeMirror.toString())) {
                return new IntegerFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.LONG) {
                return new LongFieldType(true);
            } else if ("java.lang.Long".equals(typeMirror.toString())) {
                return new LongFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.FLOAT) {
                return new FloatFieldType(true);
            } else if ("java.lang.Float".equals(typeMirror.toString())) {
                return new FloatFieldType(false);
            } else if (typeMirror.getKind() == TypeKind.DOUBLE) {
                return new DoubleFieldType(true);
            } else if ("java.lang.Double".equals(typeMirror.toString())) {
                return new DoubleFieldType(false);
            } else if ("java.lang.String".equals(typeMirror.toString())) {
                return new StringFieldType();
            } else if (typeMirror instanceof DeclaredType) {
                Annotation annotation = ((DeclaredType) typeMirror).asElement().getAnnotation(HFJsonObject.class);
                if (annotation != null) {
                    TypeMirror erasedType = types.erasure(typeMirror);
                    DeclaredType declaredType = (DeclaredType) erasedType;
                    TypeElement typeElement = (TypeElement) declaredType.asElement();

                    String packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
                    ClassName fieldClass = ClassName.bestGuess(typeMirror.toString());
                    ClassName mapperClass = ClassName.get(packageName, TypeUtils.getSimpleClassName(typeElement, packageName) + Constants.MAPPER_CLASS_SUFFIX);

                    return new JsonFieldType(fieldClass, mapperClass);
                }
            }

            return new DynamicFieldType(TypeName.get(typeMirror));
        } else {
            return null;
        }
    }

    protected static String replaceLastLiteral(String string, String replacement) {
        int pos = string.lastIndexOf("$L");
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + 2, string.length());
        } else {
            return string;
        }
    }
}
