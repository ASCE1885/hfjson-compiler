package com.paic.hyperion.hfjson.compiler.type.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_GENERATOR_VARIABLE_NAME;
import static com.paic.hyperion.hfjson.compiler.ObjectMapperInjector.JSON_PARSER_VARIABLE_NAME;

public class BooleanFieldType extends FieldType {

    protected boolean isPrimitive;

    public BooleanFieldType(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public TypeName getTypeName() {
        return (isPrimitive ? TypeName.BOOLEAN : ClassName.get(Boolean.class));
    }

    @Override
    public TypeName getNonPrimitiveTypeName() {
        return ClassName.get(Boolean.class);
    }

    @Override
    public void parse(Builder builder, int depth, String setter, Object... setterFormatArgs) {
        if (isPrimitive) {
            setter = replaceLastLiteral(setter, "$L.getValueAsBoolean()");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME));
        } else {
            setter = replaceLastLiteral(setter, "$L.getCurrentToken() == JsonToken.VALUE_NULL ? null : Boolean.valueOf($L.getValueAsBoolean())");
            builder.addStatement(setter, expandStringArgs(setterFormatArgs, JSON_PARSER_VARIABLE_NAME, JSON_PARSER_VARIABLE_NAME));
        }
    }

    @Override
    public void serialize(Builder builder, int depth, String fieldName, String getter, boolean writeFieldNameForObject) {
        if (writeFieldNameForObject) {
            builder.addStatement("$L.writeBooleanField($S, $L)", JSON_GENERATOR_VARIABLE_NAME, fieldName, getter);
        } else {
            builder.addStatement("$L.writeBoolean($L)", JSON_GENERATOR_VARIABLE_NAME, getter);
        }
    }
}
