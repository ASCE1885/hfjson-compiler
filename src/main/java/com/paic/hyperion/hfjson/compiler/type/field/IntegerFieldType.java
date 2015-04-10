package com.paic.hyperion.hfjson.compiler.type.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class IntegerFieldType extends NumberFieldType {

    public IntegerFieldType(boolean isPrimitive) {
        super(isPrimitive);
    }

    @Override
    public TypeName getTypeName() {
        return (isPrimitive ? TypeName.INT : ClassName.get(Integer.class));
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return ClassName.get(Integer.class);
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        if (isPrimitive) {
            setter = replaceLastLiteral(setter, "$L.getValueAsInt()");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
        } else {
            setter = replaceLastLiteral(setter, "$L.getCurrentToken() == JsonToken.VALUE_NULL ? null : Integer.valueOf($L.getValueAsInt())");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME, JSON_PARSER_VARIABLE_NAME));
        }
    }
}
