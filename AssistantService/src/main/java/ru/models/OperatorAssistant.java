package ru.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import groovy.xml.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Service("OperatorAssistant")
public class OperatorAssistant  {

  @JsonProperty("operatorAssistant")
   private List<Map<String,OperatorId>> operatorAssistant=new ArrayList<>();


}
