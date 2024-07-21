package com.niit.StarTaskz.model.user;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", user.getId());
            jsonGenerator.writeStringField("profilePicture", user.getProfileImageUrl());
            jsonGenerator.writeStringField("firstName", user.getFirstName());
            jsonGenerator.writeStringField("lastName", user.getLastName());
            jsonGenerator.writeStringField("email", user.getEmail());
            jsonGenerator.writeStringField("accountType", String.valueOf(user.getAccountType()));
            jsonGenerator.writeObjectField("jobTitle", user.getJobTitle());
            jsonGenerator.writeObjectField("workSpaces", user.getWorkSpaces());
            jsonGenerator.writeObjectField("dateOfBirth", user.getDateOfBirth());
            jsonGenerator.writeObjectField("userTasks", user.getUserTasks());
            jsonGenerator.writeEndObject();
        } catch (Exception e) {
            System.err.println("Serialization error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }
}
