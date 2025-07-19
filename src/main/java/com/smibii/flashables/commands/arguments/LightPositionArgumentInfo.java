package com.smibii.flashables.commands.arguments;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class LightPositionArgumentInfo implements ArgumentTypeInfo<LightPositionArgument, LightPositionArgumentInfo.Template> {
    @Override
    public void serializeToNetwork(Template template, FriendlyByteBuf buffer) {
        // No extra data to serialize
    }

    @Override
    public Template deserializeFromNetwork(FriendlyByteBuf buffer) {
        return new Template();
    }

    @Override
    public void serializeToJson(Template pTemplate, JsonObject pJson) {

    }

    @Override
    public Template unpack(LightPositionArgument argument) {
        return new Template();
    }

    public class Template implements ArgumentTypeInfo.Template<LightPositionArgument> {
        @Override
        public LightPositionArgument instantiate(CommandBuildContext pContext) {
            return new LightPositionArgument();
        }

        @Override
        public ArgumentTypeInfo<LightPositionArgument, ?> type() {
            return LightPositionArgumentInfo.this;
        }
    }
}
