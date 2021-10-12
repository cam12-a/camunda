package Models;


import com.fasterxml.jackson.annotation.JsonProperty;


public class StatusModel {
    @JsonProperty
    private StatusModelFields approved;
    @JsonProperty
    private StatusModelFields cancelled;
    @JsonProperty
    private StatusModelFields executed;

}
