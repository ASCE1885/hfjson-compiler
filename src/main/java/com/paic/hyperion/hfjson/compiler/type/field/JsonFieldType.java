package com.paic.hyperion.hfjson.compiler.type.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class JsonFieldType extends FieldType {

    private ClassName mClassName;
    private ClassName mMapperClassName;

    public JsonFieldType(ClassName className, ClassName mapperClassName) {
        mClassName = className;
        mMapperClassName = mapperClassName;
    }

    @Override
    public TypeName getTypeName() {
        return mClassName;
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return mClassName;
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, "$T._parse($L)");
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, mMapperClassName, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, String getter, boolean writeFieldNameForObject) {
        builder.beginControlFlow("if ($L != null)", getter);

        if (writeFieldNameForObject) {
            builder.addStatement("$L.writeFieldName($S)", JSON_GENERATOR_VARIABLE_NAME, fieldName);
        }

        builder
                .addStatement("$T._serialize($L, $L, true)", mMapperClassName, getter, JSON_GENERATOR_VARIABLE_NAME)
                .endControlFlow();
    }
}
