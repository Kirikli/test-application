package asyncapi.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreateEvent.class, name = "UserStreamEvent"),
        @JsonSubTypes.Type(value = UserRegisteredEvent.class, name = "UserFlowEvent"),
        @JsonSubTypes.Type(value = TaskCreateEvent.class, name = "TaskCreateEvent"),
        @JsonSubTypes.Type(value = TaskAssignExecutorEvent.class, name = "AssignExecutorEvent"),
        @JsonSubTypes.Type(value = PaymentFlowEvent.class, name = "PaymentFlowEvent"),
        @JsonSubTypes.Type(value = TaskCompleteEvent.class, name = "CompleteTaskEvent"),
        @JsonSubTypes.Type(value = TaskUpdateEvent.class, name = "TaskUpdateEvent")
})
public sealed interface KafkaEvent permits
        TaskAssignExecutorEvent,
        TaskCreateEvent,
        UserRegisteredEvent,
        UserCreateEvent,
        PaymentFlowEvent,
        TaskCompleteEvent,
        TaskUpdateEvent {
}
