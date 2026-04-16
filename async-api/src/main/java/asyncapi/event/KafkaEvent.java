package asyncapi.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserStreamEvent.class, name = "UserStreamEvent"),
        @JsonSubTypes.Type(value = UserFlowEvent.class, name = "UserFlowEvent"),
        @JsonSubTypes.Type(value = TaskCreateEvent.class, name = "TaskCreateEvent"),
        @JsonSubTypes.Type(value = AssignExecutorEvent.class, name = "AssignExecutorEvent")})
public sealed interface KafkaEvent permits AssignExecutorEvent, TaskCreateEvent, UserFlowEvent, UserStreamEvent {
}
