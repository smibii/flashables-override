package com.smibii.flashables.commands.arguments;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class LightColorArgumentInfo implements ArgumentTypeInfo<LightColorArgument, LightColorArgumentInfo.Template> {
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
    public Template unpack(LightColorArgument argument) {
        return new Template();
    }

    public class Template implements ArgumentTypeInfo.Template<LightColorArgument> {
        @Override
        public LightColorArgument instantiate(CommandBuildContext pContext) {
            return new LightColorArgument();
        }

        @Override
        public ArgumentTypeInfo<LightColorArgument, ?> type() {
            return LightColorArgumentInfo.this;
        }
    }
}
