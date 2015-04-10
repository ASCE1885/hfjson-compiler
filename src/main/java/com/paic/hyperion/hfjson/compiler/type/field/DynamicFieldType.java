package com.paic.hyperion.hfjson.compiler.type.field;

import com.paic.hyperion.hfjson.LoganSquare;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class DynamicFieldType extends FieldType {

    private TypeName mTypeName;

    public DynamicFieldType(TypeName typeName) {
        mTypeName = typeName;
    }

    @Override
    public TypeName getTypeName() {
        return mTypeName;
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return mTypeName;
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        setter = replaceLastLiteral(setter, "LoganSquare.typeConverterFor($T.class).parse($L)");
        builder.addStatement(setter, expandStringArgs(setterFormatArgs, mTypeName, JSON_PARSER_VARIABLE_NAME));
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, String getter, boolean writeFieldNameForObject) {
        if (!mTypeName.isPrimitive()) {
            builder.beginControlFlow("if ($L != null)", getter);
        }

        builder.addStatement("$T.typeConverterFor($T.class).serialize($L, $S, $L, $L)", LoganSquare.class, mTypeName, getter, fieldName, writeFieldNameForObject, JSON_GENERATOR_VARIABLE_NAME);

        if (!mTypeName.isPrimitive()) {
            builder.endControlFlow();
        }
    }

}
