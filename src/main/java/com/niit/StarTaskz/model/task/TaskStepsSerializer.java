package com.niit.StarTaskz.model.task;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TaskStepsSerializer extends JsonSerializer<TaskSteps> {
    @Override
    public void serialize(TaskSteps steps, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        // Serialize individual fields or the entire object as needed
        jsonGenerator.writeFieldName("step-description");
        jsonGenerator.writeObject(steps.getStepDescription());
        jsonGenerator.writeFieldName("status");
        jsonGenerator.writeObject(steps.getStatus());
        // Add more fields if necessary
        jsonGenerator.writeEndObject();
    }


}